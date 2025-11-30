# 🔒 Safelink - Sistema de Prevenção a Fraudes

<p align = "justify">
O projeto <b>Safelink</b> foi concebido como uma plataforma comunitária robusta para a prevenção e combate a diferentes tipos de golpes e crimes digitais. A plataforma capacita usuários (<b>Clientes</b>) a se protegerem por meio de relatórios de incidentes, pesquisa de dados suspeitos e acesso a uma base de dados dos <b>top dados mais envolvidos em golpes</b>. Um diferencial central é a geração de um <b>Guia de Ação Personalizado</b>, utilizando o <b>Azure OpenAI</b>, para auxiliar vítimas no pós-golpe. Além disso, o sistema expande seu escopo para o setor corporativo (<b>Empresas</b>), oferecendo o serviço <b>Shortener</b>, um encurtador de links com domínio e selo de segurança da Safelink.
</p>

## 💻 Arquitetura Distribuída

O Safelink se comunica através de princípios REST, orquestrando três componentes principais: a **API Principal (Gateway)** e dois **Microsserviços**.

| Componente | Função Principal | Tecnologia |
| :--- | :--- | :--- |
| **Frontend** | Interface de usuário (Comunidade e Empresas). | React, CSS |
| **API Principal (Gateway)** | Autenticação, Gerenciamento de Usuários e Orquestração de Microsserviços. | Java 21, Spring Boot |
| **Microsserviço Azure OpenAI** | Geração do Guia de Ação Personalizado (IA Cloud). | Azure OpenAI |
| **Microsserviço Shortener** | Serviço de encurtamento de links B2B. | Java 21, PostgreSQL |

## 📱 Tecnologias

* **Linguagens:** Java 21, JavaScript.
* **Backend:** Spring Boot (Web, JPA, Security, Validation).
* **Frontend:** React, CSS, **React Router DOM**.
* **Base de Dados:** PostgreSQL Relational Database (Principal e Shortener).
* **Microsserviços:** Azure OpenAI (IA Cloud), Shortener (Java 21).
* **Segurança:** OAuth2 Resource Server, JWT Token encriptado com SSL.
* **Controle de Versão:** Flyway Migrations, Lombok.
* **Testes:** JUnit 5 e Mockito.
* **Orquestração Local:** **Docker** e **Docker Compose**.

## ☁️ Deploy

<p align = "justify">
A <b>API Principal</b> e seu banco de dados foram desenvolvidos para rodar localmente e de forma isolada usando <b>containers Docker</b>. Para iniciar todo o ambiente localmente (API e Database), basta utilizar o arquivo <b>docker-compose.yaml</b>.

<b>Coleção do Postman:</b>
Você pode importar a coleção abaixo no seu cliente HTTP para testar as rotas da API Principal:

- Postman Collection [Download](https://drive.google.com/file/d/1IkJnd025w6abr5pcLlnwY-JPXJ96YO3S/view?usp=sharing)

O <b>Microsserviço Shortener</b> está implantado na nuvem e acessível de forma independente. O deploy foi realizado utilizando os serviços do <b>Render.com</b>, garantindo alta disponibilidade e sustentabilidade para as operações de encurtamento.
</p>

## ⚙️ Configuração

O ambiente completo do projeto Safelink requer a execução de dois processos principais: o **Backend/Database (via Docker)** e o **Frontend (localmente)**.

### Pré-requisitos
Antes de começar, você precisa ter instalado:
-   **Cryptography** [Download OpenSSL](https://sourceforge.net/projects/openssl/)
-   **Orquestração de Containers:** [Download Docker](https://www.docker.com/products/docker-desktop/)
-   **Versionamento:** [Download GIT](https://git-scm.com/downloads)
-   **Ambiente JavaScript:** **Node.js e NPM/Yarn** (Necessário para o React)
-   **HTTP Client** (Para testar a API): [Download Postman](https://www.postman.com/downloads/)

### Variáveis de Ambiente
Você precisará definir as variáveis de ambiente no arquivo **`.env`** que você deve criar em: `Safelink/backend/safelink-api`.

* **Chaves de Segurança (Criptografia JWT):**
    ```
    # use command prompt
    
    # generate private key:
    openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048
    # generate public key:
    openssl rsa -pubout -in private_key.pem -out public_key.pem
    
    # copy the content into the variables
    ```
    * `PUBLIC_KEY`
    * `PRIVATE_KEY`
* **Acesso ao Azure OpenAI:**
    * `API_KEY` (Você deve ter acesso a plataforma do Microsoft Azure)
* **Banco de Dados (PostgreSQL):**
    * `DATABASE_USERNAME`
    * `DATABASE_PASSWORD`
    * `DATABASE_URL`

### Execução Completa

#### Passo 1: Iniciar o Backend e a Base de Dados (via Docker)
Siga as instruções para clonar o repositório e iniciar o ambiente Backend:

```bash
# clone o repositório
git clone https://github.com/pedroxcav/Safelink.git

# selecione a pasta do projeto
cd Safelink/backend/safelink-api

# Subir a API Principal e o Banco de Dados (em background)
# O Docker Compose irá construir as imagens e iniciar os containers
docker compose up --build

# Aguarde alguns segundos até que a API esteja online.
```

### Passo 2: Instalar e Iniciar o Frontend (React)
Após a API estar rodando, você deve iniciar o Frontend (assumindo que ele está em uma subpasta chamada frontend e configurado para se conectar a http://localhost:8080).

```bash
# Navegue para a pasta do Frontend
cd Safelink/frontend/safelink-app

# 1. Instalar as dependências do projeto (incluindo react-router-dom)
npm install react-router-dom

# 2. Rodar a aplicação em modo desenvolvimento
npm start
```
Após esses passos, o sistema completo (Frontend se comunicando com a API Gateway, que usa os microsserviços) estará rodando em sua máquina.

## 📋 Documentation
#### Endpoints

<details>
  <summary>Empresa Controller</summary>

  1. **POST** `/empresa`
     Cria (registra) uma nova empresa

  2. **POST** `/empresa/login`
     Autentica (login) uma empresa, retornando o JWT

  3. **GET** `/empresa`
     Retorna os dados da empresa autenticada

  4. **PUT** `/empresa`
     Atualiza os dados da empresa autenticada

  5. **DELETE** `/empresa`
     Deleta o registro da empresa autenticada
</details>

<details>
  <summary>Cliente Controller</summary>

  1. **POST** `/cliente`
     Cria (registra) um novo cliente (usuário)

  2. **POST** `/cliente/login`
     Autentica (login) um cliente, retornando o JWT

  3. **GET** `/cliente`
     Retorna os dados do cliente autenticado

  4. **PUT** `/cliente`
     Atualiza os dados do cliente autenticado

  5. **DELETE** `/cliente`
     Deleta o registro do cliente autenticado
</details>

<details>
  <summary>Relato Controller</summary>

  1. **POST** `/relato`
     Cria um novo relato de golpe/crime digital (requer autenticação)

  2. **GET** `/relato`
     Retorna todos os relatos do cliente autenticado

  3. **DELETE** `/relato/{id}`
     Deleta um relato específico por ID (requer autenticação)

  4. **GET** `/relato/dado?tipo={...}`
     Retorna os relatos por Tipo de Dado suspeito

  5. **GET** `/relato/verifica?tipo={...}&valor={...}`
     Verifica se um dado específico (ex: telefone) tem relatos associados
</details>

<details>
  <summary>Link Controller</summary>

  1. **POST** `/link`
     **Chama o Microsserviço Shortener** para encurtar um link real (requer autenticação de Empresa)

  2. **GET** `/link`
     Retorna todos os links encurtados pela Empresa autenticada

  3. **DELETE** `/link`
     Deleta um link encurtado (requer autenticação de Empresa)
</details>

<details>
  <summary>Telefone Controller</summary>

  1. **GET** `/telefone`
     Retorna o telefone do usuário autenticado

  2. **PUT** `/telefone`
     Atualiza o telefone do usuário autenticado
</details>

### Autor
Project developed by Pedro Cavalcanti, Gabriel Moreno e Bruno Morais.

Doubts or suggestions, message me here: 

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2.svg?style=for-the-badge&logo=LinkedIn&logoColor=white)](https://www.linkedin.com/in/pedroxcav/)
[![Instagram](https://img.shields.io/badge/Instagram-%23E4405F.svg?style=for-the-badge&logo=Instagram&logoColor=white)](https://www.instagram.com/pedroxcav/)
[![Gmail](https://img.shields.io/badge/Gmail-000000.svg?style=for-the-badge&logo=Gmail&logoColor=white)](mailto:pedroxcav@gmail.com)
