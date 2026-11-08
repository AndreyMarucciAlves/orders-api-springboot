package com.springapi.orders.domain.entities.DTOs.relatorios;

public record VendasPorProdutoDto (
    String produto,
    Long quantidade,
    Double receitaGerada
){}
