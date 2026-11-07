# Orders API

API REST de pedidos de uma lanchonete, feita com Spring Boot. Permite consultar o cardápio e os métodos de pagamento, abrir um pedido, adicionar e remover itens e pagar o pedido.

O projeto também serve para aplicar os princípios SOLID, em especial o Open/Closed: novos produtos e novos métodos de pagamento entram no sistema sem alterar o código existente.

**Grupo:** Andrey Marucci Alves

## Tecnologias

- Java 21
- Spring Boot 4.1 (Web MVC e Data JPA)
- PostgreSQL 18 (via Docker Compose)
- Lombok
- springdoc-openapi (Swagger UI)
- Maven (com Maven Wrapper)

## Como executar

Pré-requisitos: JDK 21 e Docker.

```bash
./mvnw spring-boot:run
```

No Windows, use `mvnw.cmd spring-boot:run`.

O `spring-boot-docker-compose` sobe o PostgreSQL definido em `compose.yaml` junto com a aplicação, e as tabelas são criadas pelo Hibernate (`ddl-auto=update`).

Com a aplicação no ar:

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## Rotas

### Cardápio e pagamentos

| Método | Rota | Descrição |
| --- | --- | --- |
| GET | `/cardapio/produtos` | Lista os produtos do cardápio |
| GET | `/metodosPagamento/metodosPagamento` | Lista os métodos de pagamento |

### Pedidos

| Método | Rota | Parâmetros | Descrição |
| --- | --- | --- | --- |
| GET | `/pedido/pedidos` | `page`, `size`, `direction`, `orderBy` | Lista os pedidos com paginação |
| GET | `/pedido/pedidos/{idPedido}` | | Busca um pedido pelo id |
| POST | `/pedido/iniciarPedido` | | Abre um novo pedido |
| PATCH | `/pedido/adicionarItem/{idPedido}` | `produto`, `quantidade` | Adiciona um item ao pedido |
| PATCH | `/pedido/removerItem/{idPedido}` | `produto`, `quantidade` | Remove a quantidade informada de um item |
| PATCH | `/pedido/pagar/{idPedido}` | `metodoPagamento` | Paga o pedido |

Os parâmetros são enviados na query string. Em `direction`, use `ASC` ou `DESC`.

### Exemplo de fluxo

```bash
curl -X POST "http://localhost:8080/pedido/iniciarPedido"
```

```bash
curl -X PATCH "http://localhost:8080/pedido/adicionarItem/1?produto=Pizza&quantidade=2"
```

```bash
curl -X PATCH "http://localhost:8080/pedido/pagar/1?metodoPagamento=PIX"
```

Resposta do pagamento:

```json
{
  "id": 1,
  "itens": [
    { "nomeProduto": "Pizza", "preco": 90.0, "quantidade": 2 }
  ],
  "status": "PAGO",
  "metodoPagamento": "PIX",
  "valorTotal": 180.0
}
```

## Cardápio

| Código | Produto | Preço |
| --- | --- | --- |
| 1 | `Bebida` | R$ 15,75 |
| 2 | `Pizza` | R$ 90,00 |
| 3 | `Hamburguer` | R$ 25,50 |
| 4 | `Pastel` | R$ 17,95 |

Métodos de pagamento: `Boleto`, `Cartao`, `Dinheiro` e `PIX`.

Os valores de `produto` e `metodoPagamento` são os nomes acima, com a mesma grafia.

## Regras de negócio

- Todo pedido começa com o status `ABERTO` e passa para `PAGO` ao ser pago.
- A quantidade de um item deve ser maior que zero.
- Adicionar um produto que já está no pedido soma a quantidade ao item existente.
- Um item é retirado do pedido quando a quantidade chega a zero.
- Não é possível remover mais unidades do que o pedido tem.
- Um pedido sem itens não pode ser pago.
- Um pedido pago não aceita novos itens, remoções nem outro pagamento.

## Erros

As respostas de erro são tratadas em `GlobalExceptionHandler`:

| Status | Quando ocorre |
| --- | --- |
| 400 Bad Request | Argumento inválido, como quantidade zero ou produto inexistente |
| 404 Not Found | Pedido não encontrado |
| 409 Conflict | Ação não permitida no estado atual do pedido, como pagar um pedido já pago |

## Estrutura do projeto

```text
src/main/java/com/springapi/orders
├── domain
│   ├── entities      # DTOs e modelos (cardápio, pagamentos, pedido)
│   ├── enums         # StatusPedido
│   ├── exception     # Exceções de domínio
│   ├── interfaces    # Abstração de Pagamento
│   └── service       # Regras de aplicação (PedidoService)
├── infrastructure
│   └── repository    # Acesso ao banco (PedidoRepository)
└── presentation
    ├── configuration            # Conversores e configuração do Swagger
    ├── controllers              # Rotas da API
    └── globalExceptionHandler   # Tratamento de erros
```

## Open/Closed na prática

O Spring injeta todas as implementações de `Produto` e `Pagamento` como listas, que alimentam as rotas de consulta, os conversores dos parâmetros e as opções do Swagger. Para adicionar uma opção, basta criar a classe e marcá-la com `@Component`.

Novo método de pagamento:

```java
@Component
public class Vale implements Pagamento {
}
```

Novo produto:

```java
@Component
public class Suco extends Produto {
    protected Suco() {
        super(5, 12.0);
    }
}
```
