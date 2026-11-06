package com.springapi.orders.presentation.controllers;

import com.springapi.orders.domain.entities.DTOs.PedidoDto;
import com.springapi.orders.domain.entities.DTOs.pageable.FilterPageable;
import com.springapi.orders.domain.entities.DTOs.pageable.ResponsePagedCommon;
import com.springapi.orders.domain.entities.models.pedido.Pedido;
import com.springapi.orders.domain.entities.models.cardapio.Produto;
import com.springapi.orders.domain.interfaces.Pagamento;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import com.springapi.orders.domain.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @GetMapping("/pedidos")
    public ResponseEntity<ResponsePagedCommon<PedidoDto>> getPedidosPageable(@Valid FilterPageable filterPageable){
        return ResponseEntity.ok(service.getPedidosPageable(filterPageable));
    }

    @GetMapping("/pedidos{idPedido}")
    public ResponseEntity<Pedido> getPorId(@RequestParam Long idPedido){
        return ResponseEntity.ok(service.getById(idPedido));
    }

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

    @PatchMapping("/pagar/{idPedido}")
    public Pedido pagar(
            @PathVariable Long idPedido,
            @RequestParam Pagamento metodoPagamento
    ){
        return service.pagarPedido(idPedido, metodoPagamento);
    }
}
