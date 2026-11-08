package com.springapi.orders.presentation.controllers;

import com.springapi.orders.domain.entities.DTOs.PedidoDto;
import com.springapi.orders.domain.entities.DTOs.pageable.FilterPageable;
import com.springapi.orders.domain.entities.DTOs.pageable.ResponsePagedCommon;
import com.springapi.orders.domain.entities.models.Pedido;
import com.springapi.orders.domain.entities.cardapio.Produto;
import com.springapi.orders.domain.interfaces.Pagamento;

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

    @GetMapping("/pedidos/{idPedido}")
    public ResponseEntity<Pedido> getPorId(@PathVariable Long idPedido){
        return ResponseEntity.ok(service.getById(idPedido));
    }

    @PostMapping("/iniciarPedido")
    public ResponseEntity<Pedido> iniciarPedido(){
        return ResponseEntity.ok(service.iniciarPedido());
    }

    @PatchMapping("/adicionarItem/{idPedido}")
    public PedidoDto adicionarItem(@PathVariable Long idPedido, @RequestParam Produto produto, @RequestParam int quantidade){
        return service.adicionarItem(idPedido, produto, quantidade);
    }

    @PatchMapping("/removerItem/{idPedido}")
    public PedidoDto removerItem(@PathVariable Long idPedido, @RequestParam Produto produto, @RequestParam int quantidade){
        return service.removerItem(idPedido, produto, quantidade);
    }

    @PatchMapping("/pagar/{idPedido}")
    public PedidoDto pagar(@PathVariable Long idPedido, @RequestParam Pagamento metodoPagamento){
        return service.pagarPedido(idPedido, metodoPagamento);
    }

}
