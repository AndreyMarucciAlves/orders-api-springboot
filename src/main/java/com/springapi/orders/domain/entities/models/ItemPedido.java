package com.springapi.orders.domain.entities.models;

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

    protected void addQuantidade(int quantidade){ this.quantidade += quantidade; }

    protected void removerQuantidade(int quantidade){
        if(this.quantidade < quantidade)

            throw new IllegalArgumentException(
                    "Não foi possível remover " + quantidade + " " + nomeProduto +
                            " porque só tem " + this.quantidade + " adicionado ao pedido."
            );

        this.quantidade -= quantidade;
    }
}
