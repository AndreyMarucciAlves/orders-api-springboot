package com.springapi.orders.presentation.configuration;

import com.springapi.orders.domain.entities.models.cardapio.Produto;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OperationCustomizer;

import java.util.List;
import com.springapi.orders.domain.interfaces.Pagamento;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Orders")
                        .version("1.0.0"));
    }

    /**
     * Este bean permite o Open/Closed dos métodos de pagamento. Basta marcá-lo como @Component que ele será adicionado
     * automáticamente ao sistema de pagamentos.
     * @param pagamentos métodos de pagamento do sistema
     * @return Seleção de itens da rota PATCH /pagar
     */
    @Bean
    public OperationCustomizer opcoesDePagamento(List<Pagamento> pagamentos) {
        List<String> nomes = pagamentos.stream().map(Pagamento::getNome).toList();

        return (operation, handlerMethod) -> {
            if (operation.getParameters() != null) {
                operation.getParameters().stream()
                        .filter(parametro -> parametro.getName().equals("metodoPagamento"))
                        .forEach(parametro -> parametro.setSchema(new StringSchema()._enum(nomes)));
            }
            return operation;
        };
    }

    /**
     * Este bean permite o Open/Closed dos produtos. Basta marcá-lo como @Component que ele será adicionado
     * automáticamente ao sistema de Pedidos.
     * @param produtos implementações concretas da abstração de Produto
     * @return Seleção de itens da rota PATCH /adicionarItem
     */
    @Bean
    public OperationCustomizer opcoesDeProdutos(List<Produto> produtos) {
        List<String> nomes = produtos.stream().map(Produto::getName).toList();

        return (operation, handlerMethod) -> {
            if (operation.getParameters() != null) {
                operation.getParameters().stream()
                        .filter(parametro -> parametro.getName().equals("produto"))
                        .forEach(parametro -> parametro.setSchema(new StringSchema()._enum(nomes)));
            }
            return operation;
        };
    }
}