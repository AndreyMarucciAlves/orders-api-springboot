package com.springapi.orders.web.controllers;

import com.springapi.orders.entities.models.cardapio.Cardapio;
import com.springapi.orders.entities.models.cardapio.Produto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cardapio")
@AllArgsConstructor
public class CardapioController {

    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> getProdutos() {
        return ResponseEntity.ok(Cardapio.getProdutos());
    }
}
