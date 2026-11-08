package com.springapi.orders.domain.services;

import com.springapi.orders.domain.entities.DTOs.relatorios.PedidoLucroDto;
import com.springapi.orders.domain.entities.DTOs.relatorios.VendaPorMetodoPagamentoDto;
import com.springapi.orders.domain.entities.DTOs.relatorios.VendasPorProdutoDto;
import com.springapi.orders.domain.interfaces.services.IRelatorioService;
import com.springapi.orders.infrastructure.repository.RelatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService implements IRelatorioService {

    private final RelatorioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<VendasPorProdutoDto> getVendasPorProduto(){
        return repository.vendasPorProduto();
    }

    @Transactional(readOnly = true)
    @Override
    public List<VendaPorMetodoPagamentoDto> getVendasPorMetodoPamento(){
        return repository.vendasPorMetodoPagamento();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PedidoLucroDto> getPedidosMaisLucrativos(){
        return repository.pedidosMaisLucrativos();
    }

}
