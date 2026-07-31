package com.cbag.autoatendimento.model;

import java.util.ArrayList;

public class ItensPedido extends ArrayList<ItemPedido> {
    public void addItem(Produto produto, int quantidade, Pedido pedido) {
        if(quantidade <= 0){
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        // etapa 1: checar se já não consta mesmo item na lista
        int i = 0;
        while(i<this.size()) {
            ItemPedido currentItem = this.get(i);
            if(currentItem.getProduto().equals(produto)) { // se for o mesmo produto
                currentItem.setQuantidade(currentItem.getQuantidade() + quantidade); // aumenta a quantidade de itens
                return;
            }
            i++;
        }
        // se saiu do while, é porque esse item ainda não estava na lista
        this.add(new ItemPedido(pedido, produto, quantidade, produto.getPreco()));
    }
    public void removeItem(ItemPedido itemPedido) {
        this.remove(itemPedido); // como reescrevi o equals, funciona
    }
    public void removeAmount(Produto produto,  int quantidade) {
        if(quantidade < 0){
            throw new IllegalArgumentException("quantidade deve ser maior que zero.");
        }
        int i = 0;
        while(i<this.size()) {
            ItemPedido currentItem = this.get(i);
            if(produto.equals(currentItem.getProduto())) { // se for o mesmo produto
                if(quantidade == currentItem.getQuantidade()){
                    removeItem(currentItem);
                }else{
                    currentItem.setQuantidade(currentItem.getQuantidade() - quantidade); // diminui a quantidade de itens
                }
                return;
            }
            i++;
        }
        //se não achou, não faz nada
    }

}
