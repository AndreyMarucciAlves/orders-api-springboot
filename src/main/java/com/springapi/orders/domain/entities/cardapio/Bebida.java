package com.springapi.orders.domain.entities.cardapio;

import org.springframework.stereotype.Component;

@Component
public class Bebida extends Produto {
    protected Bebida() {
        super(1,15.75);
    }
}
