package com.springapi.orders.presentation.controllers;

import com.springapi.orders.domain.entities.DTOs.relatorios.PedidoLucroDto;
import com.springapi.orders.domain.entities.DTOs.relatorios.VendaPorMetodoPagamentoDto;
import com.springapi.orders.domain.entities.DTOs.relatorios.VendasPorProdutoDto;
import com.springapi.orders.domain.interfaces.services.IRelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RelatorioController {

    private final IRelatorioService service;

    @GetMapping("/vendas-por-produto")
    public ResponseEntity<List<VendasPorProdutoDto>> getVendasPorProduto(){
        return ResponseEntity.ok(service.getVendasPorProduto());
    }

    @GetMapping("/metodo-pagamento-mais-usado")
    public ResponseEntity<List<VendaPorMetodoPagamentoDto>> getMetodosPagamentosMaisUsados(){
        return ResponseEntity.ok(service.getVendasPorMetodoPamento());
    }

    @GetMapping("/pedidos-mais-lucrativos")
    public ResponseEntity<List<PedidoLucroDto>> getPedidosMaisLucrativos(){
        return ResponseEntity.ok(service.getPedidosMaisLucrativos());
    }
}
