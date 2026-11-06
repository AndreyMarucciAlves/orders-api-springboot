package com.springapi.orders.domain.entities.models.pedido;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {
    @EqualsAndHashCode.Include
    @Column(name = "nome_produto")
    private String nomeProduto;

    private double preco;

    private int quantidade;

    protected void addQuantidade(int quantidade){
        this.quantidade += quantidade;
    }
}
