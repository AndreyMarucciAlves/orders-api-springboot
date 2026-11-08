package com.springapi.orders.domain.interfaces;

import com.springapi.orders.domain.entities.models.Pedido;

public interface Pagamento {
    /**
     * Paga um pedido.
     * @return comprovante de pagamento.
     */
    String processarPagamento(Pedido pedido);

    default String getNome(){ return this.getClass().getSimpleName();}
}