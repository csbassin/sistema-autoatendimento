package com.cbag.autoatendimento.model;

import com.cbag.autoatendimento.enums.EstadoPedido;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PedidoAgendado extends Pedido {
    private LocalDateTime timestampRetirada;

    public PedidoAgendado() {}
    public PedidoAgendado(String nomeCliente, EstadoPedido estadoPedido, List<ItemPedido> itensPedido, boolean pagamentoPendente, LocalDateTime timestampRetirada) {
        super(nomeCliente, estadoPedido,/*, itensPedido,*/ pagamentoPendente);
        this.timestampRetirada = timestampRetirada;
    }

    public void setTimestampRetirada(LocalDateTime timestampRetirada) {
        this.timestampRetirada = timestampRetirada;
    }
    public LocalDateTime getTimestampRetirada() {
        return timestampRetirada;
    }
}
