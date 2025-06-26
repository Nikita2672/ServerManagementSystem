# 🖥️ Server Management System

**Server Management System** — это демонстрационный backend-сервис, реализованный на Kotlin и Spring Boot, предназначенный для управления инфраструктурой серверов, сотрудников и организаций.  
Проект разработан в целях демонстрации моих навыков в проектировании архитектуры, работе с базами данных, тестированию и построению REST API.

---

## 🚀 Возможности

- Управление компаниями, отделами и сотрудниками
- Регистрация и контроль серверов
- Привязка серверов к ответственным сотрудникам
- Каскадное удаление зависимостей (Company → Department → Employee → Server)
- RESTful API с использованием Spring Web
- Документация OpenAPI (Swagger UI)
- Интеграционные тесты с использованием MockMvc и TestContainers
- Миграции базы данных через Flyway
- Использование JPA и PostgreSQL с поддержкой внешних ключей и каскадных операций

---

## 🛠️ Технологии

- **Kotlin**
- **Spring Boot**
    - Spring Web
    - Spring Data JPA
    - Spring Validation
- **PostgreSQL**
- **Flyway**
- **Swagger / OpenAPI**
- **JUnit 5** + **MockMvc**
- **TestContainers**
- **Docker**
- **Gradle Kotlin DSL**

---

## 📦 Структура проекта

```plaintext
src/
 └── main/
     ├── kotlin/org/example/servermanagementsystem/
     │   ├── controller/       # REST-контроллеры
     │   ├── service/          # Слой бизнес-логики
     │   ├── repository/       # Spring Data JPA репозитории
     │   ├── entity/           # JPA-сущности
     │   ├── dto/              # DTO-запросы/ответы
     │   └── mapper/           # Мапперы Entity <-> DTO
     └── resources/
         └── application.yml   # Настройки с поддержкой ENV переменных
```
---

## 🚀 Запуск проекта

1. Соберите проект с помощью Gradle:  
```bash
./gradlew clean build
```

2. Запустите необходимые сервисы через Docker:
```bash
docker-compose up -d
```

3. Запустите сгенерированный JAR-файл:
```bash
java -jar build/libs/ServerManagementSystem-0.0.1-SNAPSHOT.jar
```

## 📚 Документация API

После запуска сервиса документация OpenAPI (Swagger UI) будет доступна по адресу:

```plaintext
http://localhost:8080/swagger-ui.html