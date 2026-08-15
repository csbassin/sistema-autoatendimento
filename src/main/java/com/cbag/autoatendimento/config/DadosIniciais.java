package com.cbag.autoatendimento.config;

import com.cbag.autoatendimento.enums.EstadoPedido;
import com.cbag.autoatendimento.exception.CodigoEmUsoException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.model.*;
import com.cbag.autoatendimento.repo.TipoProdutoRepository;
import com.cbag.autoatendimento.service.PedidoService;
import com.cbag.autoatendimento.service.ProdutoService;
import com.cbag.autoatendimento.service.TipoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DadosIniciais {
    @Autowired
    private TipoProdutoService tipoProdutoService;
    @Autowired
    private TipoProdutoRepository tipoProdutoRepository;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private PedidoService pedidoService;

    public void popular() throws CodigoEmUsoException, NaoEncontradoException {
        TipoProduto salgado = criarTipo("Salgado", false, "imagem de salgado");
        TipoProduto bebida = criarTipo("Bebida", true, "imagem de bebida");

        Produto salgados50 = cadastrarSeNovo(new Produto(1L, "Salgados", 27.00, "imagem lol", salgado).set("quantidade", 50).set("frito", true));
        Produto salgados100 = cadastrarSeNovo(new Produto(4L, "Salgados", 50.00, "imagem", salgado).set("quantidade", 100).set("frito", true));

        Produto fanta = new Produto(2L, "Fanta Laranja", 4.00, "imagem", bebida);
        fanta.setQuantidadeEmEstoque(0);
        fanta.setCodBarras(0L);
        fanta.set("volumeMl", 350).set("gelada", true);
        cadastrarSeNovo(fanta);

        Produto coca = new Produto(3L, "Coca-Cola", 5.00, "k", bebida);
        coca.setQuantidadeEmEstoque(10);
        coca.setCodBarras(1L);
        coca.set("volumeMl", 600).set("gelada", true).set("sabor", "original");
        cadastrarSeNovo(coca);


        Pedido pedido = new Pedido("Josias", EstadoPedido.PREPARANDO, true);
        try{
            pedidoService.cadastrar(pedido);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        pedido.addItem(salgados50, 10, "25 quibes\n25 enroladinhos de salsicha"); // adiciona 500 salgados
        pedido.removeAmount(salgados50, 3); // remove 150 salgados
        pedido.addItem(salgados100, 3, "25 coxinhas"); // adiciona 300 salgados
        pedido.addItem(fanta, 2, ""); // dois refrigerantes
        pedido.set("observacao", "sem cebola").set("mesa", 7).set("viagem", false); //wexmplo para pedido ter dados extars

        pedidoService.cadastrar(pedido);
    }

    private TipoProduto criarTipo(String nome, boolean controlaEstoque, String imagemBase64) throws CodigoEmUsoException {
        Optional<TipoProduto> existente = tipoProdutoRepository.findByNome(nome);
        if (existente.isPresent()) {
            return existente.get();
        }
        return tipoProdutoService.cadastrar(new TipoProduto(nome, controlaEstoque, imagemBase64));
    }

    private Produto cadastrarSeNovo(Produto produto) throws NaoEncontradoException {
        try {
           return produtoService.cadastrar(produto);
        } catch (CodigoEmUsoException e) {
            return produtoService.recuperarPorCodigo(produto.getCodigo());
        }
    }
}
