package com.springapi.orders.domain.entities.DTOs.pedido;

import com.springapi.orders.domain.entities.models.ItemPedido;
import com.springapi.orders.domain.entities.models.Pedido;
import com.springapi.orders.domain.enums.StatusPedido;

import java.util.List;

public record PedidoDto(
        Long id,
        List<ItemPedido> itens,
        StatusPedido status,
        String metodoPagamento,
        String meioNotificacao,
        double valorTotal
) {
    public static PedidoDto from(Pedido pedido) {
        return new PedidoDto(
                pedido.getId(),
                List.copyOf(pedido.getItens()),
                pedido.getStatus(),
                pedido.getMetodoPagamento(),
                pedido.getMeioNotificacao(),
                pedido.valorTotal()
        );
    }
}