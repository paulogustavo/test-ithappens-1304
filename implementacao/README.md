## Estoque IT Happens

### Requisitos

* Java 1.8
* Docker 19.03.8
* Maven 3.5.4

### Subindo os containers

No diretório estoque, executar os comandos:

```
  mvn clean package
```
```
  docker-compose build
```
```
  docker-compose up
```

### Testes dos endpoints com o Swagger

Acesse o link: http://localhost:8081/swagger-ui.html