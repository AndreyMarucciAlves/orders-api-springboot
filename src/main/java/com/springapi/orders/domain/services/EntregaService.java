package com.springapi.orders.domain.services;

import com.springapi.orders.domain.entities.DTOs.pedido.EntregaResponseDto;
import com.springapi.orders.domain.entities.DTOs.pedido.PedidoDto;
import com.springapi.orders.domain.entities.models.Pedido;
import com.springapi.orders.domain.exception.ResourceNotFoundException;
import com.springapi.orders.domain.interfaces.services.IEntregaService;
import com.springapi.orders.domain.interfaces.services.INotificacaoService;
import com.springapi.orders.infrastructure.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntregaService implements IEntregaService {

    private final PedidoRepository repository;
    private final INotificacaoService notificacaoService;

    @Override
    @Transactional
    public EntregaResponseDto despachar(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.despachar();
        String notificacao =
                notificacaoService.notificarCliente(pedido.getMeioNotificacao(),
                " Pedido número " + pedido.getId() + " em rota de entrega!");
        return new EntregaResponseDto(PedidoDto.from(pedido), notificacao);
    }

    @Override
    @Transactional
    public EntregaResponseDto confirmarEntrega(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.confirmarEntrega();
        String notificacao =
                notificacaoService.notificarCliente(pedido.getMeioNotificacao(),
                        " Pedido número " + pedido.getId() + " entregue!");
        return new EntregaResponseDto(PedidoDto.from(pedido), notificacao);
    }

    private Pedido buscarPedido(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", id));
    }
}