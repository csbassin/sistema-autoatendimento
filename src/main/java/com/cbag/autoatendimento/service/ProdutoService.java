package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.exception.CodigoEmUsoException;
import com.cbag.autoatendimento.exception.EmUsoException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.model.MovimentacaoEstoque;
import com.cbag.autoatendimento.model.Produto;
import com.cbag.autoatendimento.model.TipoProduto;
import com.cbag.autoatendimento.repo.ItemPedidoRepository;
import com.cbag.autoatendimento.repo.MovimentacaoEstoqueRepository;
import com.cbag.autoatendimento.repo.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private TipoProdutoService tipoProdutoService;

    @Transactional
    public Produto cadastrar(Produto produto) throws CodigoEmUsoException, NaoEncontradoException {
        Optional<Produto> existente = produtoRepository.findById(produto.getCodigo());
        if (existente.isPresent()) {
            throw new CodigoEmUsoException("O código " + produto.getCodigo() + " está em uso por " + existente.get());
        }
        prepararParaSalvar(produto);

        produto = produtoRepository.save(produto);
        if (produto.controlaEstoque() && produto.getQuantidadeEmEstoque() > 0) {
            movimentacaoEstoqueRepository.save(new MovimentacaoEstoque(produto, produto.getQuantidadeEmEstoque(), "Estoque inicial cadastrado automaticamente."));
        }
        return produto;
    }

    @Transactional
    public Produto alterar(Long codigo, Produto produto) throws NaoEncontradoException {
        Produto atual = produtoRepository.recuperarPorCodigoETravar(codigo)
                .orElseThrow(() -> new NaoEncontradoException("Não foi encontrado um produto com código " + codigo));

        produto.setCodigo(codigo);
        produto.setQuantidadeEmEstoque(atual.getQuantidadeEmEstoque()); // o estoque só muda se tiver movimento
        prepararParaSalvar(produto);
        return produtoRepository.save(produto);
    }

    @Transactional
    public void remover(Long codigo) throws NaoEncontradoException, EmUsoException {
        Produto produto = recuperarPorCodigo(codigo);
        if (itemPedidoRepository.existsByProdutoCodigo(codigo)) {
            throw new EmUsoException("O produto " + produto + " não pode ser removido porque já faz parte de pedidos. "
                    + "Zere o estoque dele para tirá-lo do cardápio.");
        }
        movimentacaoEstoqueRepository.deleteByProdutoCodigo(codigo);
        produtoRepository.delete(produto);
    }

    public List<Produto> recuperarTudo() {
        return produtoRepository.findAll();
    }

    public Produto recuperarPorCodigo(Long codigo) throws NaoEncontradoException {
        return produtoRepository.findById(codigo)
                .orElseThrow(() -> new NaoEncontradoException("Não foi encontrado um produto com código " + codigo));
    }

    public Produto recuperarPorCodBarras(Long codBarras) throws NaoEncontradoException {
        return produtoRepository.findByCodBarras(codBarras)
                .orElseThrow(() -> new NaoEncontradoException("Não foi encontrado um produto com código de barras " + codBarras));
    }

    public List<Produto> recuperarPorTipo(Long tipoProdutoId) {
        return produtoRepository.findByTipoProdutoId(tipoProdutoId);
    }

    public List<Produto> recuperarDisponiveis() {
        return produtoRepository.recuperarDisponiveis();
    }

    public List<Produto> recuperarWhereEstoqueMaiorQueZero() {
        return produtoRepository.recuperarWhereEstoqueMaiorQueZero();
    }

    private void prepararParaSalvar(Produto produto) throws NaoEncontradoException {
        TipoProduto tipo = resolverTipo(produto);
        produto.setTipoProduto(tipo);

        if (Boolean.TRUE.equals(tipo.getControlaEstoque())) {
            if (produto.getQuantidadeEmEstoque() == null) {
                produto.setQuantidadeEmEstoque(0);
            }
        } else {
            produto.setQuantidadeEmEstoque(null);
        }
    }

    private TipoProduto resolverTipo(Produto produto) throws NaoEncontradoException {
        TipoProduto informado = produto.getTipoProduto();
        if (informado == null) {
            throw new NaoEncontradoException("É necessário informar o tipo do produto.");
        }
        if (informado.getId() != null) {
            return tipoProdutoService.recuperarPorId(informado.getId());
        }
        if (informado.getNome() != null) {
            return tipoProdutoService.recuperarPorNome(informado.getNome());
        }
        throw new NaoEncontradoException("O tipo do produto deve ser identificado pelo id ou pelo nome.");
    }
}
