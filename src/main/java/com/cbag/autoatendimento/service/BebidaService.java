package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.exception.CodigoEmUsoException;
import com.cbag.autoatendimento.model.Bebida;
import com.cbag.autoatendimento.model.Salgado;
import com.cbag.autoatendimento.repo.BebidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BebidaService {
    @Autowired
    private BebidaRepository bebidaRepository;

    public Bebida cadastrar(Bebida bebida) throws CodigoEmUsoException {
        Optional<Bebida> b = bebidaRepository.findById(bebida.getCodigo());
        if(b.isPresent()){
            Bebida beb = b.get();
            throw new CodigoEmUsoException("O código "+bebida.getCodigo()+" está em uso por "+beb);
        }
        return bebidaRepository.save(bebida);
    }
    public List<Bebida> recuperarTudo(){
        return bebidaRepository.findAll();
    }
    public List<Bebida> recuperarWhereEstoqueMaiorQueZero(){
        return bebidaRepository.recuperarWhereEstoqueMaiorQueZero();
    }
}
