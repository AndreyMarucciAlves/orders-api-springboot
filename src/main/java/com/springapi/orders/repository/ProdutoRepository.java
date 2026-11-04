package com.springapi.orders.repository;

import com.springapi.orders.entities.models.Bebida;
import com.springapi.orders.entities.models.Hamburguer;
import com.springapi.orders.entities.models.Pizza;
import com.springapi.orders.entities.models.Produto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda os produtos em memória (SRP: a única responsabilidade desta classe é armazenar/buscar produtos).
 * O @Repository faz o Spring criar uma instância desta classe e entregá-la para quem precisar(o controller).
 */

@Repository
public class ProdutoRepository {
    /**
    *Apesar de ferir o O do SOLID ao adicionar um produto novo pela necessidade de vir aqui e incluí-lo
    *nesta lista, faz-se necessário para cumprir com tópico em que se pede para o sistema ter classes que
    *implementem produto - que é abstrato.
    */
     private final List<Produto> implementacoesProduto = List.of(
            new Pizza(),
            new Hamburguer(),
            new Bebida()
    );

    public List<String> findAll() {
        List<String> produtos = new ArrayList<>();

        for(Produto p : implementacoesProduto){
            produtos.add(p.getName());
        }
        return produtos;
    }
}
