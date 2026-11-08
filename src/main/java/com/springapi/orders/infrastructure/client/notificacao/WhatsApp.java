package com.springapi.orders.infrastructure.client.notificacao;

import com.springapi.orders.domain.interfaces.clients.Notificacao;
import org.springframework.stereotype.Component;

@Component
public class WhatsApp implements Notificacao {
    public String notificar(String mensagem) {
        return "[WhatsApp]" + mensagem;
    }
}
