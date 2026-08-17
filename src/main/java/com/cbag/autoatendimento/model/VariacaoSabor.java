package com.cbag.autoatendimento.model;

import com.cbag.autoatendimento.model.idClasses.VariacaoSaborId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@IdClass(VariacaoSaborId.class)
public class VariacaoSabor {
    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tipo_produto_id")
    private TipoProduto tipoProduto;
    @Id
    @NotEmpty(message = "Deve ser informado o nome do sabor.")
    private String nome;
    private int quantidadeMinima; // indica qual a quantidade mínima por sabor

    public VariacaoSabor(){}

    public VariacaoSabor(TipoProduto tipo, String nome, int quantidadeMinima){
        this.nome = nome;
        this.quantidadeMinima = quantidadeMinima;
        this.tipoProduto = tipo;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
