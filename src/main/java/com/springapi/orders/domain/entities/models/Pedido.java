package com.springapi.orders.domain.entities.models;

import com.springapi.orders.domain.entities.cardapio.Produto;
import com.springapi.orders.domain.enums.StatusPedido;
import com.springapi.orders.domain.interfaces.Pagamento;
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

        if(this.status.equals(StatusPedido.PAGO))
            throw new IllegalStateException("Não foi possível adicionar itens ao pedido porque o mesmo já foi pago.");

        ItemPedido itemPedido = new ItemPedido(produto.getName(), produto.getPreco(), quantidade);

        //Possivel por conta do @EqualsAndHashCode em ItemProduto
        if(this.itens.contains(itemPedido)){
            int index = itens.indexOf(itemPedido);
            itens.get(index).addQuantidade(quantidade);
        } else {
            this.itens.add(itemPedido);
        }
    }

    public void removerItem(Produto produto, int quantidade){
        if(quantidade <=0)
            throw new IllegalArgumentException(
                    "A quantidade de '" + produto.getName() + "' a ser removido deve ser maior que 0.");

        if(this.status.equals(StatusPedido.PAGO))
            throw new IllegalStateException(
                    "Não foi possível remover o item '" + produto.getName() +"' do pedido porque o mesmo já foi pago.");

        ItemPedido itemPedido = new ItemPedido(produto.getName(), produto.getPreco(), quantidade);

        //Possivel por conta do @EqualsAndHashCode em ItemProduto
        if(this.itens.contains(itemPedido)){
            int index = itens.indexOf(itemPedido);
            ItemPedido itemProcurado = this.itens.get(index);

            itemProcurado.removerQuantidade(quantidade);

            if(itens.get(index).getQuantidade() <= 0){
                itens.remove(index);
            }
        } else {
            throw new IllegalStateException(
                    "Não foi possível concluir a ação porque este item '" + produto.getName() + "' não está incluso no pedido.");
        }
    }

    public double valorTotal(){
        double valorTotal = 0;
        for(ItemPedido item : this.itens){
            valorTotal += item.getPreco() * item.getQuantidade();
        }
        return valorTotal;
    }

    public void marcarComoPago(Pagamento metodoPagamaento){
        if(this.itens.isEmpty())
            throw new IllegalStateException("Não foi possível concluir a ação porque a lista de itens está vazia.");

        if(this.status.equals(StatusPedido.PAGO))
            throw new IllegalStateException("O pedido já foi pago.");

        this.metodoPagamento = metodoPagamaento.getNome();
        this.status = StatusPedido.PAGO;
    }
}
