package com.cbag.autoatendimento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


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

    public Produto(){

    }

    public Produto(Long codigo, String nome, double preco, String imagemBase64) {
        setCodigo(codigo);
        setNome(nome);
        setPreco(preco);
        setImagemBase64(imagemBase64);
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
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
}
