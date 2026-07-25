package com.cbag.autoatendimento.model;

import com.cbag.autoatendimento.model.idClasses.MovimentacaoEstoqueId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@IdClass(MovimentacaoEstoqueId.class)
public class MovimentacaoEstoque {
    @Id
    @ManyToOne(fetch = FetchType.EAGER) // ManyToOne: Usada do lado que tem muitos pra indicar qual o campo que a chave estrangeira vai referenciar
    private Produto produto;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // o número da movimentação de estoque é autogerado
    private Long numero;
    @NotNull(message = "Deve ser informada a quantidade movimentada.")
    private Integer quantidadeAlterada;
    private String obs;

    public MovimentacaoEstoque() {}
    public MovimentacaoEstoque(Produto prouto, Integer quantidadeAlterada, String obs) {
        this.produto = prouto;
        this.quantidadeAlterada = quantidadeAlterada;
        this.obs = obs;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Integer getQuantidadeAlterada() {
        return quantidadeAlterada;
    }

    public void setQuantidadeAlterada(Integer quantidadeAlterada) {
        this.quantidadeAlterada = quantidadeAlterada;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }
}
