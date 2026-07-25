package com.cbag.autoatendimento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Bebida extends Produto{
    @NotNull
    private Integer quantidadeEmEstoque;
    @Column(unique = true) // faz com que o campo não possa se repetir
    private Long codBarras;

    public Bebida(){}
    public Bebida(Long codigo, String nome, double preco, String imagemBase64, int quantidadeInicialEstoque, long codBarras) {
        super(codigo, nome, preco, imagemBase64);
        setQuantidadeEmEstoque(quantidadeInicialEstoque);
        setCodBarras(codBarras);
    }
    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public Long getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(Long codBarras) {
        this.codBarras = codBarras;
    }
    @Override
    public String toString() {
        return getNome()+", "+getCodigo();
    }
}
