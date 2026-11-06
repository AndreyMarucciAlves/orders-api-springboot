package com.springapi.orders.presentation.controllers;

import com.springapi.orders.domain.entities.models.pedido.Pedido;
import com.springapi.orders.domain.entities.models.cardapio.Produto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;


import com.springapi.orders.domain.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping("/iniciarPedido")
    public ResponseEntity<Pedido> iniciarPedido(){
        return ResponseEntity.ok(service.iniciarPedido());
    }

    @PatchMapping("/adicionarItem/{idPedido}")
    public Pedido adicionarItem(
            @PathVariable Long idPedido,
            @Parameter(schema = @Schema(allowableValues = {"Pizza", "Hamburguer", "Bebida"}))
            @RequestParam Produto produto,
            @RequestParam int quantidade
    ){
        return service.adicionarItem(idPedido, produto, quantidade);
    }
}
