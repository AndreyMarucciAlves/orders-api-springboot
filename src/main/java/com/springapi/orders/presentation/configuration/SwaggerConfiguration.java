package com.springapi.orders.presentation.configuration;

import com.springapi.orders.domain.entities.cardapio.Produto;
import com.springapi.orders.domain.interfaces.Notificacao;
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
     * Estes bean permitem o Open/Closed dos métodos de pagamento, lista de produtos e serviçoes de notificação.
     * Basta marcá-lo como @Component que ele será adicionado automáticamente ao sistema pelo Spring.
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

    @Bean
    public OperationCustomizer opcoesDeNotificacao(List<Notificacao> notificacoes) {
        List<String> nomes = notificacoes.stream().map(Notificacao::getNome).toList();

        return (operation, handlerMethod) -> {
            if (operation.getParameters() != null) {
                operation.getParameters().stream()
                        .filter(parametro -> parametro.getName().equals("meioNotificacao"))
                        .forEach(parametro -> parametro.setSchema(new StringSchema()._enum(nomes)));
            }
            return operation;
        };
    }
}