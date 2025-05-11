# Subscribers

Техническое задание: Реализация микросервиса

#### Описание задачи
Необходимо разработать микросервис на Spring Boot 3, который будет
предоставлять REST API для управления пользователями и их подписками на
сервисы.

#### Функциональные требования
  1. Проект должен использовать Java 17.
  2. Postgres
  3. Dockerfile, docker-compose.yml - для локального запуска проекта

#### Требования к API
   Примерные эндпоинты:

      - POST /users - создать пользователя
      - GET /users/{id} - получить информацию о пользователе
      - PUT /users/{id} - обновить пользователя
      - DELETE /users/{id} - удалить пользователя
      - POST /users/{id}/subscriptions - добавить подписку
      - GET /users/{id}/subscriptions - получить подписки пользователя
      - DELETE /users/{id}/subscriptions/{sub_id} - удалить подписку
      - GET /subscriptions/top - получить ТОП-3 популярных подписок

#### Сборка и запуск
  1. `mvn clean package`
  2. `docker build . --tag user-management-service:0.7`
  3. `docker compose up -d`
  4. Приложение доступно на `http://localhost:8080`
