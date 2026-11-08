package com.springapi.orders.infrastructure.client;

import com.springapi.orders.domain.interfaces.Notificacao;
import org.springframework.stereotype.Component;

@Component
public class WhatsAppNotificacao implements Notificacao {
    public String notificar() {
        return "Cliente notificado via WhatsApp.";
    }
}
