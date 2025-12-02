# Task Tracker API

REST API для управления проектами и задачами.

## Технологии

**Backend:** Java 21, Spring Boot 3, Spring Security, JPA/Hibernate  
**База данных:** PostgreSQL  
**Безопасность:** JWT + Cookies, BCrypt  
**Утилиты:** Lombok, MapStruct

## Аутентификация

- **Access Token** - 15 минут (в httpOnly cookie)
- **Refresh Token** - 30 дней (в httpOnly cookie)
- **Роли:** USER, ADMIN

## Основные возможности

- Регистрация/авторизация пользователей
- CRUD для проектов, этапов и задач

## Запуск
```bash
# Запуск PostgreSQL
docker compose up -d

# Сборка и запуск приложения
./gradlew bootRun

# Для Windows используйте:
# gradlew.bat bootRun
```
