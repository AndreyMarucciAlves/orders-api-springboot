package com.springapi.orders.domain.entities.pagamentos;

import com.springapi.orders.domain.entities.models.Pedido;
import com.springapi.orders.domain.interfaces.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class Cartao implements Pagamento {
    @Override
    public String processarPagamento(Pedido pedido) {
        return "[Cartão] Pagamento aprovado! Valor total: " + pedido.valorTotal();
    }
}
