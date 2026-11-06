package com.springapi.orders.infrastructure.repository;

import com.springapi.orders.domain.entities.models.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> { }
