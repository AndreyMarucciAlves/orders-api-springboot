package com.springapi.orders.configuration;

import com.springapi.orders.entities.models.cardapio.Cardapio;
import com.springapi.orders.entities.models.cardapio.Produto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConverter implements Converter<String, Produto> {

    /**
     * Converte de String para Produto no controller, permitindo a seleção de produtos na rota de adicionar item.
     * (Fiz esse método para poder utilizar as classes da atividade!)
     * @param nome nome do produto
     * @return Produto com o nome inserido no parâmetro
     */
    @Override
    public Produto convert(String nome) {
        return Cardapio.getProdutos().stream()
                .filter(produto -> produto.getName().equals(nome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + nome));
    }
}