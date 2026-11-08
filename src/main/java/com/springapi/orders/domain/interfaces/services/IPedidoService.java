package com.springapi.orders.domain.interfaces.services;

import com.springapi.orders.domain.entities.DTOs.PagamentoResponseDto;
import com.springapi.orders.domain.entities.DTOs.PedidoDto;
import com.springapi.orders.domain.entities.DTOs.pageable.FilterPageable;
import com.springapi.orders.domain.entities.DTOs.pageable.ResponsePagedCommon;
import com.springapi.orders.domain.entities.cardapio.Produto;
import com.springapi.orders.domain.entities.models.Pedido;
import com.springapi.orders.domain.interfaces.Notificacao;
import com.springapi.orders.domain.interfaces.Pagamento;

public interface IPedidoService {
    ResponsePagedCommon<PedidoDto> getPedidosPageable(FilterPageable filtro);
    Pedido getById(Long id);
    Pedido iniciarPedido();
    PedidoDto adicionarItem(Long id, Produto produto, int quantidade);
    PedidoDto removerItem(Long id, Produto produto, int quantidade);
    PagamentoResponseDto pagarPedido(Long id, Pagamento pagamento, Notificacao notificacao);
}
