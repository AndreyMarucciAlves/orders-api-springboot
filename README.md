# Orders API

API REST de um sistema de delivery feita com Spring Boot. Ela cobre o ciclo completo de um pedido: abrir, adicionar e remover itens, pagar, despachar e confirmar a entrega, notificando o cliente a cada etapa. Também gera relatórios de vendas.

O projeto foi construído para a atividade **Aplicando os Princípios SOLID**. Este README funciona como um índice: para cada princípio e cada subtópico do enunciado, aponta a classe onde ele está implementado.

**Aluno:** Andrey Marucci Alves

## Sumário

- [Enunciado](#enunciado)
- [Guia de correção: onde encontrar cada princípio](#guia-de-correção-onde-encontrar-cada-princípio)
  - [1. SRP: Single Responsibility](#1-srp-single-responsibility)
  - [2. OCP: Open/Closed](#2-ocp-openclosed)
  - [3. LSP: Liskov Substitution](#3-lsp-liskov-substitution)
  - [4. ISP: Interface Segregation](#4-isp-interface-segregation)
  - [5. DIP: Dependency Inversion](#5-dip-dependency-inversion)
- [Pontos fortes do projeto](#pontos-fortes-do-projeto)
- [Como executar](#como-executar)
- [Rotas](#rotas)
- [Exemplo de fluxo completo](#exemplo-de-fluxo-completo)
- [Regras de negócio](#regras-de-negócio)
- [Estrutura do projeto](#estrutura-do-projeto)

## Enunciado

<details>
<summary><b>Atividade: Aplicando os Princípios SOLID</b> (clique para expandir)</summary>

**1. Single Responsibility Principle (SRP)**
Você deverá implementar funcionalidades relacionadas ao gerenciamento de pedidos do sistema de delivery.
O sistema deverá:
- criar um pedido
- salvar um pedido
- exibir as informações de um pedido

Porém, o primeiro princípio do SOLID deverá ser respeitado. As responsabilidades deverão ser separadas corretamente entre as classes.

**2. Open/Closed Principle (OCP)**
Você deverá implementar diferentes formas de pagamento para o sistema.
O sistema deverá possuir pelo menos:
- pagamento via cartão
- pagamento via PIX
- pagamento via dinheiro

O sistema deverá permitir adicionar novos tipos de pagamento futuramente sem alterar o código já existente.

**3. Liskov Substitution Principle (LSP)**
Você deverá implementar diferentes tipos de produtos do restaurante.
O sistema deverá possuir uma classe principal de produto e subclasses como:
- Pizza
- Hamburguer
- Bebida

Todas as subclasses deverão poder ser utilizadas normalmente no lugar da classe principal sem causar comportamentos incorretos.

**4. Interface Segregation Principle (ISP)**
Você deverá implementar diferentes responsabilidades do sistema utilizando interfaces específicas.
O sistema deverá possuir funcionalidades como:
- envio de notificações
- geração de relatórios
- gerenciamento de pedidos
- gerenciamento de entregas

As classes não deverão implementar métodos que não utilizam.

**5. Dependency Inversion Principle (DIP)**
Você deverá implementar um sistema de notificações desacoplado.
O sistema deverá permitir o envio de notificações utilizando diferentes meios, como:
- Email
- SMS
- WhatsApp

A classe principal do sistema não poderá depender diretamente de implementações concretas, mas sim de abstrações/interfaces.

**Entrega**
Você deverá subir a atividade no github e realizar a entrega no classroom do link.

</details>

## Guia de correção: onde encontrar cada princípio

Todos os caminhos abaixo partem de `src/main/java/com/springapi/orders/`.

### 1. SRP: Single Responsibility

**Requisito:** criar, salvar e exibir um pedido, com as responsabilidades separadas entre as classes.

| Subtópico | Onde ver | Como funciona |
| --- | --- | --- |
| Criar um pedido | [`PedidoController`](src/main/java/com/springapi/orders/presentation/controllers/PedidoController.java) → `POST /pedido/iniciarPedido`<br>[`PedidoService.iniciarPedido()`](src/main/java/com/springapi/orders/domain/services/PedidoService.java#L59) | O controller só recebe a requisição; o service cria o `Pedido`, manda salvar e devolve um `PedidoDto`. |
| Salvar um pedido | [`PedidoRepository`](src/main/java/com/springapi/orders/infrastructure/repository/PedidoRepository.java) | Única classe que fala com o banco (Spring Data JPA). As alterações feitas dentro de um `@Transactional` são gravadas automaticamente. |
| Exibir as informações | `GET /pedido/pedidos/{id}` e `GET /pedido/pedidos` (paginado)<br>[`PedidoDto`](src/main/java/com/springapi/orders/domain/entities/DTOs/pedido/PedidoDto.java) | O DTO monta a resposta (itens, status, pagamento, notificação, valor total) sem expor regras da entidade. |

Cada camada tem uma responsabilidade só:

| Classe | Responsabilidade única |
| --- | --- |
| [`PedidoController`](src/main/java/com/springapi/orders/presentation/controllers/PedidoController.java) | Receber HTTP e devolver a resposta |
| [`PedidoService`](src/main/java/com/springapi/orders/domain/services/PedidoService.java) | Orquestrar o caso de uso e controlar a transação |
| [`Pedido`](src/main/java/com/springapi/orders/domain/entities/models/Pedido.java) | Regras do próprio pedido (itens, valor total, transições de status) |
| [`PedidoRepository`](src/main/java/com/springapi/orders/infrastructure/repository/PedidoRepository.java) | Persistência |
| [`GlobalExceptionHandler`](src/main/java/com/springapi/orders/presentation/globalExceptionHandler/GlobalExceptionHandler.java) | Traduzir exceções em respostas HTTP |
| [`ProdutoConverter`](src/main/java/com/springapi/orders/presentation/configuration/ProdutoConverter.java), [`PagamentoConverter`](src/main/java/com/springapi/orders/presentation/configuration/PagamentoConverter.java), [`NotificacaoConverter`](src/main/java/com/springapi/orders/presentation/configuration/NotificacaoConverter.java) | Converter o texto do parâmetro na implementação correspondente |

### 2. OCP: Open/Closed

**Requisito:** pagamento via cartão, PIX e dinheiro, permitindo novos tipos sem alterar código existente.

| Subtópico | Onde ver |
| --- | --- |
| Abstração | [`domain/interfaces/clients/Pagamento.java`](src/main/java/com/springapi/orders/domain/interfaces/clients/Pagamento.java) |
| Pagamento via cartão | [`infrastructure/client/pagamentos/Cartao.java`](src/main/java/com/springapi/orders/infrastructure/client/pagamentos/Cartao.java) |
| Pagamento via PIX | [`infrastructure/client/pagamentos/PIX.java`](src/main/java/com/springapi/orders/infrastructure/client/pagamentos/PIX.java) |
| Pagamento via dinheiro | [`infrastructure/client/pagamentos/Dinheiro.java`](src/main/java/com/springapi/orders/infrastructure/client/pagamentos/Dinheiro.java) |
| Extra: boleto | [`infrastructure/client/pagamentos/Boleto.java`](src/main/java/com/springapi/orders/infrastructure/client/pagamentos/Boleto.java), que já foi adicionado seguindo o OCP |
| Extensão sem alteração | [`PagamentoConverter`](src/main/java/com/springapi/orders/presentation/configuration/PagamentoConverter.java), [`SwaggerConfiguration`](src/main/java/com/springapi/orders/presentation/configuration/SwaggerConfiguration.java), [`MetodosPagamentoController`](src/main/java/com/springapi/orders/presentation/controllers/MetodosPagamentoController.java) |

**Como funciona:** o Spring injeta todas as implementações de `Pagamento` como uma `List<Pagamento>`. Essa lista alimenta o conversor do parâmetro `metodoPagamento`, a rota que lista os métodos e as opções do Swagger. Nenhuma dessas classes cita `Cartao`, `PIX` ou `Dinheiro` pelo nome.

Para adicionar um meio de pagamento, basta criar a classe. Ela aparece automaticamente na API e no Swagger:

```java
@Component
public class Vale implements Pagamento {
    @Override
    public String processarPagamento(Pedido pedido) {
        return "[Vale] Pagamento aprovado! Valor total: " + pedido.valorTotal();
    }
}
```

### 3. LSP: Liskov Substitution

**Requisito:** uma classe principal de produto e subclasses (Pizza, Hamburguer, Bebida) que funcionem no lugar dela sem comportamento incorreto.

| Subtópico | Onde ver |
| --- | --- |
| Classe principal | [`domain/entities/cardapio/Produto.java`](src/main/java/com/springapi/orders/domain/entities/cardapio/Produto.java) (abstrata) |
| Pizza | [`Pizza.java`](src/main/java/com/springapi/orders/domain/entities/cardapio/Pizza.java) |
| Hamburguer | [`Hamburguer.java`](src/main/java/com/springapi/orders/domain/entities/cardapio/Hamburguer.java) |
| Bebida | [`Bebida.java`](src/main/java/com/springapi/orders/domain/entities/cardapio/Bebida.java) |
| Extra: pastel | [`Pastel.java`](src/main/java/com/springapi/orders/domain/entities/cardapio/Pastel.java) |
| Substituição na prática | [`Pedido.adicionarItem(Produto, int)`](src/main/java/com/springapi/orders/domain/entities/models/Pedido.java#L39), [`CardapioController`](src/main/java/com/springapi/orders/presentation/controllers/CardapioController.java) (`List<Produto>`), [`ProdutoConverter`](src/main/java/com/springapi/orders/presentation/configuration/ProdutoConverter.java) |

**Como funciona:** todo o sistema trabalha com o tipo `Produto`, nunca com uma subclasse específica. As subclasses só definem código e preço, sem sobrescrever comportamento nem enfraquecer contratos. Por isso qualquer uma delas pode ser passada para `adicionarItem`, listada no cardápio ou convertida pelo `ProdutoConverter` com o mesmo resultado esperado.

### 4. ISP: Interface Segregation

**Requisito:** interfaces específicas para notificações, relatórios, pedidos e entregas, sem classes implementando métodos que não usam.

As interfaces dos services ficam em [`domain/interfaces/services/`](src/main/java/com/springapi/orders/domain/interfaces/services):

| Funcionalidade | Interface | Implementação | Usada por |
| --- | --- | --- | --- |
| Envio de notificações | [`INotificacaoService`](src/main/java/com/springapi/orders/domain/interfaces/services/INotificacaoService.java) | [`NotificacaoService`](src/main/java/com/springapi/orders/domain/services/NotificacaoService.java) | `PedidoService`, `EntregaService` |
| Geração de relatórios | [`IRelatorioService`](src/main/java/com/springapi/orders/domain/interfaces/services/IRelatorioService.java) | [`RelatorioService`](src/main/java/com/springapi/orders/domain/services/RelatorioService.java) | [`RelatorioController`](src/main/java/com/springapi/orders/presentation/controllers/RelatorioController.java) |
| Gerenciamento de pedidos | [`IPedidoService`](src/main/java/com/springapi/orders/domain/interfaces/services/IPedidoService.java) | [`PedidoService`](src/main/java/com/springapi/orders/domain/services/PedidoService.java) | [`PedidoController`](src/main/java/com/springapi/orders/presentation/controllers/PedidoController.java) |
| Gerenciamento de entregas | [`IEntregaService`](src/main/java/com/springapi/orders/domain/interfaces/services/IEntregaService.java) | [`EntregaService`](src/main/java/com/springapi/orders/domain/services/EntregaService.java) | [`EntregaController`](src/main/java/com/springapi/orders/presentation/controllers/EntregaController.java) |

Além dessas, [`Pagamento`](src/main/java/com/springapi/orders/domain/interfaces/clients/Pagamento.java) e [`Notificacao`](src/main/java/com/springapi/orders/domain/interfaces/clients/Notificacao.java) têm um único método cada.

**Como funciona:** cada interface tem só os métodos da sua funcionalidade, e cada classe implementa só a interface de que precisa. O `EntregaService` não tem nada de relatório ou de pagamento, e o `RelatorioService` não tem nada de entrega. Nenhuma classe precisa deixar método vazio ou lançar `UnsupportedOperationException`.

### 5. DIP: Dependency Inversion

**Requisito:** notificações desacopladas por Email, SMS e WhatsApp, com a classe principal dependendo de abstrações.

| Subtópico | Onde ver |
| --- | --- |
| Abstração | [`domain/interfaces/clients/Notificacao.java`](src/main/java/com/springapi/orders/domain/interfaces/clients/Notificacao.java) |
| Email | [`infrastructure/client/notificacao/Email.java`](src/main/java/com/springapi/orders/infrastructure/client/notificacao/Email.java) |
| SMS | [`infrastructure/client/notificacao/SMS.java`](src/main/java/com/springapi/orders/infrastructure/client/notificacao/SMS.java) |
| WhatsApp | [`infrastructure/client/notificacao/WhatsApp.java`](src/main/java/com/springapi/orders/infrastructure/client/notificacao/WhatsApp.java) |
| Classe principal depende da abstração | [`NotificacaoService`](src/main/java/com/springapi/orders/domain/services/NotificacaoService.java) recebe `List<Notificacao>`; [`PedidoService`](src/main/java/com/springapi/orders/domain/services/PedidoService.java) e [`EntregaService`](src/main/java/com/springapi/orders/domain/services/EntregaService.java) recebem `INotificacaoService` |

**Como funciona:**

- O domínio define o contrato (`Notificacao`) e a infraestrutura o implementa. As setas de dependência apontam para o domínio, nunca o contrário.
- Nenhum service importa `Email`, `SMS` ou `WhatsApp`. O meio é escolhido pelo cliente no pagamento (parâmetro `meioNotificacao`), salvo no pedido e reutilizado nas notificações de entrega.
- O mesmo vale para as outras camadas: os controllers dependem de `IPedidoService`, `IEntregaService` e `IRelatorioService`, não das classes concretas.

O envio é simulado: cada implementação devolve a mensagem formatada (por exemplo, `[WhatsApp] Pedido número 1 em rota de entrega!`), que aparece na resposta da API. Numa aplicação real, essas classes chamariam o serviço externo correspondente.

## Pontos fortes do projeto

**Stack atual**
- **Java 21** e **Spring Boot 4.1.2** (Spring Framework 7, Hibernate 7, Spring Data JPA)
- **PostgreSQL 18** rodando em container
- **springdoc-openapi 3** para documentação interativa
- **Maven Wrapper**: não precisa ter Maven instalado

**Ambiente pronto com um comando**
- O [`compose.yaml`](compose.yaml) define o PostgreSQL com volume persistente e healthcheck.
- O `spring-boot-docker-compose` sobe o container automaticamente junto com a aplicação.
- As tabelas são criadas pelo Hibernate (`ddl-auto=update`).

**Validações e regras de negócio no domínio**
- Fluxo de status controlado pela própria entidade: `ABERTO → PAGO → EM_ENTREGA → ENTREGUE`. Não dá para pular etapas, voltar, pagar duas vezes ou alterar itens depois do pagamento.
- Quantidade deve ser maior que zero, não é possível remover mais unidades do que existem, e um pedido sem itens não pode ser pago.
- Produto, método de pagamento e meio de notificação inexistentes são rejeitados já na conversão do parâmetro.
- Parâmetros de paginação validados com Bean Validation (`@Valid` + `@NotNull` em [`FilterPageable`](src/main/java/com/springapi/orders/domain/entities/DTOs/pageable/FilterPageable.java)).

**Erros padronizados**
- [`GlobalExceptionHandler`](src/main/java/com/springapi/orders/presentation/globalExceptionHandler/GlobalExceptionHandler.java) devolve sempre o mesmo formato (`message`, `code`, `timestamp`):

| Status | Quando ocorre |
| --- | --- |
| 400 Bad Request | Argumento inválido: quantidade zero, produto ou meio de pagamento inexistente |
| 404 Not Found | Pedido não encontrado |
| 409 Conflict | Ação não permitida no status atual, como pagar um pedido já pago ou despachar um pedido aberto |

**Swagger que acompanha o código**
- As opções de `produto`, `metodoPagamento` e `meioNotificacao` aparecem como listas de seleção no Swagger e são geradas a partir dos beans ([`SwaggerConfiguration`](src/main/java/com/springapi/orders/presentation/configuration/SwaggerConfiguration.java)). Uma implementação nova aparece lá sem nenhuma configuração.

**Relatórios calculados no banco**
- [`RelatorioRepository`](src/main/java/com/springapi/orders/infrastructure/repository/RelatorioRepository.java) usa consultas JPQL com agregação (`SUM`, `COUNT`, `GROUP BY`), sem carregar todos os pedidos em memória.
- O resultado vai direto para records (DTOs), e o repositório é somente leitura (estende `Repository`, sem `save` nem `delete`).

**Outros cuidados**
- Arquitetura em camadas (`domain`, `infrastructure`, `presentation`) com o domínio isolado.
- Todas as rotas de pedido, entrega e relatórios respondem com DTOs (records); a entidade `Pedido` nunca é exposta na API.
- Transações explícitas, com `readOnly = true` nas consultas.
- Preço do item gravado no momento da venda: mudar o preço no cardápio não altera pedidos antigos.
- Listagem de pedidos com paginação e ordenação.

## Como executar

Pré-requisitos: **JDK 21** e **Docker** em execução.

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

Com a aplicação no ar:

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## Rotas

### Cardápio e pagamentos

| Método | Rota | Descrição |
| --- | --- | --- |
| GET | `/cardapio/produtos` | Lista os produtos do cardápio |
| GET | `/metodosPagamento` | Lista os métodos de pagamento |

### Pedidos

| Método | Rota | Parâmetros | Descrição |
| --- | --- | --- | --- |
| GET | `/pedido/pedidos` | `page`, `size`, `direction`, `orderBy` | Lista os pedidos com paginação |
| GET | `/pedido/pedidos/{idPedido}` | | Busca um pedido pelo id |
| POST | `/pedido/iniciarPedido` | | Abre um novo pedido |
| PATCH | `/pedido/adicionarItem/{idPedido}` | `produto`, `quantidade` | Adiciona um item ao pedido |
| PATCH | `/pedido/removerItem/{idPedido}` | `produto`, `quantidade` | Remove a quantidade informada de um item |
| PATCH | `/pedido/pagar/{idPedido}` | `metodoPagamento`, `meioNotificacao` | Paga o pedido e notifica o cliente |

### Entregas

| Método | Rota | Descrição |
| --- | --- | --- |
| PATCH | `/despachar-pedido/{idPedido}` | Coloca um pedido pago em rota de entrega e notifica o cliente |
| PATCH | `/marcar-como-recebido/{idPedido}` | Confirma a entrega e notifica o cliente |

### Relatórios

| Método | Rota | Descrição |
| --- | --- | --- |
| GET | `/vendas-por-produto` | Quantidade vendida e receita por produto |
| GET | `/metodo-pagamento-mais-usado` | Quantidade de pedidos por método de pagamento |
| GET | `/pedidos-mais-lucrativos` | Pedidos ordenados pelo valor total |

Os relatórios consideram todos os pedidos que já foram pagos (`PAGO`, `EM_ENTREGA` e `ENTREGUE`).

Os parâmetros são enviados na query string, com os nomes exatamente como aparecem na tabela a seguir. Em `direction`, use `ASC` ou `DESC`.

| Parâmetro | Valores aceitos |
| --- | --- |
| `produto` | `Pizza`, `Hamburguer`, `Bebida`, `Pastel` |
| `metodoPagamento` | `Cartao`, `PIX`, `Dinheiro`, `Boleto` |
| `meioNotificacao` | `Email`, `SMS`, `WhatsApp` |

## Exemplo de fluxo completo

```bash
curl -X POST "http://localhost:8080/pedido/iniciarPedido"
```

```bash
curl -X PATCH "http://localhost:8080/pedido/adicionarItem/1?produto=Pizza&quantidade=2"
```

```bash
curl -X PATCH "http://localhost:8080/pedido/pagar/1?metodoPagamento=PIX&meioNotificacao=WhatsApp"
```

```bash
curl -X PATCH "http://localhost:8080/despachar-pedido/1"
```

```bash
curl -X PATCH "http://localhost:8080/marcar-como-recebido/1"
```

Resposta do pagamento:

```json
{
  "pedido": {
    "id": 1,
    "itens": [
      { "nomeProduto": "Pizza", "preco": 90.0, "quantidade": 2 }
    ],
    "status": "PAGO",
    "metodoPagamento": "PIX",
    "meioNotificacao": "WhatsApp",
    "valorTotal": 180.0
  },
  "notificacao": "[WhatsApp]Pagamento recebido. Pedido número 1 sendo preparado para o envio!",
  "comprovante": "[Pix] Pagamento aprovado! Valor total: 180.0"
}
```

## Regras de negócio

Cardápio:

| Código | Produto | Preço |
| --- | --- | --- |
| 1 | `Bebida` | R$ 15,75 |
| 2 | `Pizza` | R$ 90,00 |
| 3 | `Hamburguer` | R$ 25,50 |
| 4 | `Pastel` | R$ 17,95 |

- Todo pedido começa `ABERTO` e segue `ABERTO → PAGO → EM_ENTREGA → ENTREGUE`.
- Itens só podem ser adicionados ou removidos com o pedido `ABERTO`.
- A quantidade de um item deve ser maior que zero.
- Adicionar um produto que já está no pedido soma a quantidade ao item existente.
- Um item sai do pedido quando a quantidade chega a zero, e não é possível remover mais unidades do que o pedido tem.
- Um pedido sem itens não pode ser pago, e um pedido só pode ser pago uma vez.
- Só pedidos `PAGO` podem ser despachados, e só pedidos `EM_ENTREGA` podem ter a entrega confirmada.
- O cliente é notificado no pagamento, no despacho e na entrega, sempre pelo meio escolhido no pagamento.

## Estrutura do projeto

```text
src/main/java/com/springapi/orders
├── domain
│   ├── entities
│   │   ├── cardapio          # Produto e subclasses (LSP)
│   │   ├── DTOs              # Records de resposta (pedido, relatórios, paginação)
│   │   └── models            # Pedido e ItemPedido
│   ├── enums                 # StatusPedido
│   ├── exception             # Exceções de domínio
│   ├── interfaces
│   │   ├── clients           # Pagamento (OCP) e Notificacao (DIP)
│   │   └── services          # Interfaces dos services (ISP)
│   └── services              # Casos de uso: pedido, entrega, relatório, notificação
├── infrastructure
│   ├── client
│   │   ├── notificacao       # Email, SMS, WhatsApp (DIP)
│   │   └── pagamentos        # Cartao, PIX, Dinheiro, Boleto (OCP)
│   └── repository            # PedidoRepository e RelatorioRepository
└── presentation
    ├── configuration         # Conversores de parâmetros e Swagger
    ├── controllers           # Rotas da API
    └── globalExceptionHandler
```
