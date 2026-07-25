package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.exception.CodigoEmUsoException;
import com.cbag.autoatendimento.model.Salgado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cbag.autoatendimento.repo.SalgadoRepository;

import java.util.Optional;

@Service
public class SalgadoService {
    @Autowired
    private SalgadoRepository salgadoRepository;

    public Salgado cadastrar(Salgado salgado) throws CodigoEmUsoException {
        Optional<Salgado> s = salgadoRepository.findById(salgado.getCodigo());
        if(s.isPresent()){
            Salgado salg = s.get();
            throw new CodigoEmUsoException("O código "+salgado.getCodigo()+" está em uso por "+ salg);
        }
        return salgadoRepository.save(salgado);
    }
}
