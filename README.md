# Task List Tracker

O Task List Tracker é uma aplicação de console desenvolvida em Java para a gestão de tarefas.  
O projeto foi desenhado seguindo princípios de arquitetura em camadas e utiliza automação de infraestrutura para garantir que qualquer utilizador consiga executar o sistema localmente sem configurações manuais complexas.

## Tecnologias Utilizadas

- Java 24: Versão mais recente para máxima performance e recursos da linguagem.
- MySQL: Base de dados relacional para persistência dos dados.
- Docker & Docker Compose: Contentorização da base de dados para portabilidade.
- Flyway: Automação de migrações e versionamento do esquema da base de dados.
- Lombok: Redução de código repetitivo para maior legibilidade.
- Log4j2: Sistema de logs profissional para monitorização e depuração.
- Maven: Gestão de dependências e automação do ciclo de vida do projeto.

## Funcionalidades

- Gestão de Tarefas: Criação de tarefas com nome e descrição detalhada.
- Listagem Inteligente: Visualização de todas as tarefas registadas na base de dados.
- Filtros por Estado: Consulta específica por tarefas nos estados "TO DO", "IN PROGRESS" ou "DONE".
- Edição Dinâmica: Atualização de nomes, descrições e estados de tarefas existentes.
- Remoção Segura: Eliminação de registos com confirmação explícita do utilizador.
- Auditoria Temporal: Registo automático de datas de criação e última atualização de cada tarefa.

## Como Executar

### Pré-requisitos

- Java JDK 24 instalado.
- Docker e Docker Compose instalados e em execução.
- Maven (ou uso do Maven Wrapper integrado na IDE).

### 1. Iniciar a Base de Dados (Docker)

Na raiz do projeto, execute o comando para subir o serviço MySQL:

    docker-compose up -d

Este comando configura automaticamente o ambiente isolado com a base de dados task_list_tracker_db na porta 3306.

### 2. Compilar e Instalar Dependências

Certifique-se de que está na raiz do projeto e execute:

    mvn clean compile

### 3. Executar a Aplicação

Execute a classe principal TrackerTest para iniciar o menu interativo:

    src/main/java/main/task_list_tracker/test/TrackerTest.java

Graças à integração com o Flyway, a aplicação criará automaticamente a tabela tasks_tb na primeira execução, eliminando a necessidade de scripts SQL manuais.

## Estrutura do Projeto

O projeto segue uma arquitetura modular para facilitar a manutenção:

- conn: Fábrica de conexões JDBC centralizada.
- domain: Entidades ricas e modelos de dados do sistema.
- repository: Camada DAO responsável pelas operações de persistência e queries SQL.
- service: Orquestração da lógica de negócio e interface via consola.
- test: Ponto de entrada da aplicação para testes e execução.
