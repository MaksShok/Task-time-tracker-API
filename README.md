# Task Time Tracker API

Сервис учёта рабочего времени сотрудников на задачи. Реализован как RESTful backend на Spring Boot.

## 📌 Функционал

Реализованы следующие возможности:

- ✅ Создание задачи (`Task`)
- ✅ Получение задачи по ID
- ✅ Изменение статуса задачи (`NEW` → `IN_PROGRESS` → `DONE`)
- ✅ Создание записи о затраченном времени (`TimeRecord`) сотрудником на задачу
- ✅ Получение всех записей о времени, затраченном конкретным сотрудником за указанный период

### Сущности

- **Task**:  
  `id`, `name`, `description`, `status` (один из: `NEW`, `IN_PROGRESS`, `DONE`)
  
- **TimeRecord**:  
  `id`, `employeeId`, `taskId`, `startDateTime`, `endDateTime`, `description`

## 🛠 Технологии

- Java 17
- Spring Boot 3.5.13
- MyBatis
- Maven
- H2 (встроенная БД)
- JUnit 5 + Mockito (unit-тесты)
- SpringDoc OpenAPI (Swagger)
- Валидация входных данных через Bean Validation в контроллерах
- Глобальная обработка ошибок через `@RestControllerAdvice`

> 💡 **Дополнительно**: проект разрабатывался с использованием ИИ-ассистента (Cursor, Qwen Companion for VsCode, Qwen Max)

---

## ▶️ Как запустить

### Требования
- JDK 17+
- Maven 3.6+

### Шаги

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/MaksShok/Task-time-tracker-API.git
   cd task-time-tracker-api
2. В корне папки main.java.ru.code.tasktracker найдите класс *TaskTrackerApiApplication* - это точка входа в приложение.
Запустите его.

### Как протестировать HTTP - запросами ->
В main.resources находится папка PostmanRes
<img width="671" height="229" alt="image" src="https://github.com/user-attachments/assets/b0b46c16-9fd3-42dc-8fc3-eb4456a69111" />
Внутри нее вы можете увидеть 2 JSON файла:
- *Task time tracker API.postman_collection* - Postman Коллекция запросов
- *TaskTrackerEnv.postman_environment* - Postman окружение с переменной base_url относительного пути

Дальнейшие шаги в Postman:
1. Импортируйте файл окружения:  
   `TaskTrackerEnv.postman_environment`
2. Импортируйте файл коллекции запросов
   `Task time tracker API.postman_collection`
4. Убедитесь, что выбрано окружение **«TaskTrackerEnv»** (в выпадающем списке справа вверху).
5. При необходимости отредактируйте переменную `base_url` (например, если ваш сервер запущен на другом порту).

### Unit-тестирование и Mockito
Все Unit-тесты располагаются в папке *test*. Для ясности, файловая структура внутри папки test идентична структуре основного проекта внутри *main*.
Тесты проверяют как корректные тестовые данные, так и возможные некорректные случаи.
