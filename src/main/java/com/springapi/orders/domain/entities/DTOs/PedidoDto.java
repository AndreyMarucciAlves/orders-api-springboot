package com.springapi.orders.domain.entities.DTOs;

import com.springapi.orders.domain.entities.models.pedido.ItemPedido;
import com.springapi.orders.domain.entities.models.pedido.Pedido;
import com.springapi.orders.domain.enums.StatusPedido;

import java.util.List;

public record PedidoDto(
        Long id,
        List<ItemPedido> itens,
        StatusPedido status,
        String metodoPagamento,
        double valorTotal
) {
    public static PedidoDto from(Pedido pedido) {
        return new PedidoDto(
                pedido.getId(),
                List.copyOf(pedido.getItens()),
                pedido.getStatus(),
                pedido.getMetodoPagamento(),
                pedido.valorTotal()
        );
    }
}