# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

- **Build:** `./gradlew build`
- **Run:** `./gradlew bootRun` (starts on port 5000 with debug on port 5006)
- **Run all tests:** `./gradlew test` (auto-generates JaCoCo coverage report)
- **Run a single test class:** `./gradlew test --tests "homefinance.user.service.impl.UserServiceImplTest"`
- **Run a single test method:** `./gradlew test --tests "homefinance.user.service.impl.UserServiceImplTest.testMethodName"`
- **Clean build:** `./gradlew clean build`

## Tech Stack

- Java 17, Spring Boot 3.2.3, Gradle
- PostgreSQL with Liquibase migrations (`src/main/resources/changelog/`)
- Thymeleaf server-side templates with Spring Security integration
- Lombok, MapStruct 1.5.3 for boilerplate reduction and DTO mapping
- JUnit 5, Mockito, AssertJ, Testcontainers (PostgreSQL 15) for testing

## Architecture

Layered Spring Boot MVC application organized by domain module (`user`, `expense`, `money/currency`, `home`) plus a `common` module for cross-cutting concerns.

**Layer flow:** Controller → Facade → Service (interface + impl) → Repository (Spring Data JPA)

- **Facades** (`UserFacade`, `CurrencyFacade`, `LoginFacade`) sit between controllers and services, handling DTO↔Entity conversion and coordinating multiple service calls.
- **`ConstraintEntity`** base class provides a pattern for mapping database unique constraint violations to field-level errors via `constraintsMap`. Services implement `ConstraintPersist` to handle these.
- **`HandleDuplicationExceptionAspect`** is an AOP aspect that intercepts methods annotated with `@PossibleDuplicationException` or `@PossibleDuplicationExceptionViewNameInRequestBuffer`, catching `DuplicateFieldsException` and redirecting with field errors.
- **`RequestBuffer`** is a request-scoped bean used to pass view names between aspects and controllers during redirect flows.

## Testing

- **Unit tests** use Mockito with `@ExtendWith(MockitoExtension.class)`, mirror the source package structure.
- **Integration tests** extend `AbstractSpringIntegrationTest`, which uses Testcontainers to spin up a PostgreSQL 15 container. The container is shared across tests via `@Container` static field.
- Two integration tests are excluded from the default test task: `ExchangeRatesApiIntegrationTest` and `CurrenciesRestControllerIntegrationTest` (they call external APIs).

## Database

- Local PostgreSQL expected at `localhost:5432/homeFinance` (user: `postgres`, password: `postgres`), overridable via `JDBC_DATABASE_URL` env var.
- Schema managed by Liquibase; changelogs in `src/main/resources/changelog/`. Flyway is explicitly disabled.

## Security

Spring Security with role-based access (ADMIN/USER roles), OAuth2 client support, remember-me, BCrypt password encoding. CSRF is disabled for `/rest/**` endpoints.

## Internationalization

Message bundles in `src/main/resources/locales/` support English (`_en`) and Russian (`_ru`) for both UI messages and validation errors.