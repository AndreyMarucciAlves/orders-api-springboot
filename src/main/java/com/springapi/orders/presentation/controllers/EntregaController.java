package com.springapi.orders.presentation.controllers;

import com.springapi.orders.domain.entities.DTOs.pedido.EntregaResponseDto;
import com.springapi.orders.domain.interfaces.services.IEntregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EntregaController {

    private final IEntregaService service;

    @PatchMapping("/despachar-pedido/{idPedido}")
    public ResponseEntity<EntregaResponseDto> despacharPedido(@PathVariable Long idPedido){
        return ResponseEntity.ok(service.despachar(idPedido));
    }

    @PatchMapping("/marcar-como-recebido/{idPedido}")
    public ResponseEntity<EntregaResponseDto> marcarComoRecebido(@PathVariable Long idPedido){
        return ResponseEntity.ok(service.confirmarEntrega(idPedido));
    }
}
