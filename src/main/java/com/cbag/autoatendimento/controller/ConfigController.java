package com.cbag.autoatendimento.controller;

import com.cbag.autoatendimento.exception.ItemConfiguracaoNaoEncontradoException;
import com.cbag.autoatendimento.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// vou usar para caso o front-end precise saber de algum item de configuração
@Controller
@RequestMapping("configuracoes")
public class ConfigController {
    @Autowired
    private ConfigService configService;

    @GetMapping("{name}")
    public boolean getBooleanConfigItem(@PathVariable String name){
        switch(name){
            case("atendimentoLiberado"):
                return configService.isAtendimentoLiberado();
            default:
                throw new ItemConfiguracaoNaoEncontradoException("Não foi encontrado um item de configuração de nome "+name);
        }
    }
    @PostMapping("{name}?{value}")
    public ResponseEntity<Boolean> setBooleanConfigItem(@PathVariable String name, @PathVariable String value){
        switch(name){
            case("atendimentoLiberado"):
                configService.setAtendimentoLiberado(Boolean.parseBoolean(value));
            default:
                throw new ItemConfiguracaoNaoEncontradoException("Não foi encontrado um item de configuração de nome "+name);
        }
    }

}
