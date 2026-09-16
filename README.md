# Projeto de Banco de Dados - Java + MongoDB

## Estudante

**Gabriela Fabian Pires Costa**

## Descrição

Este projeto consiste em um sistema acadêmico desenvolvido em **Java** e integrado ao banco de dados **MongoDB**, com execução via console.

O objetivo é demonstrar a utilização de um banco de dados NoSQL orientado a documentos, implementando operações completas de CRUD e relacionamentos entre diferentes entidades.

O sistema permite o gerenciamento de cursos, turmas, períodos, alunos e desafios, além de consultas específicas envolvendo os relacionamentos entre os documentos.

## Tecnologias utilizadas

- Java 21
- Maven
- MongoDB
- MongoDB Java Sync Driver
- Eclipse IDE
- Git
- GitHub

## Entidades

### Curso

- id
- nome

### Turma

- id
- nome

### Período

- id
- nome

### Aluno

- id
- nome
- RA
- turma

### Desafio

- id
- nome
- curso
- período
- turma
- alunos

## Funcionalidades

O sistema implementa as operações de CRUD para todas as entidades:

- **Create:** cadastro de novos registros.
- **Read:** listagem dos registros e consulta por identificador.
- **Update:** atualização dos registros cadastrados.
- **Delete:** exclusão de registros.

Além do CRUD, o sistema permite:

- Listar todos os cursos cadastrados.
- Listar todas as turmas cadastradas.
- Listar todos os períodos cadastrados.
- Listar todos os alunos cadastrados.
- Listar todos os desafios cadastrados.
- Consultar registros por ID.
- Listar os alunos relacionados a uma determinada turma.
- Apresentar os alunos relacionados a um determinado desafio.

## Estratégia de relacionamentos no MongoDB

Foi utilizada a estratégia de **referências entre documentos utilizando ObjectId**.

O documento de `Aluno` possui o campo `turmaId`, que referencia a turma à qual o aluno pertence.

O documento de `Desafio` possui os campos:

- `cursoId`
- `periodoId`
- `turmaId`
- `alunoIds`

O campo `alunoIds` armazena uma lista de referências aos alunos relacionados ao desafio.

Essa abordagem foi escolhida para evitar a duplicação desnecessária de informações e manter cada entidade armazenada em sua própria collection, permitindo que os documentos relacionados sejam consultados através de seus identificadores.

Exemplo simplificado:

Aluno:

    {
        "_id": ObjectId,
        "nome": "Gabriela Fabian",
        "ra": "2026123",
        "turmaId": ObjectId
    }

Desafio:

    {
        "_id": ObjectId,
        "nome": "Estrutura de Banco de Dados",
        "cursoId": ObjectId,
        "periodoId": ObjectId,
        "turmaId": ObjectId,
        "alunoIds": [
            ObjectId
        ]
    }

## Banco de dados

Por padrão, a aplicação utiliza a seguinte conexão local:

    mongodb://localhost:27017

O banco utilizado é:

    projeto_faculdade

As collections são criadas e utilizadas pela aplicação para armazenar os documentos correspondentes às entidades do sistema.

## Como configurar e executar

### Pré-requisitos

Antes de executar o projeto, é necessário possuir:

- Java JDK 21
- Maven
- MongoDB Community Server
- Eclipse IDE

### Execução

1. Certifique-se de que o serviço do MongoDB esteja em execução.

2. Abra o Eclipse.

3. Acesse:

       File > Import > Maven > Existing Maven Projects

4. Selecione a pasta do projeto que contém o arquivo `pom.xml`.

5. Aguarde o Maven carregar as dependências.

6. Localize a classe:

       src/main/java/br/com/projeto/Main.java

7. Clique com o botão direito em `Main.java`.

8. Selecione:

       Run As > Java Application

9. O menu do sistema será apresentado no console do Eclipse.

## Estrutura do projeto

    src/main/java/br/com/projeto/
    |
    |-- Main.java
    |
    |-- config/
    |   |-- MongoConfig.java
    |
    |-- model/
    |   |-- Curso.java
    |   |-- Turma.java
    |   |-- Periodo.java
    |   |-- Aluno.java
    |   |-- Desafio.java
    |
    |-- repository/
        |-- CursoRepository.java
        |-- TurmaRepository.java
        |-- PeriodoRepository.java
        |-- AlunoRepository.java
        |-- DesafioRepository.java

## Execução pelo terminal

O projeto também pode ser executado utilizando Maven.

Para compilar:

    mvn clean compile

Para executar:

    mvn exec:java

## Segurança da conexão

A aplicação utiliza por padrão uma conexão local com o MongoDB.

Também é possível configurar outra conexão por meio da variável de ambiente `MONGODB_URI`.

Credenciais de acesso ao banco de dados não devem ser armazenadas ou publicadas no repositório.

---

**Projeto desenvolvido por Gabriela Fabian Pires Costa.**