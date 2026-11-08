package com.springapi.orders.domain.interfaces.services;

import com.springapi.orders.domain.entities.DTOs.relatorios.PedidoLucroDto;
import com.springapi.orders.domain.entities.DTOs.relatorios.VendaPorMetodoPagamentoDto;
import com.springapi.orders.domain.entities.DTOs.relatorios.VendasPorProdutoDto;

import java.util.List;

public interface IRelatorioService {
    List<VendasPorProdutoDto> getVendasPorProduto();
    List<VendaPorMetodoPagamentoDto> getVendasPorMetodoPamento();
    List<PedidoLucroDto> getPedidosMaisLucrativos();
}
