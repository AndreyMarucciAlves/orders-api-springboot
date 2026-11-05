package com.springapi.orders.entities.models.pagamentos;

import com.springapi.orders.interfaces.Pagamento;

public class Dinheiro implements Pagamento {
    public boolean pagar(double valor) {
        return valor > 0;
    }
}
