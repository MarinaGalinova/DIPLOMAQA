# Дипломный проект по курсу «Тестировщик ПО»

### Тестирование сервиса покупки тура

## Документация
- [План автоматизации тестирования](https://github.com/MarinaGalinova/DIPLOMAQA/blob/master/Documents/Plan.md)
- [Отчет по итогам тестирования](https://github.com/MarinaGalinova/DIPLOMAQA/blob/master/Documents/Report.md)
- [Отчет по итогам автоматизации](https://github.com/MarinaGalinova/DIPLOMAQA/blob/master/Documents/Summary.md)

## Перед началом работы
*Необходимо, чтобы были уставновлены:*
- IntelliJ IDEA
- Docker desktop / docker toolbox
1. Используя команду `git clone https://github.com/MarinaGalinova/DIPLOMAQA` скачать себе данный репозиторий
2. Перейти в каталог со скачанным содержимым репозитория и скачать докер-контейнеры
* ```cd ./DIPLOMAQA/```

3. **Запускаем docker-контейнер с MySQL , PostgreSQL и Node.js.**
- ввести в терминале(консоли) команду `docker-compose up`

3.**Для запуска SUT открываем новую вкладку в Терминале IDEA и вводим следующую команду:**
- для mysql
java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
- для postgresql
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

 
 4.**Для запуска авто-тестов необходимо открыть новую вкладку в терминале и ввести команду:**
 - для **MySQL**: `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`
 - для **PostgreSQL**: `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`
 
 5.**Для создания отчета Allure ввести в терминале команду:**
 `gradlew allureServe`

6. Окончание тестов и остановка контейнеров

* Прервать выполнение SUT по Ctrl+C (или закрытием окна терминала)
* Остановить контейнеры командой ```docker-compose down```