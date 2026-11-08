package com.springapi.orders.domain.services;

import com.springapi.orders.domain.interfaces.clients.Notificacao;
import com.springapi.orders.domain.interfaces.services.INotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService implements INotificacaoService {

    private final List<Notificacao> notificacoes;

    public String notificarCliente(Notificacao meio, String mensagem){
        return meio.notificar(mensagem);
    }

    public String notificarCliente(String nomeMeio, String mensagem) {
        for(Notificacao n : notificacoes){
            if(n.getNome().equals(nomeMeio)){
                return n.notificar(mensagem);
            }
        }
        throw new IllegalArgumentException("Meio de notificação não encontrado.");
    }
}
