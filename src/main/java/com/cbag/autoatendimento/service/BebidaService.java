package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.exception.CodigoEmUsoException;
import com.cbag.autoatendimento.exception.NaoEncontradoException;
import com.cbag.autoatendimento.model.Bebida;
import com.cbag.autoatendimento.model.MovimentacaoEstoque;
import com.cbag.autoatendimento.model.Salgado;
import com.cbag.autoatendimento.repo.BebidaRepository;
import com.cbag.autoatendimento.repo.MovimentacaoEstoqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BebidaService {
    @Autowired
    private BebidaRepository bebidaRepository;
    @Autowired
    MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public Bebida cadastrar(Bebida bebida) throws CodigoEmUsoException {
        Optional<Bebida> b = bebidaRepository.findById(bebida.getCodigo());
        if(b.isPresent()){
            Bebida beb = b.get();
            throw new CodigoEmUsoException("O código "+bebida.getCodigo()+" está em uso por "+beb);
        }
        bebida = bebidaRepository.save(bebida);
        if(bebida.getQuantidadeEmEstoque()>0){
            movimentacaoEstoqueRepository.save(new MovimentacaoEstoque(bebida, bebida.getQuantidadeEmEstoque(), "Estoque inicial cadastrado automaticamente."));
        }
        return bebida;
    }

    @Transactional
    public Bebida alterar(Bebida bebida) throws NaoEncontradoException {
        bebidaRepository.recuperarPorCodigoETravar(bebida.getCodigo()).orElseThrow(()->new NaoEncontradoException("Não foi encontrada uma bebida com código "+bebida.getCodigo()));
        return bebidaRepository.save(bebida);
    }

    public List<Bebida> recuperarTudo(){
        return bebidaRepository.findAll();
    }
    public Bebida recuperarPorCodigo(Long codigo){
       return bebidaRepository.findById(codigo).orElse(null);
    }
    public List<Bebida> recuperarWhereEstoqueMaiorQueZero(){
        return bebidaRepository.recuperarWhereEstoqueMaiorQueZero();
    }

}
