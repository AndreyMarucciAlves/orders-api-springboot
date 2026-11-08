package com.springapi.orders.domain.service;

import com.springapi.orders.domain.entities.DTOs.PedidoDto;
import com.springapi.orders.domain.entities.DTOs.pageable.FilterPageable;
import com.springapi.orders.domain.entities.DTOs.pageable.ResponsePagedCommon;
import com.springapi.orders.domain.entities.models.Pedido;
import com.springapi.orders.domain.entities.cardapio.Produto;
import com.springapi.orders.domain.exception.ResourceNotFoundException;
import com.springapi.orders.domain.interfaces.Pagamento;
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
public class PedidoService {

    private final PedidoRepository repository;

    @Transactional(readOnly = true)
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
    public Pedido getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", id));
    }

    @Transactional
    public Pedido iniciarPedido(){
        Pedido pedido = new Pedido();
        repository.save(pedido);
        return pedido;
    }

    @Transactional
    public PedidoDto adicionarItem(Long id, Produto produto, int quantidade){
        Pedido pedido = this.getById(id);
        pedido.adicionarItem(produto,quantidade);
        return PedidoDto.from(pedido);
    }

    @Transactional
    public PedidoDto removerItem(Long id, Produto produto, int quantidade){
        Pedido pedido = this.getById(id);
        pedido.removerItem(produto,quantidade);
        return PedidoDto.from(pedido);
    }

    @Transactional
    public PedidoDto pagarPedido(Long id, Pagamento metodoPagamento){
        Pedido pedido = this.getById(id);
        pedido.pagar(metodoPagamento);
        return PedidoDto.from(pedido);
    }
}
