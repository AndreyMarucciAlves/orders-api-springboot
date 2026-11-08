package com.springapi.orders.domain.entities.DTOs.relatorios;

import com.springapi.orders.domain.enums.StatusPedido;

public record PedidoLucroDto (
        Long id,
        String metodoPagamento,
        StatusPedido status,
        Long quantidadeItens,
        Double lucroBruto
){}
