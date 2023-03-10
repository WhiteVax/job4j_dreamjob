 ## Работа мечты
 В системе есть две модели:
 - вакансии,
 - кандидаты.

Можно публиковать как вакансии так и резюме кандидатов с описанием.

Общение с БД осуществляется по средстам JDBC использованием sql запросов.

### Стек технологий:

![java](https://img.shields.io/badge/java-16-red)![java](https://img.shields.io/badge/Jcip-1.0-red)
![java](https://img.shields.io/badge/Log4j-1.2.17-red)![java](https://img.shields.io/badge/Slf4j-1.7.36-red)
![Spring boot](https://img.shields.io/badge/Spring%20boot-2.7.4-green)
![Spring-boot-starter-test](https://img.shields.io/badge/Spring%20starter%20test-2.7.4-green)
![Spring-boot-starter-thymeleaf](https://img.shields.io/badge/Spring%20starter%20thymeleaf-2.7.4-green)
![Spring-boot-starter-web](https://img.shields.io/badge/Spring%20starter%20web-2.7.4-green)
![Spring-boot-maven-plugin](https://img.shields.io/badge/Spring%20boot%20maven-2.7.4-green)
![Mockito](https://img.shields.io/badge/Mockito-4.0.0-green)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-ff69b4)
![Postgresql](https://img.shields.io/badge/Postgresql-13-blue)
![Sql2o](https://img.shields.io/badge/Sql2o-1.6.0-blue)
![Checkstylee](https://img.shields.io/badge/Checkstylee-9.0-orange)
![Liquibase](https://img.shields.io/badge/Liquibase-4.15.0-red)
![H2](https://img.shields.io/badge/H2-2.1.214-blue)![Maven](https://img.shields.io/badge/Maven-4.0.0-red)

### Требования к окружению :

- ![java](https://img.shields.io/badge/java-16+-red)
- ![Maven](https://img.shields.io/badge/Maven-4.0.0-red)
- ![Postgresql](https://img.shields.io/badge/Postgresql-13+-blue)

### Запуск приложения

Запуск с помощью командной строки:

0. Создать БД:
```
CREATE DATABASE dreamjob;
```
1. Перейти в папку с проектом.
2. Выполнить команду: mvn liquibase:update -Pproduction
3. Выполнить команду: mvn clean install
4. Выполнить команду: mvn spring-boot:run
5. Перейти по ссылке: http://localhost:8080

### Окно входа
![главный вид](imgs/log.png)

### Окно добавления вакансии
![Окно добавления вакансии](imgs/addVacancy.png)

### Окно добавления кандидата
![Окно добавления кандидата](imgs/addCandidate.png)

### Окно регистрации
![Окно регистрации](imgs/reg.png)

### Окно регистрации повторной почты
![Окно регистрации повторной почты](imgs/error.png)

### Окно кандидатов
![Окно кандидатов](imgs/candidates.png)

### Окно вакансий
![Окно вакансий](imgs/vacancies.png)

### Окно редактирования кандидата
![Окно редактирования вакансии](imgs/editCandidate.png)

### Окно редактирования вакансии
![Окно редактирования вакансии](imgs/editVacancy.png)

### Контакты: @WhiteVax