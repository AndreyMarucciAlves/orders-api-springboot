package com.springapi.orders.repository;

import com.springapi.orders.entities.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Pedido getById(Long id);
}
