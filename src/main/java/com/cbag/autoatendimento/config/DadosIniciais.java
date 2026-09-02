package com.cbag.autoatendimento.config;

import com.cbag.autoatendimento.enums.EstadoPedido;
import com.cbag.autoatendimento.exception.CodigoEmUsoException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.exception.SenhaJaDefinidaException;
import com.cbag.autoatendimento.model.*;
import com.cbag.autoatendimento.repo.TipoProdutoRepository;
import com.cbag.autoatendimento.service.*;
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
    @Autowired
    private MovimentacaoEstoqueService movimentacaoEstoqueService;

    private static final java.util.List<String> SABORES_SALGADO =
            java.util.List.of("Coxinha", "Kibe", "Misto", "Bolinha de queijo e presunto");

    public void popular() throws CodigoEmUsoException, NaoEncontradoException {
        try{
            SenhaService.setSenha("teste123");
        }catch(SenhaJaDefinidaException e){
            System.out.println("A senha não foi reescrita porque já foi definida.");
        }

        TipoProduto salgado = criarTipo("Salgado", false, "imagem de salgado");
        TipoProduto bebida = criarTipo("Bebida", true, "imagem de bebida");


        Produto salgados50 = cadastrarSeNovo(new Produto(1L, "Salgados", 27.00, "imagem lol", salgado).set("quantidade", 50).set("frito", true)
                .set("sabores", SABORES_SALGADO).set("incrementoSabor", 25));
        Produto salgados100 = cadastrarSeNovo(new Produto(4L, "Salgados", 50.00, "imagem", salgado).set("quantidade", 100).set("frito", true)
                .set("sabores", SABORES_SALGADO).set("incrementoSabor", 25));

        Produto fanta = new Produto(2L, "Fanta Laranja", 4.00, "imagem", bebida);
        fanta.setQuantidadeEmEstoque(0);
        fanta.setCodBarras(0L);
        fanta.set("volumeMl", 350).set("gelada", true);
        cadastrarSeNovo(fanta);

        Produto guaracamp = new Produto(5L, "Guaracamp", 3.50, "imagem legal", bebida);
        guaracamp.setQuantidadeEmEstoque(30);
        guaracamp.setCodBarras(2L);
        guaracamp.set("volumeMl", 290)
                 .set("sabores", java.util.List.of("Natural", "Maracujá", "Açaícamp", "Uva"))
                 .set("incrementoSabor", 1);
        cadastrarSeNovo(guaracamp);

        Produto coca = new Produto(3L, "Coca-Cola", 5.00, "k", bebida);
        coca.setQuantidadeEmEstoque(10);
        coca.setCodBarras(1L);
        coca.set("volumeMl", 600).set("gelada", true).set("sabor", "original");
        cadastrarSeNovo(coca);


        try {
            movimentacaoEstoqueService.cadastrar(new MovimentacaoEstoque(fanta, 10, "Reposição inicial."));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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

        try {
            pedidoService.cadastrar(pedido);
        } catch (Exception e) {
            System.out.println("pedido exemplo teste falhou " + e.getMessage());
        }
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
