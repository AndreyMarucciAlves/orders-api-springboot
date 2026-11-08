package com.springapi.orders.infrastructure.client.pagamentos;

import com.springapi.orders.domain.entities.models.Pedido;
import com.springapi.orders.domain.interfaces.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class PIX implements Pagamento {
    @Override
    public String processarPagamento(Pedido pedido) {
        return "[Pix] Pagamento aprovado! Valor total: " + pedido.valorTotal();
    }
}
