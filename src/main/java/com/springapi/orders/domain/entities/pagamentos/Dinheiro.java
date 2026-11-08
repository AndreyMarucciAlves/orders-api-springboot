package com.springapi.orders.domain.entities.pagamentos;

import com.springapi.orders.domain.entities.models.Pedido;
import com.springapi.orders.domain.interfaces.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class Dinheiro implements Pagamento {
    @Override
    public String processarPagamento(Pedido pedido) {
        return "[Dinheiro] Método de pagamento aceito! Assim que o pedido chegar receberemos o dinheiro! Valor total: "
                + pedido.valorTotal() + ". OBS: Não trabalhamos com troco.";
    }
}
