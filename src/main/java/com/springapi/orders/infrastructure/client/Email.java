package com.springapi.orders.infrastructure.client;

import com.springapi.orders.domain.interfaces.Notificacao;
import org.springframework.stereotype.Component;

@Component
public class Email implements Notificacao {
    public String notificar(String mensagem) {
        return "[E-mail]" + mensagem;
    }
}
