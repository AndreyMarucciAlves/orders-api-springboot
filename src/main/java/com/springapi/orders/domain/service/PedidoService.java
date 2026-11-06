package com.springapi.orders.domain.service;

import com.springapi.orders.domain.entities.models.pedido.Pedido;
import com.springapi.orders.domain.entities.models.cardapio.Produto;
import com.springapi.orders.domain.exception.ResourceNotFoundException;
import com.springapi.orders.infrastructure.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;

    /**
     * Função Auxiliar para buscar um pedido pelo id, caso não encontre lança uma exception personalizada
     * @param id id do pedido procurado
     * @return pedido
     * @throws ResourceNotFoundException caso não encontre o pedido procurado.
     */
    private Pedido getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", id));
    }

    /**
     * Apenas abre o pedido
     * @return pedidio iniciado
     */
    @Transactional
    public Pedido iniciarPedido(){
        Pedido pedido = new Pedido();
        repository.save(pedido);
        return pedido;
    }

    /**
     * Adiciona itens ao pedido
     * @param id id do pedido para adicionar itens
     * @param produto produto a ser adicionado ao pedido
     * @param quantidade quantidade do produto a ser adicionada ao pedido
     * @return status atual do pedido
     */
    @Transactional
    public Pedido adicionarItem(Long id, Produto produto, int quantidade){
        Pedido pedido = this.getById(id);
        pedido.adicionarItem(produto,quantidade);
        return pedido;
    }
}
