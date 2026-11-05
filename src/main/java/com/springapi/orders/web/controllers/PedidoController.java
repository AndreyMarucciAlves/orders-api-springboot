package com.springapi.orders.web.controllers;

import com.springapi.orders.entities.models.Pedido;
import com.springapi.orders.entities.models.cardapio.Produto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;


import com.springapi.orders.service.PedidoService;
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
