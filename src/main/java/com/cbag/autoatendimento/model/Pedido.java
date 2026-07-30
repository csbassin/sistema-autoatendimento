package com.cbag.autoatendimento.model;

import com.cbag.autoatendimento.enums.EstadoPedido;
import com.cbag.autoatendimento.exception.ItensPedidoNaoInicializada;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Pedido {
    // acho que a variável Tipo vai ser adicionada automaticamente pelo hibernate por causa da herança
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;
    @NotNull(message = "Data e hora do pedido precisam ser informados.")
    private LocalDateTime timestamp;
    @NotEmpty(message = "O nome do cliente deve ser informado.")
    private String nomeCliente;
    @NotNull(message = "O estado do pedido deve ser informado.")
    private EstadoPedido estadoPedido;
    @Transient
    private List<ItemPedido> itensPedido; // -> não posso salvar a lista no db
    @NotNull(message = "Deve-se dizer se o pagamento está pendente.")
    private Boolean pagamentoPendente;
    @Transient
    private Double cachedPreco = null;

    public Pedido() {}

    public Pedido(String nomeCliente,  EstadoPedido estadoPedido, /*List<ItemPedido> itensPedido,*/ boolean pagamentoPendente) {
        this.timestamp = LocalDateTime.now();
        this.nomeCliente = nomeCliente;
        this.estadoPedido = estadoPedido;
        //this.itensPedido = itensPedido;
        this.pagamentoPendente = pagamentoPendente;
    }
    public Pedido(String nomeCliente,  EstadoPedido estadoPedido, List<ItemPedido> itensPedido, boolean pagamentoPendente) {
        this.timestamp = LocalDateTime.now();
        this.nomeCliente = nomeCliente;
        this.estadoPedido = estadoPedido;
        this.itensPedido = itensPedido;
        this.pagamentoPendente = pagamentoPendente;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public Boolean getPagamentoPendente() {
        return pagamentoPendente;
    }

    public void setPagamentoPendente(Boolean pagamentoPendente) {
        this.pagamentoPendente = pagamentoPendente;
    }

    /*public void setCachedPreco(Double cachedPreco) {
        this.cachedPreco = cachedPreco;
    }*/

    public Double getCachedPreco() {// atributo derivado
        if(itensPedido == null){
            throw new ItensPedidoNaoInicializada(this);
        }
        if(cachedPreco == null){
            cachedPreco = 0.0;
            for(ItemPedido itemPedido : itensPedido) {
                cachedPreco += (itemPedido.getPreco()* itemPedido.getQuantidade());
            }
        }
        return cachedPreco;
    }
    public void invalidatePrecoCache(){
        cachedPreco = null;
    }
}
