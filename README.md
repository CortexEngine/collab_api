# Documentação Técnica — Collab API

Sistema de cadastro de colaboradores e departamentos com publicação de eventos via Kafka utilizando o padrão **Transactional Outbox**.

---

## Sumário

1. [Visão Geral](#1-visão-geral)
2. [Tecnologias e Dependências](#2-tecnologias-e-dependências)
3. [Arquitetura](#3-arquitetura)
4. [Estrutura do Projeto](#4-estrutura-do-projeto)
5. [Configurações](#5-configurações)
6. [Autenticação e Autorização](#6-autenticação-e-autorização)
7. [Rotas da API](#7-rotas-da-api)
8. [Banco de Dados](#8-banco-de-dados)
9. [Kafka e Transactional Outbox](#9-kafka-e-transactional-outbox)
10. [Value Objects](#10-value-objects)
11. [Hierarquia de Exceções](#11-hierarquia-de-exceções)
12. [Testes](#12-testes)
13. [Execução Local](#13-execução-local)
14. [Fluxo de Dados](#14-fluxo-de-dados)

---

## 1. Visão Geral

**Collab API** é uma API REST desenvolvida em Spring Boot 3.5 que gerencia:

- **Colaboradores**: cadastro com documentos brasileiros (CPF, RG, CNH, PIS, Carteira de Trabalho, Título de Eleitor), dados bancários (Banco, Agência, Conta, PIX), contato (e-mail, telefone) e vínculo com departamentos.
- **Departamentos**: estrutura organizacional com gerente, suporte e membros.

Toda operação de criação, atualização ou exclusão gera **eventos de domínio** que são persistidos em uma tabela **Outbox** e posteriormente publicados em **tópicos Kafka** por um job agendado.

---

## 2. Tecnologias e Dependências

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 21 | Linguagem |
| Spring Boot | 3.5.14 | Framework principal |
| Spring Data JPA / Hibernate | 6.x | ORM e persistência |
| Spring Security | 6.x | Autenticação e autorização |
| Spring Kafka | 3.x | Integração com Apache Kafka |
| PostgreSQL | — | Banco de dados relacional |
| Apache Kafka | — | Mensageria / event streaming |
| MapStruct | 1.5.5.Final | Mapeamento DTO ↔ Entity |
| Lombok | 1.18.30 | Redução de boilerplate |
| jjwt (io.jsonwebtoken) | 0.12.6 | Geração e validação de JWT |
| Hibernate Envers | — | Auditoria de entidades |
| Spring Retry | — | Retry/backoff |
| Testcontainers | — | Contêineres para testes de integração |
| Jakarta Mail (Angus) | 2.0.3 | Validação de e-mail |
| Maven Wrapper | 3.9.6 | Build |

### Dependências principais (`pom.xml`)

- `spring-boot-starter-data-jpa`
- `spring-boot-starter-web`
- `spring-boot-starter-security`
- `spring-boot-starter-validation`
- `spring-kafka`
- `postgresql` (runtime)
- `hibernate-envers`
- `mapstruct` + `mapstruct-processor`
- `lombok` + `lombok-mapstruct-binding`
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson`
- `spring-retry`
- `spring-boot-docker-compose` (opcional)
- `angus-mail` (validação de e-mail)
- `testcontainers:kafka`, `testcontainers:junit-jupiter` (testes)
- `spring-kafka-test` (testes)

---

## 3. Arquitetura

### Padrões

- **Domain-Driven Design (DDD)** — Value Objects ricos com validação no construtor, entidades com `@Audited` (Envers).
- **Transactional Outbox** — Eventos de domínio são salvos no banco (mesma transação) e publicados assincronamente no Kafka.
- **Hexagonal (Port-Adapter)** — Interface `DomainEventPublisher` (port) implementada por `OutboxDomainEventPublisher` (adapter).
- **DTOs segregados** — `*RequestDTO` (input) e `*ResponseDTO` (output) com validação via Bean Validation.

### Camadas

```
Controller → Service → Repository → PostgreSQL
                  ↓
        DomainEventPublisher (interface)
                  ↓
        OutboxDomainEventPublisher
                  ↓
             OutboxService → OutboxEventRepository
                  ↓
        OutboxRelayService (job @Scheduled)
                  ↓
          KafkaTemplate → Kafka
```

- `Controller` — Expõe endpoints REST, valida entrada com `@Valid`.
- `Service` — Orquestra validações, mapeamento e persistência; publica eventos.
- `Validator` — Componentes que validam regras de negócio (duplicidade, existência).
- `Mapper` — Interfaces MapStruct para conversão DTO ↔ Entity.
- `Repository` — Interfaces Spring Data JPA.
- `OutboxRelayService` — Job agendado que lê eventos da tabela `outbox_event` e envia ao Kafka.

---

## 4. Estrutura do Projeto

```
collab_api/
├── pom.xml
├── mvnw / mvnw.cmd
├── .env.example                    # Template de variáveis de ambiente
├── Dockerfile                      # (não presente — construir com Buildpacks ou JAR)
└── src/
    ├── main/
    │   ├── java/com/example/collab/
    │   │   ├── CollabApplication.java
    │   │   ├── config/
    │   │   │   ├── KafkaConsumerConfig.java
    │   │   │   ├── KafkaProducerConfig.java
    │   │   │   ├── KafkaTopicsProperties.java
    │   │   │   ├── KafkaTopicResolver.java
    │   │   │   └── security/
    │   │   │       ├── AuthProperties.java       # @ConfigurationProperties("app.auth")
    │   │   │       ├── AuthUser.java             # Record: username, password, roles
    │   │   │       ├── JwtAuthenticationFilter.java
    │   │   │       ├── JwtService.java
    │   │   │       └── SecurityConfig.java
    │   │   ├── controller/
    │   │   │   ├── AuthController.java           # /auth
    │   │   │   ├── CollaboratorController.java   # /collaborators
    │   │   │   └── DepartmentController.java     # /departments
    │   │   ├── dto/
    │   │   │   ├── request/  (CollaboratorRequestDTO, DepartmentRequestDTO, LoginRequest)
    │   │   │   └── response/ (CollaboratorResponseDTO, DepartmentResponseDTO, LoginResponse)
    │   │   ├── domain/
    │   │   │   ├── model/
    │   │   │   │   ├── Collaborator.java
    │   │   │   │   ├── Department.java
    │   │   │   │   └── OutboxEvent.java
    │   │   │   └── valueobject/
    │   │   │       ├── CollaboratorStatus.java    (enum)
    │   │   │       ├── ContractType.java          (value object)
    │   │   │       ├── OutboxStatus.java          (enum)
    │   │   │       ├── banking/ (Account, Agency, Bank, PIX, TypeAccount)
    │   │   │       ├── contact/ (Email, Phone)
    │   │   │       └── document/ (CNH, CNPJ, CPF, PIS, RG, VoterRegistration, WorkWallet)
    │   │   ├── exception/
    │   │   │   ├── CollabApiException.java        (base)
    │   │   │   ├── business/   (BadRequestException, InvalidDocumentException, etc.)
    │   │   │   ├── domain/     (DuplicatedCPFException, ConflictException, etc.)
    │   │   │   ├── resource/   (NotFoundException, ConfigurationException, etc.)
    │   │   │   ├── handler/GlobalExceptionHandler.java
    │   │   │   └── dto/ErrorResponse.java
    │   │   ├── mapper/
    │   │   │   ├── CollaboratorMapper.java        (MapStruct)
    │   │   │   └── DepartmentMapper.java          (MapStruct)
    │   │   ├── repository/
    │   │   │   ├── CollaboratorRepository.java
    │   │   │   ├── DepartmentRepository.java
    │   │   │   └── OutboxEventRepository.java
    │   │   └── service/
    │   │       ├── CollaboratorService.java
    │   │       ├── DepartmentService.java
    │   │       ├── OutboxService.java
    │   │       ├── OutboxRelayService.java
    │   │       ├── impl/OutboxDomainEventPublisher.java
    │   │       ├── port/DomainEventPublisher.java      (interface)
    │   │       └── validation/
    │   │           ├── CollaboratorValidator.java
    │   │           └── DepartmentValidator.java
    │   └── resources/
    │       ├── application.yml                       # Config principal
    │       └── kafka.truststore.p12                  # Truststore SSL - ESSE ARQUIVO DEVE SER GERADO NO SERVIDOR KAFKA
    └── test/java/com/example/collab/
        ├── CollabApplicationTests.java
        ├── controller/        (CollaboratorControllerTest, DepartmentControllerTest)
        ├── dto/request/       (CollaboratorRequestDTOTest, DepartmentRequestDTOTest)
        ├── dto/response/      (CollaboratorResponseDTOTest, DepartmentResponseDTOTest)
        ├── domain/model/      (CollaboratorTest, DepartmentTest)
        ├── domain/valueobject/ (todos os VOs: banking, contact, document)
        ├── mapper/            (CollaboratorMapperTest, DepartmentMapperTest)
        └── service/           (CollaboratorServiceTest, DepartmentServiceTest,
                                OutboxRelayServiceTest, OutboxServiceTest,
                                impl/OutboxDomainEventPublisherTest,
                                validation/CollaboratorValidatorTest,
                                validation/DepartmentValidatorTest)
```

---

## 5. Configurações

### 5.1 `application.yml`

```yaml
spring:
  application.name: collab
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver
  docker.compose.enabled: false
  autoconfigure.exclude:
    - org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
  jpa.hibernate.ddl-auto: update               # Auto-create/update schema
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9093}
    security.protocol: SASL_SSL
    ssl:
      trust-store-location: classpath:kafka.truststore.p12
      trust-store-password: ${KAFKA_SSL_TRUSTSTORE_PASSWORD}
      trust-store-type: PKCS12
    properties:
      "[sasl.mechanism]": SCRAM-SHA-512
      "[sasl.jaas.config]": ${KAFKA_PRODUCER_SASL_JAAS_CONFIG}
      "[ssl.endpoint.identification.algorithm]": ""
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    consumer:
      group-id: ${KAFKA_CONSUMER_GROUP_ID:collab-api}
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer

app:
  jwt:
    secret: "${JWT_SECRET}"
    expiration-ms: ${JWT_EXPIRATION_MS}
  auth:
    users:
      - username: ${JWT_USERNAME_1}
        password: "${JWT_PASSWORD_1}"
        roles: ${JWT_ROLES_1}
      - username: ${JWT_USERNAME_2}
        password: "${JWT_PASSWORD_2}"
        roles: ${JWT_ROLES_2}
  kafka:
    topics:
      collaborator-created: collab.collaborator.created
      collaborator-updated: collab.collaborator.updated
      collaborator-deleted: collab.collaborator.deleted
      department-created: collab.department.created
      department-updated: collab.department.updated
      department-deleted: collab.department.deleted
```

### 5.2 Variáveis de Ambiente (`.env`)

| Variável | Descrição | Exemplo |
|---|---|---|
| `DB_URL` | JDBC URL do PostgreSQL | `jdbc:postgresql://localhost:15432/collab_api` |
| `DB_USERNAME` | Usuário do banco | `postgres` |
| `DB_PASSWORD` | Senha do banco | `postgres123` |
| `KAFKA_BOOTSTRAP_SERVERS` | Endereço do Kafka | `localhost:9093` |
| `KAFKA_SECURITY_PROTOCOL` | Protocolo de segurança | `SASL_SSL` |
| `KAFKA_SASL_MECHANISM` | Mecanismo SASL | `SCRAM-SHA-512` |
| `KAFKA_SSL_TRUSTSTORE_PASSWORD` | Senha do truststore PKCS12 | — |
| `KAFKA_PRODUCER_SASL_JAAS_CONFIG` | JAAS config do producer | — |
| `KAFKA_CONSUMER_SASL_JAAS_CONFIG` | JAAS config do consumer | — |
| `KAFKA_CONSUMER_GROUP_ID` | Group ID do consumer | `collab-api` |
| `JWT_SECRET` | Chave HMAC (Base64) para assinar JWT | — |
| `JWT_EXPIRATION_MS` | Validade do token em ms | `86400000` (24h) |
| `JWT_USERNAME_1` | Usuário estático 1 | `admin` |
| `JWT_PASSWORD_1` | Senha bcrypt do usuário 1 | `$2b$12$...` |
| `JWT_ROLES_1` | Roles do usuário 1 | `ADMIN,USER` |
| `JWT_USERNAME_2` | Usuário estático 2 | `user` |
| `JWT_PASSWORD_2` | Senha bcrypt do usuário 2 | `$2b$12$...` |
| `JWT_ROLES_2` | Roles do usuário 2 | `USER` |

### 5.3 Propriedades do Outbox (defaults em código)

| Propriedade | Default | Descrição |
|---|---|---|
| `app.kafka.outbox.max-attempts` | `5` | Máximo de tentativas de publicação |
| `app.kafka.outbox.fixed-delay-ms` | `5000` | Intervalo entre polls (ms) |
| `app.kafka.outbox.recover-processing-ms` | `60000` | Intervalo para recuperar eventos travados (ms) |

### 5.4 Configurações do Consumer Kafka

- **Group ID**: `collab-api`
- **Auto offset reset**: `earliest`
- **Concorrência**: 3 listeners
- **Ack mode**: `MANUAL_IMMEDIATE`
- **Max poll records**: 100
- **Session timeout**: 30s
- **Heartbeat**: 10s

### 5.5 Classes `@ConfigurationProperties`

- **`AuthProperties`** — prefixo `app.auth`, mapeia lista de `AuthUser`.
- **`KafkaTopicsProperties`** — prefixo `app.kafka.topics`, mapeia os 6 nomes de tópicos.
- **`KafkaTopicResolver`** — componente que resolve o nome do tópico a partir do event type (`COLLABORATOR_CREATED` → `collab.collaborator.created`).

---

## 6. Autenticação e Autorização

### 6.1 Mecanismo

- **Usuários estáticos** definidos em variáveis de ambiente (2 usuários: `admin` e `user`).
- Senhas armazenadas como **hash bcrypt**.
- Login em `POST /auth/login` com `{username, password}` retorna JWT.
- `UserDetailsServiceAutoConfiguration` desabilitado (sem tabela de usuários).

### 6.2 JWT

- **Biblioteca**: jjwt 0.12.6
- **Chave**: HMAC-SHA (Base64 decodificada de `JWT_SECRET`)
- **Expiração**: 24 horas (`JWT_EXPIRATION_MS`)
- **Claims**: `sub` (username), `roles` (lista), `iat`, `exp`
- **Header esperado**: `Authorization: Bearer <token>`

### 6.3 Security Config (`SecurityConfig.java`)

- CSRF desabilitado
- Sessão stateless (`SessionCreationPolicy.STATELESS`)
- `POST /auth/**` — público (`permitAll()`)
- Demais rotas — autenticadas (`anyRequest().authenticated()`)
- `JwtAuthenticationFilter` adicionado antes do `UsernamePasswordAuthenticationFilter`
- `AuthenticationEntryPoint` customizado (401 JSON)
- `AccessDeniedHandler` customizado (403 JSON)

### 6.4 Roles

As roles são extraídas do JWT e prefixadas com `ROLE_` no `SecurityContext`. Atualmente **não há `@PreAuthorize`** — todos os usuários autenticados têm acesso a todos os endpoints.

---

## 7. Rotas da API

### 7.1 Autenticação

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| POST | `/auth/login` | Público | Autentica e retorna JWT |

**Request:**
```json
{ "username": "admin", "password": "..." }
```

**Response (200):**
```json
{
  "token": "eyJhbGciOiJIUz...",
  "type": "Bearer",
  "roles": ["ADMIN", "USER"]
}
```

### 7.2 Colaboradores (`/collaborators`)

| Método | Rota | Descrição |
|---|---|---|
| POST | `/collaborators` | Criar colaborador |
| GET | `/collaborators` | Listar todos |
| GET | `/collaborators/registration/{registration}` | Buscar por matrícula |
| GET | `/collaborators/cpf/{cpf}` | Buscar por CPF |
| GET | `/collaborators/name/{name}` | Buscar por nome |
| GET | `/collaborators/position/{position}` | Buscar por cargo |
| GET | `/collaborators/bank/{bank}` | Buscar por código do banco (3 dígitos) |
| PUT | `/collaborators/{registration}` | Atualizar por matrícula |
| DELETE | `/collaborators/{registration}` | Deletar por matrícula |
| DELETE | `/collaborators/cpf/{cpf}` | Deletar por CPF |

### 7.3 Departamentos (`/departments`)

| Método | Rota | Descrição |
|---|---|---|
| POST | `/departments` | Criar departamento |
| GET | `/departments` | Listar todos |
| GET | `/departments/number/{number}` | Buscar por número |
| GET | `/departments/name/{name}` | Buscar por nome |
| GET | `/departments/manager/{registration}` | Buscar por gerente |
| GET | `/departments/support_manager/{registration}` | Buscar por suporte |
| GET | `/departments/team_member?registrations=1,2,3` | Buscar por membros |
| PUT | `/departments/{number}` | Atualizar por número |
| DELETE | `/departments/{number}` | Deletar por número |

### 7.4 Formato de Erro

```json
{
  "timestamp": "2026-06-17T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "mensagem de erro",
  "path": "/api/collaborators",
  "details": {
    "fieldName": "erro de validação"
  }
}
```

`details` só aparece em erros de validação (`MethodArgumentNotValidException`, `ConstraintViolationException`).

---

## 8. Banco de Dados

### 8.1 Esquema (gerado por `ddl-auto: update`)

#### `collaborator`

| Coluna | Tipo | Observação |
|---|---|---|
| `id` | BIGINT (PK, auto-increment) | |
| `name` | VARCHAR(255) | |
| `birth_date` | DATE | |
| `marital_status` | VARCHAR(255) | |
| `nationality` | VARCHAR(255) | |
| `email` | VARCHAR(255) | Value Object → String via converter |
| `phone` | VARCHAR(255) | Value Object → String |
| `address` | VARCHAR(255) | |
| `position` | VARCHAR(255) | |
| `department_number` | INTEGER | FK → department (via @ManyToOne) |
| `admission_date` | DATE | |
| `contract_type` | VARCHAR(255) | Value Object → String |
| `status` | VARCHAR(255) | Enum → String |
| `salary` | DOUBLE | |
| `registration` | INTEGER | |
| `work_schedule` | INTEGER | |
| `manager` | BOOLEAN | |
| `support_manager` | BOOLEAN | |
| `bank` | VARCHAR(255) | Value Object → String |
| `agency` | VARCHAR(255) | Value Object → String |
| `account` | VARCHAR(255) | Value Object → String |
| `type_account` | VARCHAR(255) | Value Object → String |
| `pix` | VARCHAR(255) | Value Object → String |
| `work_wallet` | VARCHAR(255) | Value Object → String |
| `voter_registration` | VARCHAR(255) | Value Object → String |
| `reservist_certificate` | VARCHAR(255) | |
| `pis` | VARCHAR(255) | Value Object → String |
| `cnh` | VARCHAR(255) | Value Object → String |
| `cpf` | VARCHAR(255) | Value Object → String |
| `rg` | VARCHAR(255) | Value Object → String |
| `emergency_contact` | VARCHAR(255) | |
| `phone_emergency` | VARCHAR(255) | Value Object → String |
| `education` | VARCHAR(255) | |
| `course` | VARCHAR(255) | |
| `observations` | VARCHAR(255) | |
| `aud_*` | — | Colunas do Hibernate Envers |

Anotada com `@Audited` (Hibernate Envers) — tabela de auditoria `collaborator_AUD`.

#### `department`

| Coluna | Tipo | Observação |
|---|---|---|
| `id` | BIGINT (PK, auto-increment) | |
| `name` | VARCHAR(255) | |
| `number` | INTEGER (unique) | |
| `initial_date` | DATE | |
| `end_date` | DATE | |
| `manager_registration` | INTEGER | Referencia matrícula do colaborador |
| `manager_support_registration` | — | `@ElementCollection` (tabela separada) |
| `team_members_registration` | — | `@ElementCollection` (tabela separada) |

Anotada com `@Audited` (Hibernate Envers).

#### `outbox_event`

| Coluna | Tipo | Observação |
|---|---|---|
| `id` | BIGINT (PK, auto-increment) | |
| `event_id` | UUID | Gerado automaticamente |
| `aggregate_type` | VARCHAR(255) | `COLLABORATOR` ou `DEPARTMENT` |
| `aggregate_id` | VARCHAR(255) | ID da entidade |
| `event_type` | VARCHAR(255) | `COLLABORATOR_CREATED`, etc. |
| `topic` | VARCHAR(255) | Nome do tópico Kafka |
| `payload` | TEXT (Lob) | JSON serializado do DTO de response |
| `status` | VARCHAR(255) | `PENDING`, `PROCESSING`, `SENT`, `FAILED` |
| `attempts` | INTEGER | Default 0 |
| `created_at` | TIMESTAMP | |
| `published_at` | TIMESTAMP | |
| `processing_started_at` | TIMESTAMP | |
| `last_error` | TEXT | |

### 8.2 Conversores JPA

15 atributos `@Converter(autoApply = true)` que serializam/desserializam Value Objects para String no banco:

- `CpfConverter`, `RgConverter`, `CnhConverter`, `PisConverter`, `WorkWalletConverter`, `VoterRegistrationConverter`
- `BankConverter`, `AgencyConverter`, `AccountConverter`, `TypeAccountConverter`, `PixConverter`
- `EmailConverter`, `PhoneConverter`
- `ContractTypeConverter`

### 8.3 Repositories

**`CollaboratorRepository`** — métodos de busca por: `registration`, `position`, `contractType`, `CPF`, `RG`, `CNH`, `PIS`, `workWallet`, `voterRegistration`, `bank`, `account`, `pix`, `name`, `email`, `registrationAndManager`, `registrationAndSupportManager`. Também `deleteByRegistration`.

**`DepartmentRepository`** — métodos: `findByNumber`, `findByName`, `findByManagerRegistration`, `findByManagerSupportRegistrationContains`, `findByTeamMembersRegistrationIn`.

**`OutboxEventRepository`** — `findByStatusInAndAttemptsLessThanOrderByIdAsc` (com `@Lock(PESSIMISTIC_WRITE)`), `findByStatusAndProcessingStartedAtBefore`.

---

## 9. Kafka e Transactional Outbox

### 9.1 Tópicos

| Evento | Nome do Tópico |
|---|---|
| `COLLABORATOR_CREATED` | `collab.collaborator.created` |
| `COLLABORATOR_UPDATED` | `collab.collaborator.updated` |
| `COLLABORATOR_DELETED` | `collab.collaborator.deleted` |
| `DEPARTMENT_CREATED` | `collab.department.created` |
| `DEPARTMENT_UPDATED` | `collab.department.updated` |
| `DEPARTMENT_DELETED` | `collab.department.deleted` |

### 9.2 Fluxo do Outbox

```
1. Operação na entidade (create/update/delete)
   └── @Transactional
       ├── Validação
       ├── Persistência (save/delete)
       └── eventPublisher.publish(aggregateType, aggregateId, eventType, payload)
           └── OutboxDomainEventPublisher (Propagation.MANDATORY)
               └── OutboxService.saveEvent()
                   └── INSERT na tabela outbox_event (status=PENDING)

2. OutboxRelayService (job @Scheduled a cada 5s)
   └── Pessimistic Lock nos registros PENDING/FAILED
       ├── Marca como PROCESSING
       ├── Envia para Kafka via KafkaTemplate.send() (timeout 10s)
       │   ├── Sucesso → status=SENT, publishedAt=now
       │   └── Falha  → attempts++, status=FAILED, lastError=msg
       └── Commit da transação

3. Recovery (job @Scheduled a cada 60s)
   └── Eventos PROCESSING há mais de 2 minutos → revertidos para FAILED
```

### 9.3 Produtor (`KafkaProducerConfig`)

- `ProducerFactory<String, String>` a partir do `KafkaProperties` do Spring Boot
- `KafkaTemplate<String, String>`
- Serializer: `StringSerializer` (key e value)
- Security: SASL_SSL com SCRAM-SHA-512

### 9.4 Consumidor (`KafkaConsumerConfig`)

- `@EnableKafka`
- `ConsumerFactory<String, Object>`
- `ConcurrentKafkaListenerContainerFactory` com concurrency=3 e `MANUAL_IMMEDIATE` ack
- **Não há listeners Kafka definidos** — o consumer está configurado mas a aplicação atualmente apenas **produz** mensagens.

### 9.5 Payload

O payload de cada evento é o JSON do respectivo `*ResponseDTO` (ex: `CollaboratorResponseDTO` serializado).

---

## 10. Value Objects

Todos imutáveis (`@Value` do Lombok) com validação no construtor.

| Classe | Campo | Validação |
|---|---|---|
| `CPF` | `cpf` | 11 dígitos, dígitos verificadores, sem sequência repetida |
| `RG` | `rg` | 5-12 dígitos, sem repetição |
| `CNH` | `cnh` | 11 dígitos, sem repetição |
| `PIS` | `pis` | 11 dígitos, dígito verificador |
| `CNPJ` | `cnpj` | 14 dígitos, dígitos verificadores |
| `WorkWallet` | `number` | 11 dígitos, sem repetição |
| `VoterRegistration` | `voteId` | 12 dígitos |
| `Email` | `email` | Validação via `InternetAddress` (Jakarta Mail) |
| `Phone` | `number` | Formatos brasileiros (nacional/internacional, fixo/móvel) |
| `Bank` | `code` | Exatamente 3 dígitos |
| `Agency` | `number` | 4 dígitos + dígito verificador opcional |
| `Account` | `number` | 6-12 dígitos + dígito verificador opcional |
| `TypeAccount` | `type` | "Checking", "Savings" ou "Salary" |
| `PIX` | `key` | Valida como CPF, CNPJ, e-mail, telefone ou UUID |
| `ContractType` | `type` | "Hourly", "Monthly", "Per task" ou "Per project" |

### Enums

- `CollaboratorStatus`: `Active`, `Inactive`, `Vacation`, `Medical_Certificate`, `Absent`
- `OutboxStatus`: `PENDING`, `PROCESSING`, `SENT`, `FAILED`

---

## 11. Hierarquia de Exceções

```
CollabApiException (RuntimeException)
├── BadRequestException (400)                    [business]
│   ├── InvalidCollaboratorException
│   ├── InvalidDepartmentException
│   ├── InvalidManagerException
│   └── InvalidSupportManagerException
├── UnprocessableEntityException (422)           [business]
│   └── InvalidDocumentException
├── ConflictException (409)                      [domain]
│   ├── DuplicatedAccountException
│   ├── DuplicatedCNHException
│   ├── DuplicatedCPFException
│   ├── DuplicatedDepartmentMemberException
│   ├── DuplicatedEmailException
│   ├── DuplicatedNameDepartmentException
│   ├── DuplicatedNumberDepartmentException
│   ├── DuplicatedPISException
│   ├── DuplicatedPixException
│   ├── DuplicatedRGException
│   ├── DuplicatedVoteRegistrationException
│   └── DuplicatedWorkWalletException
├── NotFoundException (404)                      [resource]
│   ├── NotFoundCollaboratorException
│   ├── NotFoundDepartmentException
│   └── DepartmentNotFoundException (sinônimo)
└── ConfigurationException
```

**Tratamento global** (`GlobalExceptionHandler` via `@ControllerAdvice`):

| Handler | HTTP Status |
|---|---|
| `NotFoundException` | 404 |
| `BadRequestException` | 400 |
| `ConflictException` | 409 |
| `UnprocessableEntityException` / `InvalidDocumentException` | 422 |
| `MethodArgumentNotValidException` | 400 (com `details` dos campos) |
| `ConstraintViolationException` | 400 (validação de path/query params) |
| `ResponseStatusException` | conforme o status interno |
| `Exception` (genérico) | 500 |

---

## 12. Testes

### 12.1 Framework

- **JUnit 5** + **Spring Boot Test**
- **Testcontainers** com Kafka para testes de integração
- **Mockito** (via Spring Boot Test)

### 12.2 Cobertura

| Pacote | Testes |
|---|---|
| `domain/valueobject/banking/` | Account, Agency, Bank, PIX, TypeAccount |
| `domain/valueobject/contact/` | Email, Phone |
| `domain/valueobject/document/` | CNH, CNPJ, CPF, PIS, RG, VoterRegistration, WorkWallet |
| `domain/model/` | Collaborator, Department |
| `dto/request/` | CollaboratorRequestDTO, DepartmentRequestDTO |
| `dto/response/` | CollaboratorResponseDTO, DepartmentResponseDTO |
| `mapper/` | CollaboratorMapper, DepartmentMapper |
| `service/` | CollaboratorService, DepartmentService, OutboxService, OutboxRelayService, OutboxDomainEventPublisher |
| `service/validation/` | CollaboratorValidator, DepartmentValidator |
| `controller/` | CollaboratorController, DepartmentController |

### 12.3 Execução

```bash
./mvnw test
```

Relatórios gerados em `target/surefire-reports/`.

---

## 13. Execução Local

### Pré-requisitos

- Java 21+
- PostgreSQL rodando (porta 15432 ou configurada)
- Kafka rodando (porta 9093 ou configurada) com SASL_SSL/SCRAM-SHA-512
- Truststore PKCS12 em `src/main/resources/kafka.truststore.p12`

### Passos

1. **Configurar variáveis de ambiente** (copiar `.env.example` para `.env` e preencher)

2. **Build**:
```bash
./mvnw clean package -DskipTests
```

3. **Executar**:
```bash
./mvnw spring-boot:run
```

Ou via JAR:
```bash
java -jar target/collab-api-0.0.1-SNAPSHOT.jar
```

4. **Autenticar**:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"..."}'
```

5. **Usar o token**:
```bash
curl http://localhost:8080/collaborators \
  -H "Authorization: Bearer <token>"
```

### VS Code

Usar a configuração `CollabApplication (env)` no `.vscode/launch.json`, que carrega o `.env` automaticamente.

---

## 14. Fluxo de Dados

### Criação de Colaborador

```
POST /collaborators
  │
  ├── CollaboratorController.createCollaborator()
  │     └── @Valid CollaboratorRequestDTO
  │
  ├── CollaboratorService.createCollaborator() (@Transactional)
  │     ├── CollaboratorValidator.validateNewCollaboratorDocuments()
  │     │     └── DuplicatedCPFException (409) se CPF já existe
  │     ├── CollaboratorValidator.validateNewCollaboratorBank()
  │     ├── CollaboratorValidator.validateNewCollaboratorData()
  │     ├── CollaboratorValidator.validateCollaboratorManager()
  │     ├── CollaboratorMapper.toEntity() → Collaborator
  │     ├── collaboratorRepository.save(collaborator)
  │     ├── CollaboratorMapper.toResponse() → CollaboratorResponseDTO
  │     └── eventPublisher.publish("COLLABORATOR", id, "COLLABORATOR_CREATED", response)
  │           └── OutboxDomainEventPublisher.publish() (Propagation.MANDATORY)
  │                 └── OutboxService.saveEvent()
  │                       └── INSERT outbox_event (status=PENDING)
  │
  └── Response 201: CollaboratorResponseDTO

  (assincronamente)
  OutboxRelayService.publishPendingEvents() [a cada 5s]
    └── SELECT outbox_event WHERE status IN (PENDING, FAILED)
    └── KafkaTemplate.send("collab.collaborator.created", id, payload)
    └── UPDATE outbox_event SET status=SENT
```

### Criação de Departamento

```
POST /departments
  │
  ├── DepartmentController.createDepartment()
  ├── DepartmentService.createDepartment() (@Transactional)
  │     ├── DepartmentValidator.validateDepartmentName()    ← unique
  │     ├── DepartmentValidator.validateDepartmentNumber()  ← unique
  │     ├── DepartmentValidator.validateDepartmentManager()  ← existe e é manager
  │     ├── DepartmentValidator.validateDepartmentSupportManager()
  │     ├── DepartmentValidator.validateDepartmentMembers()
  │     ├── DepartmentMapper.toEntity() → Department
  │     ├── departmentRepository.save(department)
  │     └── eventPublisher.publish("DEPARTMENT", id, "DEPARTMENT_CREATED", response)
  │           → OutboxEvent → Kafka (mesmo fluxo do colaborador)
  │
  └── Response 201: DepartmentResponseDTO
```

---

## Observações para Desenvolvimento

1. **Não há listeners Kafka** — os eventos são apenas produzidos. Para consumi-los, criar um `@KafkaListener` em um novo serviço.
2. **Usuários estáticos** — para adicionar mais usuários, aumentar a lista `app.auth.users` no `application.yml` e adicionar as variáveis no `.env`.
3. **`@PreAuthorize`** não está implementado — pode ser adicionado nos controllers ou services para segregar roles.
4. **Tabelas são auto-criadas** pelo Hibernate (`ddl-auto: update`). Para produção, usar `validate` ou migrações (Flyway/Liquibase).
5. **Truststore PKCS12** do Kafka deve ser mantida em `src/main/resources/kafka.truststore.p12`.
6. **Mapper de Department** para o campo `department` no `CollaboratorMapper` retorna `null` propositalmente — o vínculo é feito pelo `department_number` via JPA.
