Template Spring Boot + MySQL (modo direto no MySQL)

Esse projeto já vem pronto pra subir com Spring Boot 3 + JPA e conectar no MySQL sem viadagem

O que já tá feito
- Spring Boot 3 (Web, Data JPA, driver MySQL)
- Entidade genérica `Item` com endpoints básicos (GET/POST em `/items`)
- Página raiz (`/`) mostrando status do banco, schemas, tabelas e lista de items
- Seed automático: toda vez que iniciar, limpa a tabela e insere 2 exemplos (só pra dev)

Como rodar usando MySQL
1) Tenha o MySQL Server rodando local (localhost:3306) e usuário `root` com senha configurada
2) Abra `main/src/main/resources/application.properties` e deixe assim (exemplo):
   - `spring.datasource.url=jdbc:mysql://localhost:3306/app_db?createDatabaseIfNotExist=true&serverTimezone=UTC&useSSL=false`
   - `spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver`
   - `spring.datasource.username=root`
   - `spring.datasource.password=senha` (troca pela sua)
3) Rodar o app:
   - `mvn -f ./main/pom.xml spring-boot:run`
4) Abrir no navegador:
   - `http://localhost:8080/`

Endpoints
- GET `/` – dashboard (schemas, tabelas, items)
- GET `/items` – lista items
- POST `/items` – cria item (JSON: `{ "name": "...", "description": "..." }`)

Testar no MySQL Workbench
1) Conectar no servidor local
2) Usar o schema `app_db` (vai ser criado automático na primeira subida)
3) Rodar: `SELECT * FROM items;` pra ver os itens de exemplo

Versões
- Java 17+
- Hibernate 6 (dialect detecta sozinho)
