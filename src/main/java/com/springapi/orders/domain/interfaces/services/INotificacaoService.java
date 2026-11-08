package com.springapi.orders.domain.interfaces.services;

import com.springapi.orders.domain.interfaces.clients.Notificacao;

public interface INotificacaoService {
    String notificarCliente(Notificacao meio, String mensagem);
    String notificarCliente(String nomeMeio, String mensagem);
}
