package com.springapi.orders.domain.service;

import com.springapi.orders.domain.interfaces.Notificacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final List<Notificacao> meiosNotificacao;

    public String notificarCliente(Notificacao meio, String mensagem){
        return meio.notificar(mensagem);
    }
}
