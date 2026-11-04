package com.springapi.orders.web;

import com.springapi.orders.entities.models.Produto;
import com.springapi.orders.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<String>> getProdutos() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }
}
