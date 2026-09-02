# Gerenciador de Tarefas (Task Manager CLI)

Aplicação de linha de comando em Java para gerenciamento de tarefas, com persistência de dados em arquivo JSON.

## 🚀 Funcionalidades

- **Adicionar tarefa** — cadastra nova tarefa com nome, descrição e data
- **Editar tarefa** — atualiza nome, descrição e data de uma tarefa existente pelo ID
- **Deletar tarefa** — remove uma tarefa pelo ID
- **Marcar status** — atualiza o status de uma tarefa (Não iniciado / Em progresso / Finalizado)
- **Listar todas** — exibe todas as tarefas cadastradas
- **Listar por status** — exibe apenas as tarefas de um status específico
- **Ver tarefa específica** — busca e exibe uma única tarefa pelo ID

## 🗂️ Estrutura do projeto

```
com.reis.main
 └── Main.java              // Interface de linha de comando (menu e loop principal)

com.reis.service
 ├── TaskManager.java        // Regras de negócio: CRUD e busca de tarefas
 ├── Tasks.java               // Modelo da tarefa (id, nome, descrição, data, status)
 └── Status.java              // Enum de status (NAO_INICIADO, EM_PROGRESSO, FINALIZADO)

com.reis.taskRepository
 └── TaskRepository.java     // Persistência das tarefas em tasks.json via Gson
```

## 🧠 Como funciona

- Cada `Tasks` recebe um **ID autoincrementado**, controlado por um contador estático na própria classe.
- Ao iniciar, o `TaskService` carrega as tarefas salvas em `tasks.json` (via `TaskRepository`) e reajusta o contador de ID com base no maior ID já existente, evitando duplicidade.
- Toda operação que altera dados (adicionar, editar, deletar, marcar status) persiste automaticamente a lista atualizada no `tasks.json`.
- A exibição de cada tarefa é feita pelo método `toString()` da classe `Tasks`, no formato:

```
ID: 1
 Nome: Estudar Java
 Descrição: Revisar Streams e Collections
 Status: NAO_INICIADO
```

## 🛠️ Tecnologias

- **Java**
- **Gson** — serialização/desserialização do arquivo `tasks.json`
- **Scanner** — entrada de dados via terminal

## ▶️ Como usar

Ao rodar o programa, o menu abaixo é exibido:

```
=====================================
        GERENCIADOR DE TAREFAS
=====================================
1. Adicionar tarefa
2. Editar tarefa
3. Deletar tarefa
4. Marcar status
5. Listar todas
6. Listar por status
7. Ver uma tarefa especifica
0. Sair
=====================================
Escolha uma opção:
```

Basta digitar o número da opção desejada e seguir as instruções no terminal.

## 📌 Status do projeto

Em desenvolvimento — próximas melhorias possíveis: tratamento de exceções para IDs inválidos/inexistentes, validação de entrada do usuário e testes automatizados.