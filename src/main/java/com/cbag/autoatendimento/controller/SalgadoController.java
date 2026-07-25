package com.cbag.autoatendimento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cbag.autoatendimento.service.SalgadoService;

@RestController
@RequestMapping("salgados") //...:8080/salgados
public class SalgadoController {
    @Autowired
    private SalgadoService salgadoService;


}
