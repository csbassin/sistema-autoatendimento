package com.cbag.autoatendimento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Produto extends ExtraDataContainer<Produto> {
    @Id
    @NotNull(message = "O código deve ser informado.")
    private Long codigo;
    @NotEmpty(message = "É necessário informar o nome do produto.")
    private String nome;
    @NotNull(message = "É necessário informar o preço.")
    private Double preco;
    @NotEmpty(message = "É necessário atribuir uma imagem ao produto.")
    private String imagemBase64;

    @NotNull(message = "É necessário informar o tipo do produto.")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tipo_produto_id")
    private TipoProduto tipoProduto;

    private Integer quantidadeEmEstoque;

    @Column(unique = true) // faz com que o campo não possa se repetir
    private Long codBarras;

    public Produto() {}

    public Produto(Long codigo, String nome, double preco, String imagemBase64, TipoProduto tipoProduto) {
        setCodigo(codigo);
        setNome(nome);
        setPreco(preco);
        setImagemBase64(imagemBase64);
        setTipoProduto(tipoProduto);
    }

    public boolean controlaEstoque() {
        return tipoProduto != null && Boolean.TRUE.equals(tipoProduto.getControlaEstoque());
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
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
        return nome;
    }
}
