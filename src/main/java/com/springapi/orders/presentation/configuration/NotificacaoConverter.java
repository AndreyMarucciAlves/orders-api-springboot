package com.springapi.orders.presentation.configuration;

import com.springapi.orders.domain.interfaces.Notificacao;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificacaoConverter implements Converter<String, Notificacao> {

    private final List<Notificacao> notificacoes;

    @Override
    public Notificacao convert(String nome) {
        return notificacoes.stream()
                .filter(notificacao -> notificacao.getClass().getSimpleName().equals(nome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Método de notificação não encontrado: " + nome));
    }
}
