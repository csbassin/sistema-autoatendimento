package com.cbag.autoatendimento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Produto {
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "produto_campo", joinColumns = @JoinColumn(name = "produto_codigo"))
    @MapKeyColumn(name = "chave")
    @Column(name = "valor")
    private Map<String, String> campos = new HashMap<>();

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

    public String getCampo(String chave) {
        return campos.get(chave);
    }

    public Produto setCampo(String chave, Object valor) {
        campos.put(chave, valor == null ? null : valor.toString());
        return this;
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

    public Map<String, String> getCampos() {
        return campos;
    }

    public void setCampos(Map<String, String> campos) {
        this.campos = campos == null ? new HashMap<>() : campos;
    }

    @Override
    public String toString() {
        return nome + " (" + codigo + ")";
    }
}
