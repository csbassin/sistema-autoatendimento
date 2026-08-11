package com.cbag.autoatendimento.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class TipoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "É necessário informar o nome do tipo de produto.")
    @Column(unique = true)
    private String nome;
    @NotNull(message = "É necessário informar se o tipo de produto controla estoque.")
    private Boolean controlaEstoque;

    public TipoProduto() {}

    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public TipoProduto(String nome, boolean controlaEstoque) {
        this.nome = nome;
        this.controlaEstoque = controlaEstoque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getControlaEstoque() {
        return controlaEstoque;
    }

    public void setControlaEstoque(Boolean controlaEstoque) {
        this.controlaEstoque = controlaEstoque;
    }

    @Override
    public String toString() {
        return nome;
    }
}
