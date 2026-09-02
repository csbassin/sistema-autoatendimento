package com.cbag.autoatendimento.controller;

import com.cbag.autoatendimento.exception.ItemConfiguracaoNaoEncontradoException;
import com.cbag.autoatendimento.service.ConfigService;
import com.cbag.autoatendimento.service.SenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// vou usar para caso o front-end precise saber de algum item de configuração
@RestController
@RequestMapping("senha")
public class SenhaController {

    /*@GetMapping()
    public String getSenha() {
        return SenhaService.getSenha();
    } não precisamos desse method */

    @PostMapping
    public void setSenha(@RequestParam("value") String value) {
        SenhaService.setSenha(value);
    }
    @PutMapping
    public boolean verificarSenha(@RequestParam("value")String value){
        return SenhaService.correta(value);
    }
}
