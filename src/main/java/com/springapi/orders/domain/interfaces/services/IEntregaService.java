package com.springapi.orders.domain.interfaces.services;

import com.springapi.orders.domain.entities.DTOs.PedidoDto;

public interface IEntregaService {
    PedidoDto despachar(Long id);
    PedidoDto confirmarEntrega(Long id);
}
