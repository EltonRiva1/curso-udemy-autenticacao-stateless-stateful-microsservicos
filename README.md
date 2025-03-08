# 📌 Autenticação Stateful e Stateless em Microsserviços

![Badge](https://img.shields.io/badge/Status-%20Concluído-green) ![Java](https://img.shields.io/badge/Java-17-blue) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen) ![Docker](https://img.shields.io/badge/Docker-20.10.7-blue)

Este projeto é uma implementação baseada no curso **"Autenticação Stateful e Stateless em Microsserviços"** da Udemy. O objetivo é demonstrar diferentes abordagens de autenticação em microsserviços utilizando tecnologias como **Java**, **Spring Boot** e **Docker**.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Security**
- **JWT (JSON Web Token)**
- **PostgreSQL** (via Docker)
- **Docker & Docker Compose**

## 📂 Estrutura do Projeto

O projeto está dividido em duas principais abordagens de autenticação:

1. **Stateful** → Sessão do usuário mantida no servidor.
2. **Stateless** → Autenticação via JWT, sem necessidade de estado no servidor.

## 🔐 Segurança

- **Stateful**: Autenticação baseada em sessão, utilizando Spring Security com armazenamento no servidor.
- **Stateless**: Autenticação baseada em tokens JWT, onde cada requisição carrega um token para validação.

## 🐳 Docker

O projeto utiliza **Docker** para containerização dos microsserviços e do banco de dados **PostgreSQL**, garantindo portabilidade e facilidade de deploy.

### Subindo os containers com Docker Compose:

```bash
docker-compose up -d
```

Isso iniciará os microsserviços e o banco de dados automaticamente.

## 📦 Como Executar o Projeto

### Pré-requisitos:

- Docker instalado na máquina.
- Docker Compose configurado.

### Passos para execução:

1. Clone o repositório:

   ```bash
   git clone https://github.com/EltonRiva1/curso-udemy-autenticacao-stateless-stateful-microsservicos.git
   ```

2. Acesse o diretório do projeto:

   ```bash
   cd curso-udemy-autenticacao-stateless-stateful-microsservicos
   ```

3. Suba os containers:

   ```bash
   docker-compose up -d
   ```

4. Acesse os serviços:

   - **Stateful Auth Service** → `http://localhost:8080`
   - **Stateless Auth Service** → `http://localhost:8081`

## 📡 Endpoints Principais

### Stateful Auth Service (Sessão)

- **POST** `/login` → Autentica um usuário e inicia uma sessão.
- **POST** `/register` → Registra um novo usuário.

### Stateless Auth Service (JWT)

- **POST** `/login` → Autentica um usuário e retorna um token JWT.
- **POST** `/register` → Registra um novo usuário.

## 🧪 Testes

Para rodar os testes:

```bash
./mvnw test
```

---

🔹 **Desenvolvido por [Elton Riva](https://github.com/EltonRiva1) 🚀**

