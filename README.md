# Task Manager

Aplicação full stack de gerenciamento de tarefas, construída como projeto de estudo com o objetivo de evoluir de uma aplicação de linha de comando para uma arquitetura completa de backend (API REST) e frontend (SPA), com persistência em banco de dados relacional e ambiente containerizado.

O projeto nasceu como um CLI simples em Java, com persistência em arquivo JSON, e foi migrado ao longo do desenvolvimento para uma API REST em Spring Boot, com banco PostgreSQL, e um cliente web em React com TypeScript.

## Sumário

- [Visão geral](#visão-geral)
- [Arquitetura](#arquitetura)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Estrutura do repositório](#estrutura-do-repositório)
- [Modelo de dados](#modelo-de-dados)
- [Endpoints da API](#endpoints-da-api)
- [Como executar o projeto](#como-executar-o-projeto)
- [Variáveis de ambiente](#variáveis-de-ambiente)
- [Decisões técnicas](#decisões-técnicas)
- [Roadmap](#roadmap)
- [Autor](#autor)

## Visão geral

O Task Manager permite criar, listar, editar, atualizar o status e remover tarefas. Cada tarefa possui nome, descrição, data e um status que representa seu andamento (não iniciado, em progresso ou finalizado).

O backend expõe uma API REST responsável por toda a lógica de negócio e persistência dos dados. O frontend consome essa API e oferece uma interface web para gerenciamento das tarefas.

## Arquitetura

O backend segue uma arquitetura em camadas, separando claramente responsabilidades:

```
Requisição HTTP
      |
  Controller   -> recebe a requisição, converte o corpo JSON em DTO
      |
   Service     -> aplica as regras de negócio
      |
  Repository   -> abstrai o acesso a dados (Spring Data JPA)
      |
   Banco de dados (PostgreSQL)
```

- **Controller**: exposição dos endpoints HTTP. Não contém regra de negócio.
- **DTO (Data Transfer Object)**: define o formato de entrada e saída de cada operação, evitando expor a entidade de persistência diretamente na API.
- **Service**: concentra as regras de negócio, como o status inicial de uma tarefa recém-criada e a atualização de campos de uma tarefa existente.
- **Repository**: interface que estende `JpaRepository`, delegando ao Spring Data a geração da implementação de acesso a dados.
- **Entity**: classe mapeada como tabela do banco de dados via JPA/Hibernate.

O tratamento de erros é centralizado através de um `@ControllerAdvice`, responsável por converter exceções internas (como a busca por uma tarefa inexistente) em respostas HTTP apropriadas.

## Tecnologias utilizadas

**Backend**
- Java 17
- Spring Boot 4
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven

**Frontend**
- React 19
- TypeScript
- Vite

**Infraestrutura**
- Docker e Docker Compose

## Estrutura do repositório

```
Task-Manager/
├── backend/
│   ├── src/main/java/com/reis/
│   │   ├── controllers/     Camada de exposição HTTP
│   │   ├── dto/             Objetos de entrada e saída da API
│   │   ├── entities/        Entidades JPA
│   │   ├── exceptions/      Tratamento centralizado de exceções
│   │   ├── repositories/    Interfaces de acesso a dados
│   │   ├── service/         Regras de negócio
│   │   └── TaskManagerApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── pom.xml
│
└── front/
    └── tasks-frontend/
        ├── src/
        │   ├── App.tsx      Componente principal
        │   ├── api.ts       Camada de comunicação com a API
        │   ├── types.ts     Definições de tipos TypeScript
        │   └── App.css
        └── package.json
```

## Modelo de dados

**Tasks**

| Campo      | Tipo   | Descrição                                          |
|------------|--------|-----------------------------------------------------|
| id         | int    | Identificador único, gerado pelo banco de dados      |
| nome       | String | Título da tarefa                                     |
| descricao  | String | Descrição detalhada da tarefa                        |
| data       | String | Data associada à tarefa                              |
| status     | enum   | `NAO_INICIADO`, `EM_PROGRESSO` ou `FINALIZADO`       |

## Endpoints da API

Base URL: `http://localhost:8080`

| Método | Rota                  | Descrição                                  | Corpo da requisição            |
|--------|-----------------------|----------------------------------------------|---------------------------------|
| GET    | `/tasks`              | Lista todas as tarefas                        | -                                |
| GET    | `/tasks/{id}`         | Retorna uma tarefa específica                 | -                                |
| POST   | `/tasks`              | Cria uma nova tarefa                          | `{ nome, descricao, data }`      |
| PUT    | `/tasks/{id}`         | Atualiza nome, descrição e data de uma tarefa | `{ nome, descricao, data }`      |
| PATCH  | `/tasks/{id}/status`  | Atualiza o status de uma tarefa               | `{ status }`                     |
| DELETE | `/tasks/{id}`         | Remove uma tarefa                             | -                                |

Requisições para um `id` inexistente retornam status `404 Not Found`.

## Como executar o projeto

### Pré-requisitos

- Docker e Docker Compose
- Node.js (para execução do frontend)

### Backend

O backend é executado via Docker Compose, que sobe dois serviços: a aplicação Spring Boot e o banco PostgreSQL.

```bash
cd backend
docker compose up --build
```

A API ficará disponível em `http://localhost:8080`.

### Frontend

```bash
cd front/tasks-frontend
npm install
npm run dev
```

A aplicação web ficará disponível em `http://localhost:5173`.

## Variáveis de ambiente

O backend utiliza um arquivo `.env`, na raiz da pasta `backend`, com as credenciais do banco de dados:

```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=defina_uma_senha
POSTGRES_DB=taskmanager
```

Este arquivo não é versionado. Um arquivo de exemplo pode ser criado como referência de configuração.

## Decisões técnicas

- **Migração de persistência em arquivo para banco relacional**: a persistência original em arquivo JSON foi substituída por PostgreSQL, eliminando problemas como geração manual e não segura de identificadores e ausência de garantias transacionais.
- **Uso de DTOs**: os dados recebidos e retornados pela API são representados por classes específicas para cada operação, mantendo a entidade de persistência isolada da camada de comunicação externa.
- **Regras de negócio centralizadas na camada de serviço**: decisões como o status inicial de uma tarefa são responsabilidade exclusiva do `TaskService`, independentemente de qual camada superior o invoque.
- **Tratamento de exceções centralizado**: um `GlobalExceptionHandler` converte exceções internas em respostas HTTP consistentes, evitando o vazamento de erros genéricos (500) para o cliente.
- **Ambiente containerizado**: backend e banco de dados são executados via Docker Compose, com comunicação entre os serviços realizada pela rede interna do Compose.

## Roadmap

- Validação de dados de entrada nos DTOs
- Configuração de CORS para o consumo pela aplicação frontend
- Cobertura de testes automatizados (unitários e de integração)
- Autenticação e autorização de usuários
- Deploy do backend e do frontend em ambiente de produção
- Containerização completa do frontend


Repositório: [github.com/rodrigoreisrsz/Task-Manager](https://github.com/rodrigoreisrsz/Task-Manager)
