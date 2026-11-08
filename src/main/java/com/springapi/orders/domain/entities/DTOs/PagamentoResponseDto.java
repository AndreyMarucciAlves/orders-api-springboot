package com.springapi.orders.domain.entities.DTOs;

import com.springapi.orders.domain.interfaces.Notificacao;

public record PagamentoResponseDto(
        PedidoDto pedido,
        String notificacao
){ }
