package com.springapi.orders.repository;

import com.springapi.orders.entities.models.cardapio.Cardapio;
import com.springapi.orders.entities.models.cardapio.Produto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Guarda os produtos em memória (SRP: a única responsabilidade desta classe é armazenar/buscar produtos).
 * O @Repository faz o Spring criar uma instância desta classe e entregá-la para quem precisar(o controller).
 */

@Repository
public class CardapioRepository {
    /**
    *Apesar de ferir o O do SOLID ao adicionar um produto novo pela necessidade de vir aqui e incluí-lo
    *nesta lista, faz-se necessário para cumprir com tópico em que se pede para o sistema ter classes que
    *implementem produto - que é abstrato.
    */

    public List<Produto> findAll() {
        return Cardapio.getProdutos();
    }
}
