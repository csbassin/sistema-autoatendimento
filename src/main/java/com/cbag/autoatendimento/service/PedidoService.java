package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.exception.ItensPedidoNaoInicializadaException;
import com.cbag.autoatendimento.model.ItemPedido;
import com.cbag.autoatendimento.model.Pedido;
import com.cbag.autoatendimento.model.printer.ReciptCliente;
import com.cbag.autoatendimento.repo.ItemPedidoRepository;
import com.cbag.autoatendimento.repo.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository<Pedido> pedidoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido cadastrar(Pedido pedido){
        if(pedido.getItensPedido() == null || pedido.getItensPedido().isEmpty()){
            throw new ItensPedidoNaoInicializadaException(pedido);
        }
        Pedido p = pedidoRepository.save(pedido); // tenho que salvar isso primeiro pra termos o número
        itemPedidoRepository.saveAll(pedido.getItensPedido());
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
