package com.springapi.orders.domain.entities.DTOs.relatorios;

public record VendaPorMetodoPagamentoDto(
        String metodo,
        Long quantidade
){}
