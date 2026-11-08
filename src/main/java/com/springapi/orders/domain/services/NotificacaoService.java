package com.springapi.orders.domain.services;

import com.springapi.orders.domain.interfaces.Notificacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    public String notificarCliente(Notificacao meio, String mensagem){
        return meio.notificar(mensagem);
    }
}
