package com.cbag.autoatendimento.config;

import com.cbag.autoatendimento.enums.TipoDadoCampo;
import com.cbag.autoatendimento.exception.CampoInvalidoException;
import com.cbag.autoatendimento.exception.CodigoEmUsoException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.model.DefinicaoCampo;
import com.cbag.autoatendimento.model.Produto;
import com.cbag.autoatendimento.model.TipoProduto;
import com.cbag.autoatendimento.repo.TipoProdutoRepository;
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

    public void popular() throws CodigoEmUsoException, NaoEncontradoException, CampoInvalidoException {
        TipoProduto salgado = criarTipoSalgado();
        TipoProduto bebida = criarTipoBebida();

        cadastrarSeNovo(new Produto(1L, "Coxinha", 10.00, "imagem lol", salgado)
                .setCampo("quantidade", 50)
                .setCampo("frito", true));
        cadastrarSeNovo(new Produto(4L, "Esfiha de carne", 8.50, "imagem", salgado)
                .setCampo("quantidade", 12)
                .setCampo("frito", false));

        Produto fanta = new Produto(2L, "Fanta Laranja", 4.00, "imagem", bebida);
        fanta.setQuantidadeEmEstoque(0);
        fanta.setCodBarras(0L);
        fanta.setCampo("volumeMl", 350);
        cadastrarSeNovo(fanta);

        Produto coca = new Produto(3L, "Coca-Cola", 5.00, "k", bebida);
        coca.setQuantidadeEmEstoque(10);
        coca.setCodBarras(1L);
        coca.setCampo("volumeMl", 600);
        cadastrarSeNovo(coca);
    }

    private TipoProduto criarTipoSalgado() throws CodigoEmUsoException {
        Optional<TipoProduto> existente = tipoProdutoRepository.findByNome("Salgado");
        if (existente.isPresent()) {
            return existente.get();
        }
        TipoProduto salgado = new TipoProduto("Salgado", false);
        salgado.adicionarCampo(new DefinicaoCampo("quantidade", "Unidades no pacote", TipoDadoCampo.INTEIRO, true));
        salgado.adicionarCampo(new DefinicaoCampo("frito", "É frito (senão, congelado)", TipoDadoCampo.BOOLEANO, true));
        return tipoProdutoService.cadastrar(salgado);
    }

    private TipoProduto criarTipoBebida() throws CodigoEmUsoException {
        Optional<TipoProduto> existente = tipoProdutoRepository.findByNome("Bebida");
        if (existente.isPresent()) {
            return existente.get();
        }
        TipoProduto bebida = new TipoProduto("Bebida", true);
        bebida.adicionarCampo(new DefinicaoCampo("volumeMl", "Volume (ml)", TipoDadoCampo.INTEIRO, false));
        bebida.adicionarCampo(new DefinicaoCampo("gelada", "Servida gelada", TipoDadoCampo.BOOLEANO, false));
        return tipoProdutoService.cadastrar(bebida);
    }

    private void cadastrarSeNovo(Produto produto) throws NaoEncontradoException, CampoInvalidoException {
        try {
            produtoService.cadastrar(produto);
        } catch (CodigoEmUsoException e) {

        }
    }
}
