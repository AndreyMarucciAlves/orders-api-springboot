package com.springapi.orders.domain.entities.DTOs;

public record PagamentoResponseDto(
        PedidoDto pedido,
        String notificacao,
        String comprovante
){ }
