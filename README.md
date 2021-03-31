# web-crawler
Repositório que contém um micro serviço em Spring Boot que implementa um Web Crawler

### Technologias ###

* Spring Boot
* Java 11
* Junit4
* MongoDB
* Swagger
* Docker
* Docker Compose

### Decisões Arquiteturais ###

Spring Boot: Utilizado para servir como serviço Rest e Injeção de Dependência;

Docker: Facilitar a construção do projeto;

MongoDB: Banco de dados NOSQL orientado a documento, muito útil em manipulação de JSON;

Testes unitários: O foco dos testes ficou no Core da aplicação, evitando testes de get e set em modelos, só para aumentar a cobertura de código;

Docker-compose: Facilitar o deploy e configuração da aplicação.

Swagger: Documentação simples e intuitiva

### Deploy ###

Seguir o tutorial de instalação do docker

Seguir o tutorial de instalação do docker-compose

Baixar o docker-compose.yaml

Executar os comandos:

docker-compose pull
docker-compose up -d

### Swagger ###

Para acessar o Swagger Doc: http://localhost:8081/web-crawler/swagger-ui.html
