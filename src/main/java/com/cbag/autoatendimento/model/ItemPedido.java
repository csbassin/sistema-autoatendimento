package com.cbag.autoatendimento.model;

import com.cbag.autoatendimento.model.idClasses.ItemPedidoId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@IdClass(ItemPedidoId.class)
public class ItemPedido {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Pedido pedido;
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Produto produto;
    @NotNull(message = "A quantidade deve ser informada.")
    private Integer quantidade;
    @NotNull(message = "O preço deve ser informado.")
    private Double preco;

    public ItemPedido(){}
    public ItemPedido(Pedido pedido, Produto produto, int quantidade, double preco) {
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}
