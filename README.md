 ## Работа мечты
 В системе есть две модели:
 - вакансии,
 - кандидаты.

Можно публиковать как вакансии так и резюме кандидатов с описанием.

Общение с БД осуществляется по средстам JDBC использованием sql запросов.

### Стек технологий:

![java](https://img.shields.io/badge/java-16-red)![java](https://img.shields.io/badge/Jcip-1.0-red)![java](https://img.shields.io/badge/AssertJ-3.23.1-red)
![java](https://img.shields.io/badge/Log4j-1.2.17-red)![java](https://img.shields.io/badge/Slf4j-1.7.36-red)
![Spring boot](https://img.shields.io/badge/Spring%20boot-2.7.4-green)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-2.7.4-green)
![Thymeleaf](https://img.shields.io/badge/Mockito-4.0.0-green)
![Bootstrap](https://img.shields.io/badge/Bootstrap-4-ff69b4)
![Postgresql](https://img.shields.io/badge/Postgresql-42.5.0-blue)
![Hibernate](https://img.shields.io/badge/Checkstylee-9.0-orange)
![Liquibase](https://img.shields.io/badge/Liquibase-3.6.2-red)
![H2](https://img.shields.io/badge/H2-1.4.200-blue)![Maven](https://img.shields.io/badge/Maven-4.0.0-red)

### Требования к окружению :

- ![java](https://img.shields.io/badge/java-16+-red)
- ![Maven](https://img.shields.io/badge/Maven-4.0.0-red)
- ![Postgresql](https://img.shields.io/badge/Postgresql-13+-blue)

### Запуск приложения

Запуск с помощью командной строки:

1. Перейти в папку с проектом.
2. Выполнить команду: mvn liquibase:update
3. Выполнить команду: mvn clean install
4. Выполнить команду: mvn spring-boot:run
5. Перейти по ссылке: http://localhost:8080

### Окно входа
![главный вид](resources/log.png)

### Окно добавления вакансии
![главный вид](resources/addVacancy.png)

### Окно добавления кандидата
![главный вид](resources/regCandidate.png)

### Окно регистрации
![подробней задачи](resources/reg.png)

### Окно кандидатов
![подробней задачи](resources/candidates.png)

### Окно вакансий
![подробней задачи](resources/vacancy.png)

Контакты: @WhiteVax