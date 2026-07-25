package com.cbag.autoatendimento.model;

import com.cbag.autoatendimento.enums.TipoSalgado;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Salgado extends Produto{
    @NotNull(message = "É necessário informar a quantidade de unidades no pacote.")
    private Integer quantidade;
    @NotNull(message = "É necessário informar se o salgado é congelado ou frito.")
    private TipoSalgado tipo;

    public Salgado(){}
    public Salgado(long codigo, String nome, double preco, String imagemBase64, Integer quantidade, TipoSalgado tipo) {
        super(codigo, nome, preco, imagemBase64);
        this.quantidade = quantidade;
        this.tipo = tipo;
    }
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setTipoFromBool(boolean tipo) {
        this.tipo = TipoSalgado.fromBoolean(tipo);
    }

    public TipoSalgado getTipo() {
        return tipo;
    }

    public void setTipo(TipoSalgado tipo) {
        this.tipo = tipo;
    }
    @Override
    public String toString() {
        return getNome()+" - "+getQuantidade()+"un.";
    }
}
