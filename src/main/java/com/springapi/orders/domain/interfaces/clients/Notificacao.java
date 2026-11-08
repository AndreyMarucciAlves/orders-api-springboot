package com.springapi.orders.domain.interfaces.clients;

public interface Notificacao {
    String notificar(String mensagem);

    default String getNome(){ return this.getClass().getSimpleName(); };
}
