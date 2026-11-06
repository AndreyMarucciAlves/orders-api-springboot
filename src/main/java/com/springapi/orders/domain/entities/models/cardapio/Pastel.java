package com.springapi.orders.domain.entities.models.cardapio;

import org.springframework.stereotype.Component;

@Component
public class Pastel extends Produto{
    protected Pastel() {
        super(4, 17.95);
    }
}
