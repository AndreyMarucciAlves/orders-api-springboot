package com.springapi.orders.domain.interfaces.services;

import com.springapi.orders.domain.entities.DTOs.pedido.PagamentoResponseDto;
import com.springapi.orders.domain.entities.DTOs.pedido.PedidoDto;
import com.springapi.orders.domain.entities.DTOs.pageable.FilterPageable;
import com.springapi.orders.domain.entities.DTOs.pageable.ResponsePagedCommon;
import com.springapi.orders.domain.entities.cardapio.Produto;
import com.springapi.orders.domain.interfaces.clients.Notificacao;
import com.springapi.orders.domain.interfaces.clients.Pagamento;

public interface IPedidoService {
    ResponsePagedCommon<PedidoDto> getPedidosPageable(FilterPageable filtro);
    PedidoDto getById(Long id);
    PedidoDto iniciarPedido();
    PedidoDto adicionarItem(Long id, Produto produto, int quantidade);
    PedidoDto removerItem(Long id, Produto produto, int quantidade);
    PagamentoResponseDto pagarPedido(Long id, Pagamento pagamento, Notificacao notificacao);
}
