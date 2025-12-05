# ClonePayloads_Backend

## Descrição 📖

Backend em Java + Spring Boot responsável por clonar payloads JSON e ajustar automaticamente o campo timestamp, adicionando milissegundos incrementais conforme a quantidade solicitada.

Este serviço é utilizado pelo frontend hospedado em [Vercel](https://payload-cloner.vercel.app/). 

---

## ⚠️ Uso Local (Importante!)

Se você quiser rodar **localmente**, é necessário ajustar CORS e portas:

### ✔ Backend deve rodar na porta **8081**  
### ✔ Frontend deve rodar na porta **8080**

O frontend local só consegue acessar a API se o backend estiver em:
```bash
http://localhost:8081/api/generate
```

---

E no arquivo `ClonePayloadsApplication.java`, você deve alterar o CORS:

```java
.allowedOrigins("http://localhost:8080")
```

## 🚀 Funcionalidade principal

A API recebe um JSON contendo:

* **quantidade** = número de cópias a serem geradas (mínimo 2, máximo 100)

* **payload** = objeto com os campos originais, incluindo necessariamente um timestamp ISO-8601

O backend retorna uma lista de payloads clonados, cada um com o timestamp incrementado em +1ms, +2ms, +3ms... até a quantidade solicitada.

---

## 🧩 Exemplo de requisição

```json
POST /api/generate
Content-Type: application/json

{
  "quantidade": 3,
  "payload": {
    "name": "rodolfo",
    "timestamp": "2025-12-04T15:40:12.487Z"
  }
}
```

### Resposta:

```json
{
  "payloads": [
    {
      "name": "rodolfo",
      "timestamp": "2025-12-04T15:40:12.488Z"
    },
    {
      "name": "rodolfo",
      "timestamp": "2025-12-04T15:40:12.489Z"
    },
    {
      "name": "rodolfo",
      "timestamp": "2025-12-04T15:40:12.490Z"
    }
  ]
}

```
--- 
## 🛠 Tecnologias utilizadas

* **Java 17+**
* **Spring Boot**
* **Spring Web / MVC**
* **Bean Validation** (Jakarta Validation)
* **JUnit 5**
* **MockMvc**
* **Maven**
* **Docker** (opcional)
* **CORS** configurado para o frontend:
  * https://payload-cloner.vercel.app/
---
## 📂 Estrutura do projeto
```css
src/
 └── main/
      ├── java/
      │    └── com.example.clonepayloads
      │          ├── ClonePayloadsApplication.java
      │          ├── controller/
      │          │      └── PayloadController.java
      │          ├── dto/
      │          │      ├── PayloadRequest.java
      │          │      └── PayloadResponse.java
      │          └── service/
      │                 └── PayloadService.java
      └── test/
           ├── PayloadControllerTest.java
           └── PayloadServiceTest.java

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

O projeto inclui testes unitários e de integração com MockMvc:
* Validação de @Valid no payload
* Testes de timestamp
* Teste de geração de múltiplos payloads

Para rodar:
```bash
./mvnw test
```
---
## 🔒 Validações implementadas

### PayloadRequest

* **quantidade ≥ 2**
* **quantidade ≤ 100**
* **payload ≠ null**
* **payload ≠ vazio**

### PayloadService

* **timestamp** deve existir no payload
* **timestamp** deve ser String válida ISO-8601
* O **timestamp** é clonado adicionando milissegundos incrementais
---
## 🔗 Endpoint principal

```http
  POST /api/generate
```

| Campo | Tipo     | Obrigatório  | Descrição                |
| :-------- | :------- | :------- | :------------------------- |
| `quantidade` | `int` | ✅ | Quantidade de clones (2–100) |
| `payload` | `Map` | ✅ | Objeto JSON original contendo **timestamp** |


## 🌐 CORS
A API está configurada para aceitar requisições apenas do frontend oficial:
```arduino
https://payload-cloner.vercel.app/
```
