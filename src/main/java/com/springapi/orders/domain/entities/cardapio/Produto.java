package com.springapi.orders.domain.entities.cardapio;

import lombok.Getter;

/**
 * Classe abstrata para cumprir com o requisito da atividade. O ideal seria utilizar uma
 * tabela no banco apenas para os produtos. Com isso eu poderia adicionar, remover, editar e
 * consultas os produtor (CRUD).
 */
public abstract class Produto {
    @Getter
    private final int codigo;

    @Getter
    private final double preco;

    protected Produto(int codigo,double preco){
        this.codigo = codigo;
        this.preco = preco;
    }

    /**
     * Função para os produtos retornarem o seu nome na API, permitindo adicionar no pedido.
     * @return nome da classe.
     */
    public String getName(){ return this.getClass().getSimpleName(); }
}
