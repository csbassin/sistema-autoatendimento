package com.cbag.autoatendimento.controller;

import com.cbag.autoatendimento.exception.ItemConfiguracaoNaoEncontradoException;
import com.cbag.autoatendimento.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
