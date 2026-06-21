# ClonePayloads_Backend

## Descrição 📖

Backend em Java + Spring Boot responsável por gerar múltiplas variações de payloads JSON a partir de um payload base.

A API permite criar até 100 clones aplicando estratégias específicas por campo, como geração de UUIDs, incremento numérico, incremento de timestamps e rotação de valores fixos.

Este serviço é utilizado pelo frontend hospedado em:
https://clone-payloads.vercel.app/

---

## ⚠️ Uso Local (Importante!)

Se você quiser rodar localmente, é necessário ajustar CORS e portas:

### ✔ Backend deve rodar na porta 8081
### ✔ Frontend deve rodar na porta 8080

O frontend local só consegue acessar a API se o backend estiver em:

```bash
http://localhost:8081/api/generate
```

---

E no arquivo ClonePayloadsApplication.java você deve alterar o CORS:

```java
.allowedOrigins("http://localhost:8080")
```

---

## 🚀 Funcionalidade principal

A API recebe um JSON contendo:

* quantidade = número de cópias a serem geradas (mínimo 2, máximo 100)

* payload = objeto JSON base

* fieldRoles = lista contendo as estratégias que serão aplicadas em cada campo

O backend retorna uma lista de payloads clonados aplicando automaticamente as regras configuradas.

---

## 🧩 Exemplo de requisição

```json
POST /api/generate
Content-Type: application/json

{
  "quantidade": 3,
  "payload": {
    "name": "rodolfo",
    "timestamp": "2025-12-04T15:40:12.487Z",
    "userId": "abc",
    "score": 10
  },
  "fieldRoles": [
    {
      "field": "timestamp",
      "strategy": "TIMESTAMP_INCREMENT"
    },
    {
      "field": "userId",
      "strategy": "UUID"
    },
    {
      "field": "score",
      "strategy": "NUMERIC_INCREMENT"
    }
  ]
}
```

### Resposta

```json
[
  {
    "name": "rodolfo",
    "timestamp": "2025-12-04T15:40:12.488Z",
    "userId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "score": 11
  },
  {
    "name": "rodolfo",
    "timestamp": "2025-12-04T15:40:12.489Z",
    "userId": "9c858901-8a57-4791-81fe-4c455b099bc9",
    "score": 12
  },
  {
    "name": "rodolfo",
    "timestamp": "2025-12-04T15:40:12.490Z",
    "userId": "16fd2706-8baf-433b-82eb-8c7fada847da",
    "score": 13
  }
]
```

---

## 🧠 Estratégias disponíveis

### TIMESTAMP_INCREMENT

Incrementa timestamps ISO-8601 em +1ms, +2ms, +3ms...

### NUMERIC_INCREMENT

Incrementa valores numéricos em +1, +2, +3...

### UUID

Gera UUIDs aleatórios para cada clone.

### FIXED_VALUES

Rotaciona valores de uma lista em ordem circular.

Exemplo:

```text
alice → bob → carol → alice → bob
```

---

## 🛠 Tecnologias utilizadas

* Java 17+
* Spring Boot
* Spring Web / MVC
* Bean Validation (Jakarta Validation)
* JUnit 5
* MockMvc
* Maven
* Docker (opcional)

---

## 📂 Estrutura do projeto

```css
src/
 └── main/
      ├── java/
      │    └── com.example.clonepayloads
      │          ├── controller/
      │          ├── dto/
      │          ├── service/
      │          └── exceptions/
      └── test/
```

---

## ⚙️ Como rodar o projeto

▶️ Rodando com Maven

```bash
git clone https://github.com/ezequieldesr/ClonePayloads_Backend.git

cd ClonePayloads_Backend

./mvnw clean install

./mvnw spring-boot:run
```

A API sobe normalmente em:

```bash
http://localhost:8081/api/generate
```

---

## 🐳 Rodando com Docker

```bash
docker build -t clonepayloads-backend .

docker run -p 8081:8081 clonepayloads-backend
```

---

## 🧪 Testes Unitários

O projeto inclui testes unitários e de integração utilizando MockMvc.

Para rodar:

```bash
./mvnw test
```

---

## 🔒 Validações implementadas

### PayloadRequest

* quantidade ≥ 2
* quantidade ≤ 100
* payload ≠ null
* payload ≠ vazio
* payload ≤ 100 campos

### TIMESTAMP_INCREMENT

* exige timestamp ISO-8601 válido

### NUMERIC_INCREMENT

* exige campo numérico

### UUID

* exige campo String

### FIXED_VALUES

* exige lista não vazia

---

## 🔗 Endpoint principal

```http
POST /api/generate
```

| Campo | Tipo | Obrigatório | Descrição |
|--------|--------|--------|--------|
| quantidade | int | ✅ | Quantidade de clones (2–100) |
| payload | Map | ✅ | Payload base |
| fieldRoles | List | ❌ | Estratégias aplicadas aos campos |

---

## 🌐 CORS

A API está configurada para aceitar requisições apenas do frontend oficial:

https://clone-payloads.vercel.app/
