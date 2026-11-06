package com.springapi.orders.domain.entities.models.pedido;

import com.springapi.orders.domain.entities.models.cardapio.Cardapio;
import com.springapi.orders.domain.entities.models.cardapio.Produto;
import com.springapi.orders.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="pedido")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Pedido {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;

    @ElementCollection
    @CollectionTable(name = "pedido_item", joinColumns = @JoinColumn(name = "id_pedido"))
    private List<ItemPedido> itens = new ArrayList<>();

    @Column(name = "metodo_pagamento")
    private String metodoPagamento;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.ABERTO;

    public void adicionarItem(Produto produto, int quantidade){
        if(quantidade <=0)
            throw new IllegalArgumentException("A quantidade de " + produto.getName() + " deve ser maior que 0.");

        if(!Cardapio.getProdutos().contains(produto))
            throw new IllegalArgumentException(
                    "Só é possível pedir um destes produtos na lista: " + Cardapio.getProdutos() +
                    ".\nProduto inserido: " + produto + "."
            );

        ItemPedido itemPedido = new ItemPedido(produto.getName(), produto.getPreco(), quantidade);

        //Possivel por conta do @EqualsAndHashCode em ItemProduto
        if(this.itens.contains(itemPedido)){
            int index = itens.indexOf(itemPedido);
            itens.get(index).addQuantidade(quantidade);
        } else {
            this.itens.add(itemPedido);
        }
    }

    public double valorTotal(){
        double valorTotal = 0;
        for(ItemPedido item : this.itens){
            valorTotal += item.getPreco() * item.getQuantidade();
        }
        return valorTotal;
    }
}
