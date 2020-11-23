# Weather APP

Aplicação de previsão de tempo usando Spring Boot, Docker, MySQL, Angular e integração com WeatherAPI.

## Getting Started

Segue abaixo os requisitos e instruções para rodar o projeto local.

### Prerequisites

É necessário ter instalado na máquina:
- Docker 
- Docker Compose
- Git

Para mais informações de como instalar segue o link:
[Instalação Docker](https://docs.docker.com/get-docker/)
[Instalação Docker Compose](https://docs.docker.com/compose/)

### Installing and Building

Segue abaixo o passo a passo para execução do projeto:

1. Clone o projeto

```
git clone https://github.com/gabriellima96/weather-app.git
```

2. Navegue para a pasta do projeto

```
cd weather-app
```

3. Construa o projeto com o docker-compose
```
docker-compose up --build
```

4. Acessar a aplicação: http://localhost:4200

## Built With

* Maven - Gerenciador de dependências
* Spring Boot - Projetos com configurações Mínimas
* Spring Web - Aplicações para Web MVC
* Spring Data JPA - Integração para o bancos SQL
* JUnit - Para construção de testes unitários
* Mockito - Para simular em teste chamadas de serviços
* Bean Validation - Validar objetos com facilidade
* Lombok - Automatização na criação de getters e setters
* MySQL - Banco SQL
* H2 - Banco em memória para testes
* EhCache - Para gerencimento de cache de segundo nível
* FlywayDB - Controle de versão para banco de dados SQL
* Angular - Framework para construção de aplicações WEB SPA
* WeatherAPI - Integração com a API de previsões do tempo (https://www.weatherapi.com/)

## Funcionalidades

1. Cadastro, listagem e filtro das cidades
2. Previsão do tempo de até 5 dias da cidade com as informações de condição do tempo, temperatura e data.
3. Não é permitido cadastra uma cidade já cadastrada.
5. É realizado o cache da previsão do tempo por 15 minutos, para uma próxima consulta na API WeatherAPI
6. É realizado o cache da listagem das cidades até uma nova cidade for cadastrada.
7. Aplicativo responsivo

## Telas (prints)

Link do protótipo no figma: [Figma](https://www.figma.com/file/oZd1QXrTK0mkDvEjx5BfcA/Aplicativo-de-tempo?node-id=0%3A1)

### Home - Tela principal com a listagem das cidades, filtro e opção de adicionar uma nova cidade.

### Forecast - Tela com a previsão do tempo de uma cidade


## Endpoints

### Cities

#### GET http://localhost:8080/api/v1/cities
Buscar as cidades

**Parameters**
- search (string) - optional 
- size (string) - optional
- sort (string) - optional
- page (string) - optional
- direction (string) - optional

**Response**

``` 
/// Response: Success - 200
{
    "content": [
        {
            "id": 3,
            "name": "Blumenau",
            "country": "Brazil"
        },
        {
            "id": 1,
            "name": "Maceio",
            "country": "Brazil"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 2,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 5,
    "totalElements": 9,
    "last": false,
    "size": 2,
    "number": 0,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
}
```
Exemplo:
http://localhost:8080/api/v1/cities?search=&size=2&sort=country,name&page=0

#### GET http://localhost:8080/api/v1/cities/{id}/forecasts
Buscar a previsão de tempo de até 5 dias por id da cidade

**Path variable **
- id (number) - required
```
/// Response Success - 200
{
    "city": {
        "name": "Maceio",
        "country": "Brazil"
    },
    "forecasts": [
        {
            "date": "2020-11-23",
            "tempMin": 22.6,
            "tempMax": 31.3,
            "conditionText": "Possibilidade de chuva irregular",
            "conditionIconUrl": "https://cdn.weatherapi.com/weather/64x64/day/176.png"
        },
        {
            "date": "2020-11-24",
            "tempMin": 22.7,
            "tempMax": 27.3,
            "conditionText": "Chuva moderada",
            "conditionIconUrl": "https://cdn.weatherapi.com/weather/64x64/day/302.png"
        },
        {
            "date": "2020-11-25",
            "tempMin": 22.1,
            "tempMax": 30.4,
            "conditionText": "Possibilidade de chuva irregular",
            "conditionIconUrl": "https://cdn.weatherapi.com/weather/64x64/day/176.png"
        }
    ]
}
///
```
Exemplo:
http://localhost:8080/api/v1/cities/1/forecasts

#### POST http://localhost:8080/api/v1/cities
Salvar uma nova cidade

**Body**
```
/// Request Body
{
	"name": "Barra da Tijuca"
}
///
```

```
/// Response Success - 200
{
  "id": 3,
	"name": "Barra da Tijuca",
  "country": "Brazil"
}
///
```
Exemplo:
http://localhost:8080/api/v1/cities

## Architecture

![Arquitetura](blob/main/architecture.png)

## Author

* **Gabriel Lima** - [https://gabriellima.site](https://gabriellima.site)

## License

Este projeto está licenciado sob a licença MIT - consulte o arquivo [LICENSE.md] (LICENSE.md) para obter detalhes
