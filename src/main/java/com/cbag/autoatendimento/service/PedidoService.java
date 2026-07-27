package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.model.Pedido;
import com.cbag.autoatendimento.model.printer.ReciptCliente;
import com.cbag.autoatendimento.repo.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository<Pedido> pedidoRepository;

    public Pedido cadastrar(Pedido pedido){
        Pedido p = pedidoRepository.save(pedido);
        ReciptCliente recipt = new ReciptCliente(p);
        recipt.montarBuffer();
        try{
            recipt.flushAndPrint();
        }catch(Exception e){
            e.printStackTrace();
        }
        return p;
    }
}
