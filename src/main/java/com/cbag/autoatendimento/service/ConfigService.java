package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.config.StaticConfigObjects;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    public void setAtendimentoLiberado(boolean atendimentoLiberado) {
        StaticConfigObjects.atendimentoLiberado = atendimentoLiberado;
    }
    public boolean isAtendimentoLiberado(){
        return StaticConfigObjects.atendimentoLiberado;
    }
}
