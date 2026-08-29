package com.cbag.autoatendimento.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//entidade pedido nao funciona entao fazer isso aqui para funcionar fica melhor, nao sei se é amelhor opção qualquer coisa depois mudar
public class PedidoRequest {
    @NotEmpty(message = "O nome do cliente deve ser informado.")
    private String nomeCliente;
    private Boolean pagamentoPendente = Boolean.TRUE;
    private LocalDateTime timestampRetirada;
    private Boolean origemTotem = Boolean.FALSE;
    private Map<String, Object> extraData;
    @NotEmpty(message = "O pedido precisa ter ao menos um item.")
    private List<ItemRequest> itens = new ArrayList<>();

    public static class ItemRequest {
        @NotNull(message = "O código do produto deve ser inforado.")
        private Long codigoProduto;
        @NotNull(message = "A quantidade deve ser informada.")
        private Integer quantidade;
        private String observacao = "";

        public Long getCodigoProduto() { return codigoProduto; }
        public void setCodigoProduto(Long codigoProduto) { this.codigoProduto = codigoProduto; }
        public Integer getQuantidade() { return quantidade; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
        public String getObservacao() { return observacao == null ? "" : observacao; }
        public void setObservacao(String observacao) { this.observacao = observacao; }
    }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public Boolean getPagamentoPendente() { return pagamentoPendente; }
    public void setPagamentoPendente(Boolean pagamentoPendente) { this.pagamentoPendente = pagamentoPendente; }
    public LocalDateTime getTimestampRetirada() { return timestampRetirada; }
    public void setTimestampRetirada(LocalDateTime timestampRetirada) { this.timestampRetirada = timestampRetirada; }
    public Boolean getOrigemTotem() { return origemTotem; }
    public void setOrigemTotem(Boolean origemTotem) { this.origemTotem = origemTotem; }
    public Map<String, Object> getExtraData() { return extraData; }
    public void setExtraData(Map<String, Object> extraData) { this.extraData = extraData; }
    public List<ItemRequest> getItens() { return itens; }
    public void setItens(List<ItemRequest> itens) { this.itens = itens; }
}
