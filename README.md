<<<<<<< HEAD
# 🚗 Sistema de Gestão Automotiva

API REST desenvolvida com **Spring Boot 3** + **MySQL** para gerenciamento de veículos, modelos e marcas.

---

## 📋 Requisitos

| Ferramenta | Versão mínima |
|------------|---------------|
| Java       | 17            |
| Maven      | 3.8+          |
| MySQL      | 8.0+          |

---

## ⚙️ Configuração do Banco de Dados

1. Certifique-se de que o MySQL está rodando na porta **3306**.
2. Edite as credenciais em `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=root
```

> O banco `gestao_automotiva` é criado automaticamente pelo Spring Boot (`createDatabaseIfNotExist=true`).  
> Para popular com dados de exemplo, execute o script `src/main/resources/db/schema.sql`.

---

## ▶️ Executando o Backend

```bash
# Na raiz do projeto
mvn spring-boot:run
```

A API ficará disponível em: `http://localhost:8080`

---

## 📖 Documentação da API (Swagger)

Acesse após subir o servidor:

```
http://localhost:8080/swagger-ui.html
```

---

## 🌐 Frontend

Abra o arquivo `frontend/index.html` diretamente no navegador (não precisa de servidor).  
Certifique-se de que o backend está rodando em `http://localhost:8080`.

---

## 🗂️ Endpoints

### Marcas — `/api/marcas`

| Método | Rota            | Descrição              |
|--------|-----------------|------------------------|
| GET    | `/api/marcas`   | Listar todas as marcas |
| GET    | `/api/marcas/{id}` | Buscar por ID       |
| POST   | `/api/marcas`   | Cadastrar marca        |
| PUT    | `/api/marcas/{id}` | Atualizar marca     |
| DELETE | `/api/marcas/{id}` | Remover marca       |

### Modelos — `/api/modelos`

| Método | Rota                        | Descrição                  |
|--------|-----------------------------|----------------------------|
| GET    | `/api/modelos`              | Listar todos os modelos    |
| GET    | `/api/modelos/{id}`         | Buscar por ID              |
| GET    | `/api/modelos/marca/{id}`   | Listar modelos por marca   |
| POST   | `/api/modelos`              | Cadastrar modelo           |
| PUT    | `/api/modelos/{id}`         | Atualizar modelo           |
| DELETE | `/api/modelos/{id}`         | Remover modelo             |

### Veículos — `/api/veiculos`

| Método | Rota                    | Descrição                                      |
|--------|-------------------------|------------------------------------------------|
| GET    | `/api/veiculos`         | Listar com filtros e paginação                 |
| GET    | `/api/veiculos/{id}`    | Buscar por ID                                  |
| GET    | `/api/veiculos/placa/{placa}` | Buscar por placa                        |
| POST   | `/api/veiculos`         | Cadastrar veículo                              |
| PUT    | `/api/veiculos/{id}`    | Atualizar veículo completo                     |
| PATCH  | `/api/veiculos/{id}`    | Atualizar parcialmente (preço, km, status)     |
| DELETE | `/api/veiculos/{id}`    | Remover veículo                                |

#### Parâmetros de filtro (GET `/api/veiculos`)

| Parâmetro | Tipo    | Descrição                  |
|-----------|---------|----------------------------|
| marcaId   | Long    | Filtrar por marca          |
| modeloId  | Long    | Filtrar por modelo         |
| precoMin  | Decimal | Preço mínimo               |
| precoMax  | Decimal | Preço máximo               |
| anoMin    | Integer | Ano mínimo                 |
| anoMax    | Integer | Ano máximo                 |
| status    | Enum    | DISPONIVEL, VENDIDO, etc.  |
| page      | Integer | Número da página (0-based) |
| size      | Integer | Itens por página           |

---

## 🏗️ Estrutura do Projeto

```
gestao-automotiva/
├── src/
│   └── main/
│       ├── java/com/gestaoautomotiva/
│       │   ├── controller/       # REST Controllers
│       │   ├── dto/              # Data Transfer Objects
│       │   ├── entity/           # Entidades JPA
│       │   ├── enums/            # StatusVeiculo
│       │   ├── exception/        # Tratamento de erros
│       │   ├── repository/       # Spring Data JPA + Specification
│       │   └── service/          # Regras de negócio
│       └── resources/
│           ├── application.properties
│           └── db/schema.sql     # Modelo físico + dados de exemplo
├── frontend/
│   ├── index.html                # Interface web
│   └── app.js                    # Lógica JavaScript
├── pom.xml
└── README.md
```

---

## 📊 Status de Veículo

| Status        | Descrição                        |
|---------------|----------------------------------|
| DISPONIVEL    | Veículo disponível para venda    |
| RESERVADO     | Reservado por um cliente         |
| MANUTENCAO    | Em manutenção                    |
| VENDIDO       | Já foi vendido                   |
| DESCONTINUADO | Fora de linha / descontinuado    |
=======
# Gestao-de-veiculos
Sistema de gestão de estoque para lojas automotivas e locadoras de veículos
>>>>>>> 2ee43c6333a34e395b3e185149e929357d983c39
