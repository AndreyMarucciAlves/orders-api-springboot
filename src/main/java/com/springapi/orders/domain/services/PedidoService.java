package com.springapi.orders.domain.services;

import com.springapi.orders.domain.entities.DTOs.pedido.PedidoDto;
import com.springapi.orders.domain.entities.DTOs.pedido.PagamentoResponseDto;
import com.springapi.orders.domain.entities.DTOs.pageable.FilterPageable;
import com.springapi.orders.domain.entities.DTOs.pageable.ResponsePagedCommon;
import com.springapi.orders.domain.entities.models.Pedido;
import com.springapi.orders.domain.entities.cardapio.Produto;
import com.springapi.orders.domain.exception.ResourceNotFoundException;
import com.springapi.orders.domain.interfaces.clients.Notificacao;
import com.springapi.orders.domain.interfaces.clients.Pagamento;
import com.springapi.orders.domain.interfaces.services.INotificacaoService;
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
    private final INotificacaoService notificacaoService;

    @Transactional(readOnly = true)
    @Override
    public ResponsePagedCommon<PedidoDto> getPedidosPageable(FilterPageable filterPageable){
        Page<Pedido> listPedidosDB = repository.findAll(
                PageRequest.of(
                        filterPageable.getPage(), filterPageable.getSize(),
                        Sort.by(filterPageable.getDirection(), filterPageable.getOrderBy())
                )
        );

        List<PedidoDto> listPedidosResponse = listPedidosDB.stream().map(PedidoDto::from).toList();

        return new ResponsePagedCommon<PedidoDto>(
            listPedidosResponse,
            listPedidosDB.getTotalElements(),
            listPedidosDB.getTotalPages(),
            filterPageable.getSize()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PedidoDto getById(Long id) {
        return PedidoDto.from(buscarPedido(id));
    }

    @Transactional
    @Override
    public PedidoDto iniciarPedido(){
        Pedido pedido = repository.save(new Pedido());
        return PedidoDto.from(pedido);
    }

    @Transactional
    @Override
    public PedidoDto adicionarItem(Long id, Produto produto, int quantidade){
        Pedido pedido = buscarPedido(id);
        pedido.adicionarItem(produto,quantidade);
        return PedidoDto.from(pedido);
    }

    @Transactional
    @Override
    public PedidoDto removerItem(Long id, Produto produto, int quantidade){
        Pedido pedido = buscarPedido(id);
        pedido.removerItem(produto,quantidade);
        return PedidoDto.from(pedido);
    }

    @Transactional
    @Override
    public PagamentoResponseDto pagarPedido(Long id, Pagamento metodoPagamento, Notificacao meioNotificacao){
        Pedido pedido = buscarPedido(id);

        pedido.marcarComoPago(metodoPagamento, meioNotificacao);
        String comprovante = metodoPagamento.processarPagamento(pedido);
        String mensagem = notificacaoService.notificarCliente(
                meioNotificacao,"Pagamento recebido. Pedido número " + id + " sendo preparado para o envio!");

        return new PagamentoResponseDto(PedidoDto.from(pedido), mensagem, comprovante);
    }

    private Pedido buscarPedido(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", id));
    }
}
