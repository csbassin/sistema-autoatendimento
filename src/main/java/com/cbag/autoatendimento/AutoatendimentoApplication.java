package com.cbag.autoatendimento;

import com.cbag.autoatendimento.config.DadosIniciais;
import com.cbag.autoatendimento.config.StaticConfigObjects;
import com.cbag.autoatendimento.model.MovimentacaoEstoque;
import com.cbag.autoatendimento.model.Produto;
import com.cbag.autoatendimento.model.TipoProduto;
import com.cbag.autoatendimento.service.MovimentacaoEstoqueService;
import com.cbag.autoatendimento.service.ProdutoService;
import com.cbag.autoatendimento.service.TipoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoatendimentoApplication implements CommandLineRunner {

    @Autowired
    private DadosIniciais dadosIniciais;
    @Autowired
    private TipoProdutoService tipoProdutoService;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private MovimentacaoEstoqueService movimentacaoEstoqueService;

    public static void main(String[] args) {
        SpringApplication.run(AutoatendimentoApplication.class, args);
    }


    @Override
    public void run(String... args) {
        try {
            teste();
        } catch (Exception e) {
            System.out.println("teste falhou: " + e.getMessage());
        }
    }

    public void teste() throws Exception{//exemplo, depois limpar
        dadosIniciais.popular(); //exemplo, deopis limpar

        //teste
        System.out.println("Tipos de produto cadastrados: ");
        for (TipoProduto t : tipoProdutoService.recuperarTudo()) {
            System.out.println("    ->" + t + " (controla estoque: " + t.getControlaEstoque() + ")");
        }

        System.out.println("Produtos cadastrados: ");
        for (Produto p : produtoService.recuperarTudo()) {
            System.out.println("    ->" + p + " [" + p.getTipoProduto() + "] estoque=" + p.getQuantidadeEmEstoque()
                    + " extraData=" + p.getExtraData());
        }

        Produto coxinha = produtoService.recuperarPorCodigo(1L); //expl
        System.out.println("Lendo a da coxinha: quantidade=" + coxinha.getInt("quantidade")
                + ", frito=" + coxinha.getBool("frito") + ", recheio=" + coxinha.getString("recheio", "não informado"));

        System.out.println("Produtos com estoque maior que zero: ");
        for (Produto p : produtoService.recuperarWhereEstoqueMaiorQueZero()) {
            System.out.println("    ->" + p);
        }

        System.out.println("Produtos disponíveis para venda: ");
        for (Produto p : produtoService.recuperarDisponiveis()) {
            System.out.println("    ->" + p);
        }

        Produto coca = produtoService.recuperarPorCodigo(3L);
        Produto fanta = produtoService.recuperarPorCodigo(2L);
        movimentacaoEstoqueService.cadastrar(new MovimentacaoEstoque(coca, -9, "alterada no testes."));
        movimentacaoEstoqueService.cadastrar(new MovimentacaoEstoque(fanta, 5, "alterada nos testes."));

        System.out.println("Estoque após as movimentações: ");
        for (Produto p : produtoService.recuperarWhereEstoqueMaiorQueZero()) {
            System.out.println("    ->" + p + ": " + p.getQuantidadeEmEstoque());
        }

        System.out.println(StaticConfigObjects.userHomeDir);
        //fim teste
    }
}
