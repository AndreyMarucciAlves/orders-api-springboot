package com.springapi.orders.domain.interfaces;

public interface Notificacao {
    String notificar();

    default String getNome(){ return this.getClass().getSimpleName(); };
}
