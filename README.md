# API REST com Spring Boot + PokeAPI

Projeto de estudo em Spring Boot que se integra à [PokeAPI](https://pokeapi.co/) e implementa os verbos **GET**, **POST**, **PUT** e **DELETE** para uma API de pokémons.

> **Observação:** a PokeAPI aceita apenas requisições GET. Por isso, as operações de POST, PUT e DELETE trabalham em uma lista **local, em memória** (os dados são perdidos ao reiniciar a aplicação). Já as consultas por nome e a listagem de até 50 pokémons vêm diretamente da PokeAPI.

## Tecnologias

- Java 17+
- Spring Boot 3.2+ (Spring Web)
- Maven
- `RestClient` para consumir a PokeAPI

## Estrutura do projeto

```
apirestwithspring/src/main/java/com/example/apirestwithspring
├── ApirestwithspringApplication.java   # classe principal
├── controllers/PokemonController.java  # endpoints REST
├── services/PokemonService.java        # regras e chamada à PokeAPI
├── repositories/PokemonRepository.java # armazenamento local em memória
└── entities/Pokemon.java               # record (id, name, height)
```

## Como executar

1. Clone o repositório:
   ```bash
   git clone <URL-DO-REPOSITORIO>
   cd <NOME-DA-PASTA>
   ```
2. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
   (No Windows: `mvnw.cmd spring-boot:run`. Também é possível rodar a classe `ApirestwithspringApplication` pela IDE.)
3. Aguarde a mensagem `Started ApirestwithspringApplication`. A API ficará disponível em `http://localhost:8080`.

## Endpoints

Base: `http://localhost:8080/pokeApi`

| Método | Endpoint | Descrição | Origem dos dados |
|--------|----------|-----------|------------------|
| GET | `/pokemon/{nome}` | Busca um pokémon pelo nome | PokeAPI |
| GET | `/pokemon` | Lista até 50 pokémons (`listarTodos`) | PokeAPI |
| GET | `/locais` | Lista os pokémons cadastrados localmente (máx. 50) | Lista local |
| POST | `/pokemon` | Cadastra um pokémon | Lista local |
| PUT | `/pokemon/{id}` | Atualiza um pokémon pelo id | Lista local |
| DELETE | `/pokemon/{id}` | Remove um pokémon pelo id | Lista local |

### Corpo das requisições (POST e PUT)

```json
{
  "id": 25,
  "name": "pikachu",
  "height": 4
}
```

No PUT, o `id` é definido pela URL, então o corpo só precisa de `name` e `height`.

### Códigos de resposta

| Código | Significado |
|--------|-------------|
| 200 | Sucesso |
| 204 | Removido com sucesso (DELETE) |
| 404 | Pokémon não encontrado (PUT ou DELETE com id inexistente) |
| 415 | Faltou o cabeçalho `Content-Type: application/json` |

## Como testar com curl

Execute na ordem, em um segundo terminal (com a aplicação rodando).

```bash
# 1. Buscar por nome (PokeAPI)
curl -i http://localhost:8080/pokeApi/pokemon/pikachu

# 2. Listar até 50 pokémons (PokeAPI)
curl -i http://localhost:8080/pokeApi/pokemon

# 3. Cadastrar (POST)
curl -i -X POST http://localhost:8080/pokeApi/pokemon \
  -H "Content-Type: application/json" \
  -d '{"id":25,"name":"pikachu","height":4}'

# 4. Conferir a lista local
curl -i http://localhost:8080/pokeApi/locais

# 5. Atualizar (PUT)
curl -i -X PUT http://localhost:8080/pokeApi/pokemon/25 \
  -H "Content-Type: application/json" \
  -d '{"name":"raichu","height":8}'

# 6. Remover (DELETE)
curl -i -X DELETE http://localhost:8080/pokeApi/pokemon/25

# 7. Remover de novo (deve retornar 404)
curl -i -X DELETE http://localhost:8080/pokeApi/pokemon/25
```

**Windows (cmd):** use aspas duplas e escape as internas, em uma única linha:

```
curl -i -X POST http://localhost:8080/pokeApi/pokemon -H "Content-Type: application/json" -d "{\"id\":25,\"name\":\"pikachu\",\"height\":4}"
```

**Windows (PowerShell):** use `curl.exe` no lugar de `curl`.

## Como testar com Postman

1. Crie uma nova requisição (**New → HTTP**).
2. Escolha o verbo e informe a URL (por exemplo, `http://localhost:8080/pokeApi/pokemon/pikachu`).
3. Para POST e PUT, abra a aba **Body**, selecione **raw → JSON** e cole o corpo da requisição.
4. Clique em **Send** e confira o status e o corpo da resposta.

Sugestão: salve as requisições em uma **Collection** para repetir os testes depois.

## Problemas comuns

- **`Connection refused`:** a aplicação não está rodando ou subiu em outra porta.
- **`415 Unsupported Media Type`:** faltou o `Content-Type: application/json` no POST ou no PUT.
- **`400 Bad Request`:** JSON mal formado (confira aspas e vírgulas).
- **`404` ao buscar por nome:** use o nome em minúsculas, como `pikachu`.
