package com.springapi.orders.entities.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ItemPedido {
    @Column(name = "nome_produto")
    private String nomeProduto;

    private double preco;

    private int quantidade;
}
