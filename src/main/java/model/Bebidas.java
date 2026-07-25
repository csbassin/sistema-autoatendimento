package model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Bebidas extends Produto{
    @NotNull
    private Integer quantidadeEmEstoque;
    private Long codBarras;
}
