# HomeFinance

Personal finance management web application built with Spring Boot.

## Prerequisites

- Java 17
- Docker

## Running the Application

### Option 1: Everything in Docker

Starts both PostgreSQL and the app:

```bash
docker compose up -d
```

App available at http://localhost:5050

To rebuild after code changes:

```bash
docker compose up -d --build
```

### Option 2: IDE + Docker (for development)

Start only PostgreSQL:

```bash
docker compose up -d postgres
```

Then run the app from your IDE in debug mode. App available at http://localhost:5050, debug port at 5006.

### Verify PostgreSQL is running

```bash
docker compose ps
```

### Stop everything

```bash
docker compose down
```

## Build & Test

```bash
./gradlew build           # build the project
./gradlew test            # run all tests (generates JaCoCo coverage report)
./gradlew clean build     # clean build
```

Run a single test class:

```bash
./gradlew test --tests "homefinance.user.service.impl.UserServiceImplTest"
```

Run a single test method:

```bash
./gradlew test --tests "homefinance.user.service.impl.UserServiceImplTest.testMethodName"
```

## Tech Stack

- Java 17, Spring Boot 3.2.3, Gradle
- PostgreSQL 15 with Liquibase migrations
- Thymeleaf, Spring Security, OAuth2
- Lombok, MapStruct
- JUnit 5, Mockito, Testcontainers