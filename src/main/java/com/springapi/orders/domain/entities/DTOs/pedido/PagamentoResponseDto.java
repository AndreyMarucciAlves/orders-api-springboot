package com.springapi.orders.domain.entities.DTOs.pedido;

public record PagamentoResponseDto(
        PedidoDto pedido,
        String notificacao,
        String comprovante
){ }
