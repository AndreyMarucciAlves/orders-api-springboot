package com.springapi.orders.domain.interfaces.services;

import com.springapi.orders.domain.interfaces.Notificacao;

public interface INotificacaoService {
    String notificarCliente(Notificacao meio, String mensagem);
    public String notificarCliente(String nomeMeio, String mensagem);
}
