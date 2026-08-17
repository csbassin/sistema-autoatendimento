package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.model.TipoProduto;
import com.cbag.autoatendimento.model.VariacaoSabor;
import com.cbag.autoatendimento.repo.VariacaoSaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariacaoSaborService {
    @Autowired
    private VariacaoSaborRepository variacaoSaborRepository;

    public VariacaoSabor cadastrar(VariacaoSabor variacaoSabor){
        return variacaoSaborRepository.save(variacaoSabor);
    }
    public List<VariacaoSabor> recuperarTudo(){
        return variacaoSaborRepository.findAll();
    }
    public List<VariacaoSabor> findByTipoProduto(Long tipoProduto){
        return findByTipoProduto(tipoProduto);
    }
}
