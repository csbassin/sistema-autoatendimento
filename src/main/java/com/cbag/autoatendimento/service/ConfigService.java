package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.config.StaticConfigObjects;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    public boolean isAtendimentoLiberado(){
        return StaticConfigObjects.atendimentoLiberado;
    }
}
