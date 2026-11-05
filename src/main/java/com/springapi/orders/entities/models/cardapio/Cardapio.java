package com.springapi.orders.entities.models.cardapio;

import lombok.Getter;

import java.util.List;

public class Cardapio {
    @Getter
    private static final List<Produto> produtos = List.of(
            new Pizza(),
            new Hamburguer(),
            new Bebida()
    );
}
