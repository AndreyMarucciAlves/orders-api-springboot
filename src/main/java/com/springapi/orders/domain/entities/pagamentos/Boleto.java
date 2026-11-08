package com.springapi.orders.domain.entities.pagamentos;

import com.springapi.orders.domain.entities.models.Pedido;
import com.springapi.orders.domain.interfaces.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class Boleto implements Pagamento {

    public String processarPagamento(Pedido pedido) {
        return "[Boleto] Pagamento aprovado! Valor total do pedido: " + pedido.valorTotal();
    }
}
