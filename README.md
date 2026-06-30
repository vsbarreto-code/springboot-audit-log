# AuditLog
Projeto desenvolvido como parte de uma série de desafios técnicos para treino de backend com Java e Spring Boot, simulando processos seletivos reais.

---


API REST para análise em lote de operações financeiras (créditos e débitos), com identificação automática de inconsistências com base em regras de negócio pré-definidas.

Projeto desenvolvido como parte de uma série de desafios técnicos para treino de backend com Java e Spring Boot, simulando processos seletivos reais.

## Tecnologias

- Java 17
- Spring Boot 3.x (Web, Validation)
- MapStruct
- Lombok
- Maven

## Funcionalidade

A API recebe um lote de operações de um sistema de origem e retorna um resumo consolidado, incluindo:

- Total de operações processadas
- Soma total de créditos
- Soma total de débitos
- Saldo final (créditos − débitos)
- IDs de operações consideradas suspeitas
- Indicador geral de inconsistência no lote

### Regras de inconsistência

Uma operação é marcada como suspeita quando atende a pelo menos uma das condições abaixo:

- Valor da operação superior a **R$ 50.000,00**
- Operação do tipo **DÉBITO** com valor superior a **R$ 10.000,00**
- ID de operação **duplicado** dentro do mesmo lote

O lote como um todo é considerado inconsistente quando:

- Existe pelo menos uma operação suspeita, **ou**
- O saldo final é **negativo**

## Endpoint

### `POST /lotes/analisar`

Analisa um lote de operações e retorna o resumo consolidado.

**Request Body**

```json
{
  "sistemaOrigem": "ERP-Financeiro",
  "operacoes": [
    {
      "id": "op-001",
      "tipo": "CREDITO",
      "valor": 1500.00,
      "dataHora": "2025-06-20T10:30:00"
    },
    {
      "id": "op-002",
      "tipo": "DEBITO",
      "valor": 12000.00,
      "dataHora": "2025-06-20T11:00:00"
    }
  ]
}
```

**Validações do request**

| Campo                   | Regra                                              |
|--------------------------|-----------------------------------------------------|
| `sistemaOrigem`          | Obrigatório, entre 3 e 50 caracteres                |
| `operacoes`               | Obrigatório, não pode ser vazio                     |
| `operacoes[].id`          | Obrigatório                                          |
| `operacoes[].tipo`        | Obrigatório (`DEBITO` ou `CREDITO`)                  |
| `operacoes[].valor`       | Obrigatório, deve ser positivo                       |
| `operacoes[].dataHora`    | Obrigatório, não pode ser data futura                |

**Response Body — `200 OK`**

```json
{
  "totalOperacoes": 2,
  "totalCreditos": 1500.00,
  "totalDebitos": 12000.00,
  "saldoFinal": -10500.00,
  "idOperacoes": ["op-002"],
  "possuiInconsistencia": true
}
```

**Response Body — `422 Unprocessable Entity`** (erro de validação)

```json
{
  "timestamp": "2025-06-20T11:05:00",
  "status": 422,
  "Erro": "Erro de Validação",
  "mensagens": [
    "O valor é obrigatório.",
    "Informe pelo menos um item"
  ]
}
```

## Estrutura do projeto

```
com.vb.audilog
├── Service
│   └── SistemaService          # Regras de negócio e cálculo do resumo
├── controller
│   └── SistemaController       # Endpoint POST /lotes/analisar
├── database.entity
│   ├── SistemaModel            # Modelo agregado do lote
│   ├── OperacoesModel          # Modelo de cada operação
│   └── Enuns.TipoOperacao      # DEBITO / CREDITO
├── dto.request
│   ├── SistemaRequestDTO
│   └── OperacaoRequestDTO
├── dto.response
│   ├── SistemaResponseDTO
│   └── ErrorResponseDTO
├── mapper
│   └── ISistemaMapper          # Conversão DTO <-> Model (MapStruct)
└── exception
    └── GlobalException         # Tratamento global de validação
```

## Como executar

```bash
./mvnw spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

### Exemplo de chamada via cURL

```bash
curl -X POST http://localhost:8080/lotes/analisar \
  -H "Content-Type: application/json" \
  -d '{
    "sistemaOrigem": "ERP-Financeiro",
    "operacoes": [
      { "id": "op-001", "tipo": "CREDITO", "valor": 1500.00, "dataHora": "2025-06-20T10:30:00" },
      { "id": "op-002", "tipo": "DEBITO", "valor": 12000.00, "dataHora": "2025-06-20T11:00:00" }
    ]
  }'
```
