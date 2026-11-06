package com.springapi.orders.domain.entities.models.cardapio;

import lombok.Getter;

import java.util.List;

public class Cardapio {
    /**
     * Apesar de ferir o O do SOLID (Open/Closed), pois ao inserir um novo Produto faz-se necessário vir aqui e
     * adicioná-lo na lista, mantive essa implementação porque o escopo da atividade preve apenas esses três Produtos.
     */
    @Getter
    private static final List<Produto> produtos = List.of(
            new Bebida(),
            new Pizza(),
            new Hamburguer()
    );
}
