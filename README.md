# Summarize API

Este projeto fornece uma API REST para carregar e manipular dados de pessoas a partir de um arquivo CSV e calcular várias estatísticas relacionadas à idade e gênero. O projeto é desenvolvido com Spring Boot e usa JPA para persistência de dados.


## Descrição

API para sumarização de dados de pessoas por estado.

## Funcionalidades

- Carregar dados de um arquivo CSV.
- Calcular a idade das pessoas com base na data de nascimento.
- Contar o n.º de pessoas em diferentes faixas etárias (≥ 50 anos e ≤ 20 anos).
- Contar o n.º de pessoas por gênero em diferentes estados.
- Exibir estados com o maior número de pessoas em cada faixa etária e por gênero.

## Pré-requisitos

- Java 21 ou superior
- Maven
- Spring Boot
- Excel ou Google Planilhas (para manipulação de dados CSV)

## Índice

- [Introdução](#introdução)
- [Configuração](#configuração)
- [Endpoints da API](#endpoints-da-api)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Modelos de Dados](#modelos-de-dados)
- [Serviços](#serviços)
- [Consultas Personalizadas](#consultas-personalizadas)

## Introdução

O objetivo deste projeto é fornecer uma API RESTful que permite a carga de dados de um arquivo CSV, armazenamento em um banco de dados e consultas sobre esses dados para obter estatísticas demográficas.

## Configuração

1. Clone o repositório:
    ```bash
    git clone https://github.com/enzoctmgeo/summarize.git
    ```

2. Navegue até o diretório do projeto:
    ```bash
    cd summarize
    ```

3. Execute o projeto usando Maven:
    ```bash
    ./mvnw spring-boot:run
    ```

4. Certifique-se de que o arquivo `lista.csv` esteja presente no diretório `src/main/resources`. Este arquivo deve ter a formatação na mesma sequencia do exemplo abaixo:

   ```bash
   Gender,Title,GivenName,MiddleInitial,Surname,State,EmailAddress,Birthday,Latitude,Longitude
   ```

## Endpoints da API

A API fornece os seguintes endpoints:

- `GET /api/persons`: Retorna todas as pessoas.
- `GET /api/ageCountData`: Retorna a contagem de idade agrupada por estados para pessoas com idade ≥ 50 e ≤ 20 anos.
- `GET /api/genderCountByStates`: Retorna a contagem de gêneros por estados.
- `GET /api/stateGenderCountMinMaxData`: Retorna a contagem mínima e máxima de gêneros por estados.

## Estrutura do Projeto

- `config/DataLoader.java`: Carrega dados de um arquivo CSV e os salva no banco de dados.
- `controller/PersonController.java`: Define os endpoints da API.
- `model/Person.java`: Define a entidade `Person`.
- `repository/PersonRepository.java`: Interface JPA para acessar os dados da entidade `Person`.
- `service/PersonService.java`: Contém a lógica de negócios para manipulação dos dados da entidade `Person`.

## Modelos de Dados

### Person

A entidade `Person` representa uma pessoa e possui os seguintes campos:

- `id`: Identificador único.
- `gender`: Gênero da pessoa.
- `title`: Título da pessoa.
- `givenName`: Nome da pessoa.
- `middleInitial`: Inicial do meio da pessoa.
- `surname`: Sobrenome da pessoa.
- `state`: Estado da pessoa.
- `email`: Email da pessoa.
- `birthday`: Data de nascimento da pessoa.
- `latitude`: Latitude da localização da pessoa.
- `longitude`: Longitude da localização da pessoa.

A data de nascimento da pessoa é convertida automaticamente para idade através do método `getAge`.

## Serviços

### PersonService

A classe `PersonService` fornece a lógica de negócios para manipulação dos dados da entidade `Person`. Esta classe interage com o `PersonRepository` para realizar operações de CRUD e consultas complexas. Aqui estão algumas das principais funcionalidades oferecidas pelo `PersonService`:

#### Método

- `findAll()`: Retorna uma lista de todas as pessoas no banco de dados.

#### Consultas Personalizadas

- `findStateWithPeopleAgeGreaterEquals50()`: Retorna estados com contagem de pessoas com idade ≥ 50 anos.
- `findStateWithPeopleAgeLowerEquals20()`: Retorna estados com contagem de pessoas com idade ≤ 20 anos.
- `getAgeCountData()`: Obtém dados de contagem de idade para pessoas com idade ≥ 50 anos e ≤ 20 anos.
- `findGenderCountByStates()`: Retorna a contagem de gêneros por estados.
- `getStateGenderCountMinMaxData()`: Obtém dados de contagem mínima e máxima de gêneros por estados.

#### Lógica de Negócios

- `getMaxCountStates(List<AgeCountDto> states)`: Filtra estados com o valor máximo de contagem de pessoas.
- `getMaxAgeCountDtos(List<Object[]> results)`: Converte resultados de consultas em DTOs e filtra estados com o valor máximo de contagem.
- `getMinMaxStateGenderCountDtos(List<StateGenderCountDto> data, Gender gender, Operation operation)`: Filtra e mapeia estados com a contagem mínima ou máxima de um determinado gênero.

#### Enumerações

- `Gender`: Enumeração para representar gêneros (MALE, FEMALE).
- `Operation`: Enumeração para representar operações (MIN, MAX).

## Consultas Personalizadas

### PersonRepository

Contém consultas personalizadas para obter estatísticas dos dados de `Person`:

- `findStateWithPeopleAgeGreaterEquals50`: Retorna estados com contagem de pessoas com idade ≥ 50 anos.
- `findStateWithPeopleAgeLowerEquals20`: Retorna estados com contagem de pessoas com idade ≤ 20 anos.
- `findGenderCountForAllStates`: Retorna a contagem de gêneros (masculino e feminino) por estado.
- `findGenderCountByStates`: Retorna a contagem de gêneros por estado usando `Tuple`.

### Observações

- Caso possua dados com a formatação de data dd/mm/yyyy, é necessário alterar manualmente no código esta formatação.
- Os testes de integração estão formatados para verificar os valores da lista de modelo.
- O endpoint `stateGenderCountMinMaxData` não aceita valores nulos, ou seja, se houver apenas um estado na lista, ele será exibido tanto no "min" como no "max".

Para mais detalhes, visite o repositório no GitHub: [summarize](https://github.com/enzoctmgeo/summarize).