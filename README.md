# ForumHubApi

API REST para um fórum de discussão, desenvolvida com Java 17, Spring Boot 3.5.4 e MySQL. Projeto baseado em desafio da plataforma Alura, com foco principal na modelagem, criação e gestão de tópicos.

## 🚀 Funcionalidades

* ✅ Autenticação com Spring Security e JWT
* ✅ CRUD completo de Tópicos
* ✅ Relacionamento com Usuários e Cursos
* ✅ Validações e tratamento centralizado de erros
* ✅ Migrations com Flyway
* ✅ Padrão DTO para entrada e saída de dados
* ✅ Testado via Insomnia

---

## 💠 Tecnologias utilizadas

* Java 17
* Spring Boot 3.5.4
* Spring Data JPA
* Spring Security
* JWT (JSON Web Token)
* MySQL
* Flyway
* Lombok
* Maven

---

## 🧹 Endpoints principais

| Método | Endpoint        | Descrição                     | Autenticação |
| ------ | --------------- | ----------------------------- | ------------ |
| POST   | `/login`        | Geração de token JWT          | ❌            |
| POST   | `/topicos`      | Registrar novo tópico         | ✅            |
| GET    | `/topicos`      | Listar todos os tópicos       | ✅            |
| GET    | `/topicos/{id}` | Detalhar um tópico específico | ✅            |
| PUT    | `/topicos/{id}` | Atualizar um tópico existente | ✅            |
| DELETE | `/topicos/{id}` | Excluir um tópico             | ✅            |

### 🔐 Autenticação

Para acessar os endpoints protegidos, envie um token JWT no cabeçalho:

```http
Authorization: Bearer <seu-token-jwt>
```

---

## 🧪 Testando com Insomnia

A coleção de requisições pode ser importada no Insomnia ou Postman. Certifique-se de seguir os seguintes passos:

1. Autentique-se usando `/login`
2. Copie o token JWT da resposta
3. Use o token para testar os endpoints de Tópicos

---

---

## 🎥 Demonstração da API

Veja abaixo alguns exemplos de requisições realizadas com o Insomnia:

### 🔐 Autenticação

![Image](https://github.com/user-attachments/assets/6eac2de8-622b-4d1e-a76d-294bdbad001f)

### 📝 Criação de tópico

![Image](https://github.com/user-attachments/assets/b5510974-2945-44eb-a11d-a21b42a4b1d1)

### 📋 Listagem de tópicos

![Image](https://github.com/user-attachments/assets/4e65d845-c453-4d04-8b9b-c90ad5ccfd86)

![Image](https://github.com/user-attachments/assets/65661776-c613-4edb-b5bd-0237b289f4fe)

### 🔄 Atualização de tópico

![Image](https://github.com/user-attachments/assets/d1558564-dfca-460b-a51f-cd58585f189c)
### ❌ Exclusão de tópico

![Image](https://github.com/user-attachments/assets/8bbac248-72fb-44a0-801c-9f05a97964d5)

> ⚠️ Os GIFs foram gravados com [ScreenToGif](https://www.screentogif.com/). As credenciais e tokens são fictícios.



## 🗃️ Migrations (Flyway)

As migrations SQL estão no diretório:

```
src/main/resources/db/migration
```

Para recriar o banco do zero:

1. Acesse o MySQL e execute:

   ```sql
   DROP DATABASE forumhub;
   CREATE DATABASE forumhub;
   ```
2. Rode a aplicação novamente para que o Flyway aplique as migrations automaticamente.

---

## ⚙️ Configurações

No arquivo `application.properties`:

```properties
spring.application.name=forum-hub-api

spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
spring.datasource.username=<seu-usuario>
spring.datasource.password=<sua-senha>

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

api.security.secret=minhaChaveSecretaMuitoSegura1234567890
```

Você também pode usar variáveis de ambiente (`FORUMHUB_DB_URL`, `FORUMHUB_DB_USERNAME`, etc.).

---

## 🧑‍💻 Autor

**Dev Heitor Matos**
Desenvolvedor Backend em formação com foco em aplicações Java.

---

## 📄 Licença

Este projeto está licenciado sob os termos da [MIT License](LICENSE).
