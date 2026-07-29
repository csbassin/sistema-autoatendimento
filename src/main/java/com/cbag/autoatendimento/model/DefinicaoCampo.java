package com.cbag.autoatendimento.model;

import com.cbag.autoatendimento.enums.TipoDadoCampo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"tipo_produto_id", "chave"}))
public class DefinicaoCampo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tipo_produto_id")
    private TipoProduto tipoProduto;

    @NotEmpty(message = "É necessário informar a chave do campo.")
    private String chave;
    @NotEmpty(message = "É necessário informar o rótulo do campo.")
    private String rotulo;
    @NotNull(message = "É necessário informar o tipo de dado do campo.")
    @Enumerated(EnumType.STRING)
    private TipoDadoCampo tipoDado;
    @NotNull(message = "É necessário informar se o campo é obrigatório.")
    private Boolean obrigatorio;

    public DefinicaoCampo() {}

    public DefinicaoCampo(String chave, String rotulo, TipoDadoCampo tipoDado, boolean obrigatorio) {
        this.chave = chave;
        this.rotulo = rotulo;
        this.tipoDado = tipoDado;
        this.obrigatorio = obrigatorio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public TipoDadoCampo getTipoDado() {
        return tipoDado;
    }

    public void setTipoDado(TipoDadoCampo tipoDado) {
        this.tipoDado = tipoDado;
    }

    public Boolean getObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(Boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    @Override
    public String toString() {
        return rotulo + " (" + chave + ": " + tipoDado.getNome() + ")";
    }
}
