package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.exception.EstoqueInvalidoException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.model.MovimentacaoEstoque;
import com.cbag.autoatendimento.model.Produto;
import com.cbag.autoatendimento.repo.MovimentacaoEstoqueRepository;
import com.cbag.autoatendimento.repo.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoEstoqueService {
    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public MovimentacaoEstoque cadastrar(MovimentacaoEstoque movimentacaoEstoque) throws NaoEncontradoException, EstoqueInvalidoException {
        if (movimentacaoEstoque.getProduto() == null || movimentacaoEstoque.getProduto().getCodigo() == null) {
            throw new NaoEncontradoException("É necessário informar o produto da movimentação.");
        }
        Long codigo = movimentacaoEstoque.getProduto().getCodigo();

        Produto produto = produtoRepository.recuperarPorCodigoETravar(codigo)
                .orElseThrow(() -> new NaoEncontradoException("Não foi encontrado um produto com código " + codigo));

        if (!produto.controlaEstoque()) {
            throw new EstoqueInvalidoException("O produto " + produto + " é do tipo " + produto.getTipoProduto().getNome()
                    + ", que não controla estoque.");
        }

        int novaQuantidade = produto.getQuantidadeEmEstoque() + movimentacaoEstoque.getQuantidadeAlterada();
        if (novaQuantidade < 0) {
            throw new EstoqueInvalidoException("O produto " + produto + " tem apenas " + produto.getQuantidadeEmEstoque()
                    + " unidades em estoque, não é possível retirar " + Math.abs(movimentacaoEstoque.getQuantidadeAlterada()) + ".");
        }

        produto.setQuantidadeEmEstoque(novaQuantidade);
        produtoRepository.save(produto);

        movimentacaoEstoque.setProduto(produto);
        return movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }

    public List<MovimentacaoEstoque> recuperarPorProduto(Long codigo) {
        return movimentacaoEstoqueRepository.findByProdutoCodigoOrderByTimestampDesc(codigo);
    }
}
