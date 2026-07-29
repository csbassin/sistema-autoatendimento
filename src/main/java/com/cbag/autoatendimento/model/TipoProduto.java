package com.cbag.autoatendimento.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    @Valid
    @JsonManagedReference
    @OneToMany(mappedBy = "tipoProduto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DefinicaoCampo> campos = new ArrayList<>();

    public TipoProduto() {}

    public TipoProduto(String nome, boolean controlaEstoque) {
        this.nome = nome;
        this.controlaEstoque = controlaEstoque;
    }

    public TipoProduto adicionarCampo(DefinicaoCampo campo) {
        campo.setTipoProduto(this);
        campos.add(campo);
        return this;
    }

    public DefinicaoCampo getCampo(String chave) {
        for (DefinicaoCampo campo : campos) {
            if (campo.getChave().equals(chave)) {
                return campo;
            }
        }
        return null;
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

    public List<DefinicaoCampo> getCampos() {
        return campos;
    }

    public void setCampos(List<DefinicaoCampo> campos) {
        this.campos.clear();
        if (campos != null) {
            for (DefinicaoCampo campo : campos) {
                adicionarCampo(campo);
            }
        }
    }

    @Override
    public String toString() {
        return nome;
    }
}
