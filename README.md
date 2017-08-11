# Serviço de controle de status de veículos

Esta aplicação é um serviço que permite o cadastro e consulta de placas de veículos e seu status num banco de dados local.

### Tecnologias

Esta aplicação utiliza o SpringBoot para ter um servidor embarcado e gera um banco SQLite local para os dados.

Spring Boot 

Spring Boot JPA

Spring Boot Test

DBUnit

SQLite

Tomcat


### Deploy

Para executar a aplicação primeiro precisa compilar com o maven: mvn clean install
Depois pode ser executado via jar ou via maven:

Jar:
Na raiz do projeto
cd target
java  -jar cadastro.status-0.1.0.jar

Maven:
Na raiz do projeto
mvn spring-boot:run

### Dependências

Java 8
Maven 3

### Serviços que podem ser acessados

As URLs da aplicação de cadastro de status que a biblioteca acessa são:

Requisição GET: http://localhost:8081/placa/consultar?numero=Placa do veículo<br>
Requisição GET: http://localhost:8081/placa/consultar?status=OK<br>
Requisição POST: http://localhost:8081/placa/cadastrar<br>
Requisição POST: http://localhost:8081/placa/atualizar?numero=Placa do veículo<br>
Requisição DELETE: http://localhost:8081/placa/excluir?numero=Placa do veículo


### Testes

Foram implementados testes de integração nos testes automatizados. 
Como o banco criado é o SQLite ele cria uma base inicial de banco de dados par ao SQLite com a conexão de testes.
Os teste recebem um requisição com um servidor web mock especifico para testes e fazem todo o fluxo até o banco de dados e validam o JSON da resposta dos serviços da aplicação.


### Considerações
Projeto foi liberado na versão 0.1.0. Mas não foi gerado nenhum tag;
Não foram colocados logs na aplicação por falta de tempo, mas os logs seriam colocados com uma lib de aspecto para manter o código limpo e conseguir usar o log sem interferir na aplicação;
O código foi feito em português por escolha pessoal, apesar de poder ser em inglês acredito que fica melhor escrito em português pelo modelo estar em português. 
A execução dos testes automatizados está lenta pois o SQLite demora cerca de 2 segundos para limpar e recriar a base com DBUnit, mas a lentidão só ficou perceptível no final e acabei ficando sem tempo de melhorar a performa, é o delete do SQLite sem configuração que causa a lentidão.
