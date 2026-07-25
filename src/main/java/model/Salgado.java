package model;

import enums.TipoSalgado;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Salgado extends Produto{
    @NotNull(message = "É necessário informar a quantidade de unidades no pacote.")
    private Integer quantidade;
    @NotNull(message = "É necessário informar se o salgado é congelado ou frito.")
    private TipoSalgado tipo;

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
}
