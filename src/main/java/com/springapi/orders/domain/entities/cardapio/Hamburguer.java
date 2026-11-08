package com.springapi.orders.domain.entities.cardapio;

import org.springframework.stereotype.Component;

@Component
public class Hamburguer extends Produto {
    protected Hamburguer() {
        super(3,25.50);
    }
}
