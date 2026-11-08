package com.springapi.orders.domain.services;

import com.springapi.orders.domain.entities.DTOs.PedidoDto;
import com.springapi.orders.domain.entities.DTOs.PagamentoResponseDto;
import com.springapi.orders.domain.entities.DTOs.pageable.FilterPageable;
import com.springapi.orders.domain.entities.DTOs.pageable.ResponsePagedCommon;
import com.springapi.orders.domain.entities.models.Pedido;
import com.springapi.orders.domain.entities.cardapio.Produto;
import com.springapi.orders.domain.exception.ResourceNotFoundException;
import com.springapi.orders.domain.interfaces.Notificacao;
import com.springapi.orders.domain.interfaces.Pagamento;
import com.springapi.orders.domain.interfaces.services.IPedidoService;
import com.springapi.orders.infrastructure.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService implements IPedidoService {

    private final PedidoRepository repository;
    private final NotificacaoService notificacaoService;

    @Transactional(readOnly = true)
    @Override
    public ResponsePagedCommon<PedidoDto> getPedidosPageable(FilterPageable filterPageable){
        Page<Pedido> listPedidosDB = repository.findAll(
                PageRequest.of(
                        filterPageable.getPage(), filterPageable.getSize(),
                        Sort.by(filterPageable.getDirection(), filterPageable.getOrderBy())
                )
        );

        List<PedidoDto> listPedidosResponse = listPedidosDB.stream().map(this::toDto).toList();

        return new ResponsePagedCommon<PedidoDto>(
            listPedidosResponse,
            listPedidosDB.getTotalElements(),
            listPedidosDB.getTotalPages(),
            filterPageable.getSize()
        );
    }

    private PedidoDto toDto(Pedido pedido) {
        return new PedidoDto(
                pedido.getId(), pedido.getItens(), pedido.getStatus(), pedido.getMetodoPagamento(), pedido.valorTotal());
    }

    @Transactional(readOnly = true)
    @Override
    public Pedido getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", id));
    }

    @Transactional
    @Override
    public Pedido iniciarPedido(){
        Pedido pedido = new Pedido();
        repository.save(pedido);
        return pedido;
    }

    @Transactional
    @Override
    public PedidoDto adicionarItem(Long id, Produto produto, int quantidade){
        Pedido pedido = this.getById(id);
        pedido.adicionarItem(produto,quantidade);
        return PedidoDto.from(pedido);
    }

    @Transactional
    @Override
    public PedidoDto removerItem(Long id, Produto produto, int quantidade){
        Pedido pedido = this.getById(id);
        pedido.removerItem(produto,quantidade);
        return PedidoDto.from(pedido);
    }

    @Transactional
    @Override
    public PagamentoResponseDto pagarPedido(Long id, Pagamento metodoPagamento, Notificacao meioNotificacao){
        Pedido pedido = this.getById(id);

        pedido.marcarComoPago(metodoPagamento);
        String comprovante = metodoPagamento.processarPagamento(pedido);
        String mensagem = notificacaoService.notificarCliente(
                meioNotificacao,"Pagamento recebido. Pedido número " + id + " sendo preparado para o envio!");

        return new PagamentoResponseDto(PedidoDto.from(pedido), mensagem, comprovante);
    }
}
