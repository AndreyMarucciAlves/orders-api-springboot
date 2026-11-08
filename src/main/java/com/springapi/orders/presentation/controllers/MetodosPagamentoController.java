package com.springapi.orders.presentation.controllers;

import com.springapi.orders.domain.interfaces.clients.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metodosPagamento")
@RequiredArgsConstructor
public class MetodosPagamentoController {

    private final List<Pagamento> pagamentos; // Simulando um repository

    @GetMapping
    public ResponseEntity<List<Pagamento>> getMetodosPagamento(){
        return ResponseEntity.ok(pagamentos);
    }
}
