package com.springapi.orders.infrastructure.client.notificacao;

import com.springapi.orders.domain.interfaces.Notificacao;
import org.springframework.stereotype.Component;

@Component
public class SMS implements Notificacao {
    public String notificar(String mensagem) {
        return "[SMS]" + mensagem;
    }
}
