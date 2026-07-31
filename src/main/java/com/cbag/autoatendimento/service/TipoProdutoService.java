package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.exception.CodigoEmUsoException;
import com.cbag.autoatendimento.exception.EmUsoException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.model.TipoProduto;
import com.cbag.autoatendimento.repo.ProdutoRepository;
import com.cbag.autoatendimento.repo.TipoProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoProdutoService {
    @Autowired
    private TipoProdutoRepository tipoProdutoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public TipoProduto cadastrar(TipoProduto tipoProduto) throws CodigoEmUsoException {
        Optional<TipoProduto> existente = tipoProdutoRepository.findByNome(tipoProduto.getNome());
        if (existente.isPresent()) {
            throw new CodigoEmUsoException("Já existe um tipo de produto chamado " + tipoProduto.getNome());
        }
        return tipoProdutoRepository.save(tipoProduto);
    }

    @Transactional
    public TipoProduto alterar(Long id, TipoProduto tipoProduto) throws NaoEncontradoException {
        TipoProduto atual = recuperarPorId(id);
        atual.setNome(tipoProduto.getNome());
        atual.setControlaEstoque(tipoProduto.getControlaEstoque());
        return tipoProdutoRepository.save(atual);
    }

    public List<TipoProduto> recuperarTudo() {
        return tipoProdutoRepository.findAll();
    }

    public TipoProduto recuperarPorId(Long id) throws NaoEncontradoException {
        return tipoProdutoRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Não foi encontrado um tipo de produto com id " + id));
    }

    public TipoProduto recuperarPorNome(String nome) throws NaoEncontradoException {
        return tipoProdutoRepository.findByNome(nome)
                .orElseThrow(() -> new NaoEncontradoException("Não foi encontrado um tipo de produto chamado " + nome));
    }

    public void remover(Long id) throws NaoEncontradoException, EmUsoException {
        TipoProduto tipoProduto = recuperarPorId(id);
        if (produtoRepository.existsByTipoProdutoId(id)) {
            throw new EmUsoException("O tipo " + tipoProduto.getNome() + " não pode ser removido porque ainda existem produtos cadastrados nele.");
        }
        tipoProdutoRepository.delete(tipoProduto);
    }
}
