package com.springapi.orders.domain.interfaces;

public interface Pagamento {
    default String getNome(){ return this.getClass().getSimpleName();}
}