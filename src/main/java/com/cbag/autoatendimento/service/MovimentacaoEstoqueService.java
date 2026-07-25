package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.model.Bebida;
import com.cbag.autoatendimento.model.MovimentacaoEstoque;
import com.cbag.autoatendimento.repo.MovimentacaoEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoEstoqueService {
    // todo implementar tudo
    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    @Autowired
    private BebidaService bebidaService;

    public MovimentacaoEstoque cadastrar(MovimentacaoEstoque movimentacaoEstoque) throws NaoEncontradoException {
        // quando cadastrar a movimentação de estoque, precisa também alterar o produto
        Bebida b = bebidaService.recuperarPorCodigo(movimentacaoEstoque.getProduto().getCodigo());
        if(b == null){
            throw new NaoEncontradoException("Não foi encontrada uma bebida com código "+movimentacaoEstoque.getProduto().getCodigo());
        }
        b.setQuantidadeEmEstoque(b.getQuantidadeEmEstoque()+movimentacaoEstoque.getQuantidadeAlterada());
        bebidaService.alterar(b);
        return movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }

}
