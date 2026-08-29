package com.cbag.autoatendimento.enums;

public enum EstadoPedido {
    PREPARANDO("Em preparação", "O pedido já foi enviado para preparo."), AGUARDANDO_RETIRADA("Aguardando retirada", "O pedido está pronto e pode ser retirado."), PRONTO("Pronto", "O pedido foi retirado pelo cliente."),
    CANCELADO("Cancelado", "O pedido foi cancelado.");

    EstadoPedido(String estado, String descricao) {
        this.estado = estado;
        this.descricao = descricao;
    }

    private final String descricao;
    private final String estado;

    public String getEstado() {
        return estado;
    }
    public String getDescricao() {
        return descricao;
    }
}
