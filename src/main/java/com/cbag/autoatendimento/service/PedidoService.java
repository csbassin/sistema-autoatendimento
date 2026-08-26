package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.exception.ItensPedidoNaoInicializadaException;
import com.cbag.autoatendimento.model.ItemPedido;
import com.cbag.autoatendimento.model.MovimentacaoEstoque;
import com.cbag.autoatendimento.model.Pedido;
import com.cbag.autoatendimento.model.printer.ReciptCliente;
import com.cbag.autoatendimento.repo.ItemPedidoRepository;
import com.cbag.autoatendimento.repo.MovimentacaoEstoqueRepository;
import com.cbag.autoatendimento.repo.PedidoRepository;
import com.cbag.autoatendimento.repo.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository<Pedido> pedidoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public Pedido cadastrar(Pedido pedido){
        if(pedido.getItensPedido() == null || pedido.getItensPedido().isEmpty()){
            throw new ItensPedidoNaoInicializadaException(pedido);
        }
        Pedido p = pedidoRepository.save(pedido); // tenho que salvar isso primeiro pra termos o número
        itemPedidoRepository.saveAll(pedido.getItensPedido());
        // preciso registrar as movimentações de estoque
        for(ItemPedido item:pedido.getItensPedido()){
            if(item.getProduto().getTipoProduto().getControlaEstoque()){ // se o produto controla estque, eu salvo
                movimentacaoEstoqueRepository.save(new MovimentacaoEstoque(item.getProduto(), -item.getQuantidade(), "Pedido nº "+pedido.getNumero()));
                item.getProduto().setQuantidadeEmEstoque(item.getProduto().getQuantidadeEmEstoque()-item.getQuantidade());
                produtoRepository.save(item.getProduto());
            }
        }
        ReciptCliente recipt = new ReciptCliente(p);
        recipt.montarBuffer();
        try{
            recipt.flushAndPrint();
        }catch(Exception e){
            e.printStackTrace();
        }
        return p;
    }

    public List<Pedido> recuperarTodos(){
        return pedidoRepository.findAll();
    }
}
