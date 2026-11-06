package com.springapi.orders.domain.entities.DTOs;

import com.springapi.orders.domain.entities.models.pedido.ItemPedido;
import com.springapi.orders.domain.enums.StatusPedido;
import com.springapi.orders.domain.interfaces.Pagamento;

import java.util.List;

public record PedidoDto(
        Long id,
        List<ItemPedido> itens,
        StatusPedido status,
        String metodoPagamento
) { }
