package com.cbag.autoatendimento.model.idClasses;

import com.cbag.autoatendimento.model.Pedido;
import com.cbag.autoatendimento.model.Produto;

import java.io.Serializable;

public class ItemPedidoId implements Serializable {
    private Pedido pedido;
    private Produto produto;
}
