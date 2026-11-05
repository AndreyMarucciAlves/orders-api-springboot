package com.springapi.orders.entities.models.cardapio;

import lombok.Getter;

import java.util.List;

public class Cardapio {

    /**
     *Apesar de ferir o O do SOLID ao adicionar um produto novo pela necessidade de vir aqui e incluí-lo
     *nesta lista, faz-se necessário para cumprir com o tópico em que se pede para o sistema ter classes que
     *implementem produto - que é abstrato.
     */

    @Getter
    private static final List<Produto> produtos = List.of(
            new Pizza(),
            new Hamburguer(),
            new Bebida()
    );
}
