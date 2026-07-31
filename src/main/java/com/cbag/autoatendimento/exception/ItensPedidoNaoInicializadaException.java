package com.cbag.autoatendimento.exception;

import com.cbag.autoatendimento.model.Pedido;

public class ItensPedidoNaoInicializadaException extends RuntimeException {
    public ItensPedidoNaoInicializadaException(Pedido pedido) {
        super("A lista de itens do pedido "+pedido.getNumero()+" não foi inicializada.");
    }
}
