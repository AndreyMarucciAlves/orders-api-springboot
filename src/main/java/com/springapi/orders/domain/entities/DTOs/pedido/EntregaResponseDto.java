package com.springapi.orders.domain.entities.DTOs.pedido;

public record EntregaResponseDto(
        PedidoDto pedidoDto,
        String notificacao
){}
