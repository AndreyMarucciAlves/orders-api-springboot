package com.springapi.orders.presentation.configuration;

import com.springapi.orders.domain.entities.models.cardapio.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProdutoConverter implements Converter<String, Produto> {

    private final List<Produto> Cardapio;

    /**
     * Converte de String para Produto no controller, permitindo a seleção de produtos na rota de adicionar item.
     * (Fiz esse método para poder utilizar as classes da atividade!)
     * @param nome nome do produto
     * @return Produto com o nome inserido no parâmetro
     */
    @Override
    public Produto convert(String nome) {
        return Cardapio.stream()
                .filter(produto -> produto.getName().equals(nome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + nome));
    }
}