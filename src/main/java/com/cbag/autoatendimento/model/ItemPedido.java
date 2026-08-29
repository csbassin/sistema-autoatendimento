package com.cbag.autoatendimento.model;

import com.cbag.autoatendimento.model.idClasses.ItemPedidoId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    //@NotEmpty
    private String observacao;

    public ItemPedido(){}
    public ItemPedido(Pedido pedido, Produto produto, int quantidade, double preco, String observacao) {
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.observacao = observacao;
    }


    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    @JsonIgnore
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemPedido outro)) {
            return false;
        }
        return java.util.Objects.equals(outro.getPedido(), this.pedido)
                && java.util.Objects.equals(outro.getProduto(), this.produto);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(pedido, produto);
    }
    @Override
    public String toString() {
        return "("+quantidade+") - "+produto.getNome()+": "+quantidade+" x "+preco+" = "+(quantidade*preco);
    }

}
