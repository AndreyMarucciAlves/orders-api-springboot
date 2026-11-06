package com.springapi.orders.presentation.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import com.springapi.orders.domain.interfaces.Pagamento;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PagamentoConverter implements Converter<String, Pagamento> {

    private final List<Pagamento> pagamentos;

    /**
     * Converte de String para Pagamento no POST /pagamento de pedido, permitindo a seleção de métodos de pagamento.
     * (Fiz esse método para poder utilizar as classes da atividade!)
     * @param nome nome do produto
     * @return Produto com o nome inserido no parâmetro
     */
    @Override
    public Pagamento convert(String nome) {
        return pagamentos.stream()
                .filter(pagamento -> pagamento.getClass().getSimpleName().equals(nome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Método de pagamento não encontrado: " + nome));
    }
}
