package com.cbag.autoatendimento.controller;

import com.cbag.autoatendimento.exception.ItemConfiguracaoNaoEncontradoException;
import com.cbag.autoatendimento.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// vou usar para caso o front-end precise saber de algum item de configuração
@RestController
@RequestMapping("configuracoes")
public class ConfigController {
    @Autowired
    private ConfigService configService;

    // GET /configuracoes/atendimentoLiberado
    @GetMapping("{name}")
    public boolean getBooleanConfigItem(@PathVariable String name) {
        if ("atendimentoLiberado".equals(name)) {
            return configService.isAtendimentoLiberado();
        }
        throw new ItemConfiguracaoNaoEncontradoException("Não foi encontrado um item de configuração de nome " + name);
    }

    // PUT /configuracoes/atendimentoLiberado?value=false
    @PutMapping("{name}")
    public boolean setBooleanConfigItem(@PathVariable String name, @RequestParam("value") boolean value) {
        if ("atendimentoLiberado".equals(name)) {
            configService.setAtendimentoLiberado(value);
            return configService.isAtendimentoLiberado();
        }
        throw new ItemConfiguracaoNaoEncontradoException("Não foi encontrado um item de configuração de nome " + name);
    }
}
