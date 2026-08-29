package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.config.StaticConfigObjects;
import com.cbag.autoatendimento.dto.PedidoRequest;
import com.cbag.autoatendimento.enums.EstadoPedido;
import com.cbag.autoatendimento.exception.AtendimentoEncerradoException;
import com.cbag.autoatendimento.exception.EstoqueInvalidoException;
import com.cbag.autoatendimento.exception.ItensPedidoNaoInicializadaException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.exception.PedidoJaCanceladoException;
import com.cbag.autoatendimento.model.*;
import com.cbag.autoatendimento.model.printer.ReciptCliente;
import com.cbag.autoatendimento.repo.ItemPedidoRepository;
import com.cbag.autoatendimento.repo.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository<Pedido> pedidoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private MovimentacaoEstoqueService movimentacaoEstoqueService;
    @Autowired
    private ProdutoService produtoService;

    @Transactional
    public Pedido cadastrar(Pedido pedido) {
        if (pedido.getItensPedido() == null || pedido.getItensPedido().isEmpty()) {
            throw new ItensPedidoNaoInicializadaException(pedido);
        }
        if (pedido.getTimestamp() == null) {
            pedido.setTimestamp(LocalDateTime.now());
        }
        if (pedido.getEstadoPedido() == null) {
            pedido.setEstadoPedido(EstadoPedido.PREPARANDO);
        }

        ItensPedido itens = pedido.getItensPedido();
        Pedido salvo = pedidoRepository.save(pedido); // salvo antes para termos o numero

        for (ItemPedido item : itens) {
            item.setPedido(salvo);
        }
        itemPedidoRepository.saveAll(itens);

        for (ItemPedido item : itens) {
            if (item.getProduto().controlaEstoque()) {
                try {
                    movimentacaoEstoqueService.cadastrar(new MovimentacaoEstoque(
                            item.getProduto(), -item.getQuantidade(), "Pedido nº " + salvo.getNumero()));
                } catch (NaoEncontradoException | EstoqueInvalidoException e) {
                    throw new EstoqueInsuficienteRuntime(e.getMessage());
                }
            }
        }

        salvo.setItensPedido(itens);
        salvo.invalidatePrecoCache();
        imprimir(salvo);
        return salvo;
    }

    @Transactional
    public Pedido cadastrarDeRequest(PedidoRequest request) throws NaoEncontradoException {
        if (Boolean.TRUE.equals(request.getOrigemTotem()) && !StaticConfigObjects.atendimentoLiberado) {
            throw new AtendimentoEncerradoException();
        }

        Pedido pedido = request.getTimestampRetirada() == null ? new Pedido() : new PedidoAgendado();
        pedido.setNomeCliente(request.getNomeCliente());
        pedido.setEstadoPedido(EstadoPedido.PREPARANDO);
        pedido.setPagamentoPendente(request.getPagamentoPendente() == null || request.getPagamentoPendente());
        pedido.setTimestamp(LocalDateTime.now());
        pedido.setItensPedido(new ItensPedido());
        if (request.getExtraData() != null) {
            pedido.setExtraData(request.getExtraData());
        }
        if (pedido instanceof PedidoAgendado agendado) {
            agendado.setTimestampRetirada(request.getTimestampRetirada());
        }

        for (PedidoRequest.ItemRequest item : request.getItens()) {
            Produto produto = produtoService.recuperarPorCodigo(item.getCodigoProduto());
            pedido.addItem(produto, item.getQuantidade(), item.getObservacao());
        }

        return cadastrar(pedido);
    }

    public List<Pedido> recuperarTodos() {
        //pega mais novo primeiro
        List<Pedido> pedidos = pedidoRepository.findAll(
                Sort.by(Sort.Direction.DESC, "timestamp").and(Sort.by(Sort.Direction.DESC, "numero")));
        for (Pedido pedido : pedidos) {
            hidratarItens(pedido);
        }
        return pedidos;
    }

    public List<Pedido> recuperarPorEstado(EstadoPedido estado) {
        List<Pedido> filtrados = new ArrayList<>();
        for (Pedido pedido : recuperarTodos()) {
            if (pedido.getEstadoPedido() == estado) {
                filtrados.add(pedido);
            }
        }
        return filtrados;
    }

    public Pedido recuperarPorNumero(Long numero) throws NaoEncontradoException {
        Pedido pedido = pedidoRepository.findById(numero)
                .orElseThrow(() -> new NaoEncontradoException("Não foi encontrado um pedido com número " + numero));
        return hidratarItens(pedido);
    }

    @Transactional
    public Pedido alterarEstado(Long numero, EstadoPedido estado) throws NaoEncontradoException {
        Pedido pedido = recuperarPorNumero(numero);
        pedido.setEstadoPedido(estado);
        pedidoRepository.save(pedido);
        return pedido;
    }

    @Transactional
    public Pedido confirmarPagamento(Long numero) throws NaoEncontradoException {
        Pedido pedido = recuperarPorNumero(numero);
        pedido.setPagamentoPendente(false);
        pedidoRepository.save(pedido);
        return pedido;
    }

    @Transactional
    public Pedido cancelar(Long numero) throws NaoEncontradoException, PedidoJaCanceladoException {
        Pedido pedido = recuperarPorNumero(numero);

        if (pedido.getEstadoPedido() == EstadoPedido.CANCELADO) {
            throw new PedidoJaCanceladoException("O pedido nº " + numero + " já está cancelado.");
        }

        // devolve ao estoque o que a criacao do pedido tinha retirado
        for (ItemPedido item : pedido.getItensPedido()) {
            if (item.getProduto().controlaEstoque()) {
                try {
                    movimentacaoEstoqueService.cadastrar(new MovimentacaoEstoque(
                            item.getProduto(), item.getQuantidade(), "Cancelamento do pedido nº " + numero));
                } catch (EstoqueInvalidoException e) {
                    throw new EstoqueInsuficienteRuntime(e.getMessage());
                }
            }
        }

        pedido.setEstadoPedido(EstadoPedido.CANCELADO);
        pedidoRepository.save(pedido);
        return pedido;
    }

    public Pedido reimprimir(Long numero) throws NaoEncontradoException {
        Pedido pedido = recuperarPorNumero(numero);
        imprimir(pedido);
        return pedido;
    }

    private Pedido hidratarItens(Pedido pedido) {
        ItensPedido itens = new ItensPedido();
        itens.addAll(itemPedidoRepository.findByPedidoNumero(pedido.getNumero()));
        pedido.setItensPedido(itens);
        pedido.invalidatePrecoCache();
        return pedido;
    }

    private void imprimir(Pedido pedido) {
        try {
            ReciptCliente recipt = new ReciptCliente(pedido);
            recipt.montarBuffer();
            recipt.flushAndPrint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class EstoqueInsuficienteRuntime extends RuntimeException {
        public EstoqueInsuficienteRuntime(String mensagem) {
            super(mensagem);
        }
    }
}
