package com.springapi.orders.web;

import com.springapi.orders.entities.models.cardapio.Produto;
import com.springapi.orders.repository.CardapioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardapioController {

    private final CardapioRepository cardapioRepository;

    public CardapioController(CardapioRepository produtoRepository) {
        this.cardapioRepository = produtoRepository;
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> getProdutos() {
        return ResponseEntity.ok(cardapioRepository.findAll());
    }
}
