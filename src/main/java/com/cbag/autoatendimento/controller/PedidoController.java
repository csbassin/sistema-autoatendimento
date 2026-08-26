package com.cbag.autoatendimento.controller;

import com.cbag.autoatendimento.model.Pedido;
import com.cbag.autoatendimento.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> recuperarTodos(){
        System.out.println("O controller foi acionado");
        return pedidoService.recuperarTodos();
    }

}
