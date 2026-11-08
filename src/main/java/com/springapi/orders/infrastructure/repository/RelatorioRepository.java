package com.springapi.orders.infrastructure.repository;

import com.springapi.orders.domain.entities.DTOs.relatorios.PedidoLucroDto;
import com.springapi.orders.domain.entities.DTOs.relatorios.VendaPorMetodoPagamentoDto;
import com.springapi.orders.domain.entities.DTOs.relatorios.VendasPorProdutoDto;
import com.springapi.orders.domain.entities.models.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RelatorioRepository extends Repository<Pedido,Long> {

    @Query("""
        SELECT i.nomeProduto AS produto,
               SUM(i.quantidade) AS quantidade,
               SUM(i.preco * i.quantidade) AS receita
        FROM Pedido p JOIN p.itens i
        WHERE p.status <> "ABERTO"
        GROUP BY i.nomeProduto
        ORDER BY SUM(i.quantidade) DESC
    """)
    List<VendasPorProdutoDto> vendasPorProduto();

    @Query("""
            SELECT p.metodoPagamento AS metodo,
                   COUNT(p) AS quantidade
            FROM Pedido p
            WHERE p.status <> "ABERTO"
            GROUP BY p.metodoPagamento
            ORDER BY COUNT(p) DESC
        """)
    List<VendaPorMetodoPagamentoDto> vendasPorMetodoPagamento();

    @Query("""
        SELECT p.id,
               p.metodoPagamento,
               p.status,
               SUM(i.quantidade),
               SUM(i.preco * i.quantidade)
        FROM Pedido p JOIN p.itens i
        WHERE p.status <> "ABERTO"
        GROUP BY p.id, p.metodoPagamento, p.status
        ORDER BY SUM(i.preco * i.quantidade) DESC
    """)
    List<PedidoLucroDto> pedidosMaisLucrativos();
}
