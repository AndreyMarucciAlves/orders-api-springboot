package com.springapi.orders.domain.entities.models.pagamentos;

import com.springapi.orders.domain.interfaces.Pagamento;

public class Dinheiro implements Pagamento {
    public boolean pagar(double valor) {
        return valor > 0;
    }
}
