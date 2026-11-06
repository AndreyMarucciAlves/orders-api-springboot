package com.springapi.orders.domain.entities.models.cardapio;

import org.springframework.stereotype.Component;

@Component
public class Pizza extends Produto {
    protected Pizza() {
        super(2,90.0);
    }
}
