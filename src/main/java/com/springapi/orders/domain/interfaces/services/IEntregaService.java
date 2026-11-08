package com.springapi.orders.domain.interfaces.services;

import com.springapi.orders.domain.entities.DTOs.pedido.EntregaResponseDto;

public interface IEntregaService {
    EntregaResponseDto despachar(Long id);
    EntregaResponseDto confirmarEntrega(Long id);
}
