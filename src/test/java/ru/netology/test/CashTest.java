package ru.netology.test;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Conditional;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import org.junit.jupiter.api.*;
import ru.netology.testUtils.TestSQLHelper;

import java.sql.SQLException;
import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CashTest {
//    @BeforeAll
//    static void setUpAll() throws SQLException {
//
//        TestSQLHelper.cleanTables();
//        SelenideLogger.addListener("allure", new AllureSelenide());
//    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    void cleanTables() throws SQLException {
        TestSQLHelper.cleanTables();
    }

    @Test
    @DisplayName("1.Positive. Valid card, status APPROVED")
    void ShouldBeCorrect() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("10").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Операция одобрена Банком."));
        assertEquals("APPROVED", TestSQLHelper.getOperationStatus(TestSQLHelper.getPayTable()));
    }
    @Test
    @DisplayName("2.Negative. Invalid card, status DECLINED")
    void ShouldntBeCorrect() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4442").click();//номер карты
        $("[placeholder='08']").setValue("10").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $$(".notification__content").get(1)
                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldHave(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
        assertEquals("DECLINED", TestSQLHelper.getOperationStatus(TestSQLHelper.getPayTable()));
    }
    @Test
    @DisplayName("3.Negative. Other card number, status DECLINED")
    void ShouldntBeCorrectAnoterCardNumber() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4443").click();//номер карты
        $("[placeholder='08']").setValue("10").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $$(".notification__content").get(1)
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    }
    @Test
    @DisplayName("4.Negative. Expired card (month), interlinear message visible")
    void ShouldntBeCorrectExpiredCardMonth() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("05").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }
    @Test
    @DisplayName("5.Negative. Expired card (year), interlinear message visible")
    void ShouldntBeCorrectExpiredCardYear() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("05").click();//месяц
        $("[placeholder='22']").setValue("22").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Истёк срок действия карты"));
    }
    @Test
    @DisplayName("6.Negative. Empty month field, interlinear message visible")
    void ShouldntBeCorrectEmptyMonth() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue(" ").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверный формат"));
    }
    @Test
    @DisplayName("7.Negative. One Digit month field, interlinear message visible")
    void ShouldntBeCorrectOneDigitMonth() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("1").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверный формат"));
    }
    @Test
    @DisplayName("8.Negative. Double Zero month field, interlinear message visible")
    void ShouldntBeCorrectDoubleZeroMonth() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("00").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }
    @Test
    @DisplayName("9.Negative. Over 12 month field, interlinear message visible")
    void ShouldntBeCorrectOver12Month() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("13").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }
    @Test
    @DisplayName("10.Negative. Empty year field, interlinear message visible")
    void ShouldntBeCorrectEmptyYear() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("12").click();//месяц
        $("[placeholder='22']").setValue(" ").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверный формат"));
    }
    @Test
    @DisplayName("11.Negative. One Digit year field, interlinear message visible")
    void ShouldntBeCorrectOneDigitYear() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("12").click();//месяц
        $("[placeholder='22']").setValue("2").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверный формат"));
    }
    @Test
    @DisplayName("12.Negative. Less than 16 digits card number, interlinear message visible")
    void ShouldntBeCorrectLess16DigitsCardNumber() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 444").click();//номер карты
        $("[placeholder='08']").setValue("12").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверный формат"));
    }
    @Test
    @DisplayName("13.Negative. Empty card number field, interlinear message visible")
    void ShouldntBeCorrectEmptyCardNumber() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue(" ").click();//номер карты
        $("[placeholder='08']").setValue("12").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверный формат"));
    }
    @Test
    @DisplayName("14.Negative. Empty cardholder field, interlinear message visible")
    void ShouldntBeCorrectEmptyCardholder() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("12").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue(" ");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
    @Test
    @DisplayName("15.Negative. Cyrillic cardholder field, interlinear message visible")
    void ShouldntBeCorrectCyrillicCardholder() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("12").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Иван Иванов");
        $("[placeholder='999']").setValue("123").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверный формат"));
    }
    @Test
    @DisplayName("16.Negative. CVC field, interlinear message visible")
    void ShouldntBeCorrectEmptyCVC() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("12").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue(" ").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $$(".input__sub").get(1)
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверный формат"));
    }
    @Test
    @DisplayName("17.Negative. Two digits CVC field, interlinear message visible")
    void ShouldntBeCorrect2DigitsCVC() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("12").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("22").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Неверный формат"));
    }
    @Test
    @DisplayName("18.Positive. Valid Card, Latin cardholder name")
    void ShouldBeCorrectLatinCardholder() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $("[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441").click();//номер карты
        $("[placeholder='08']").setValue("12").click();//месяц
        $("[placeholder='22']").setValue("23").click();//год
        $$(".input__control").get(3).setValue("Ivan Ivanov");
        $("[placeholder='999']").setValue("222").click();//год
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(25))
                .shouldHave(Condition.exactText("Операция одобрена Банком."));
    }
}



    //    Покупка туров. Позитивный сценарий (карта валидная, статус APPROVED)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание:
    //        получить уведомление от сервиса об успешной операции: «Успешно. Операция одобрена банком»
    //        получить от базы данных статус "APPROVED"

    //        Покупка туров. Негативный сценарий (карта невалидная, статус DECLINED)
    //        Нажать кнопку "Купить"
    //        Ввести данные невалидной карты
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание:
    //
    //        получить уведомление от сервиса об отказе в операции «Ошибка! Банк отказал в проведении операции»
    //        получить от базы данных статус "DECLINED"

    //        Покупка туров. Негативный сценарий (карта с правильными данными, но с номером, отличным от валидной/ невалидной карты)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме номера)
    //        В поле "Номер карты" ввести: 4444 4444 4444 4443
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса об отказе в операции «Ошибка! Банк отказал в проведении операции»
    //
    //        Покупка туров. Негативный сценарий (карта валидная, срок действия истек (месяцы))
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме месяца действия)
    //        В поле "Месяц" ввести любой прошедший месяц в текущем году
    //        В поле "Год" ввести текущий год
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса: «Неверно указан срок действия карты»
    //
    //        Покупка туров. Негативный сценарий (карта валидная, срок действия истек (год))
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме года действия)
    //        В поле "Год" ввести любой прошедший год
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса: «Истек срок действия карты»
    //
    //        Покупка туров. Негативный сценарий (карта валидная, поле "Месяц" не заполнено)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме поля месяц действия)
    //        Поле "Месяц" оставить пустым
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса: «Неверный формат»
    //
    //        Покупка туров. Негативный сценарий (карта валидная, поле "Месяц" заполнено одной цифрой)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме поля месяц действия)
    //        В поле "Месяц" ввести 1
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса: «Неверный формат»
    //
    //        Покупка туров. Негативный сценарий (карта валидная, поле "Месяц" заполнено двумя нулями 00)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме поля месяц действия)
    //        В поле "Месяц" ввести 00
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //       !!!! Ожидание: получить уведомление от сервиса: «Неверный формат». Правильно неверно указан срок действия карты
    //
    //        Покупка туров. Негативный сценарий (карта валидная, поле "Месяц" заполнено двузначным числом, больше 12)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме поля месяц действия)
    //        В поле "Месяц" ввести 13
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        !!!!Ожидание: получить уведомление от сервиса: «Неверный формат» равильно неверно указан срок действия карты
    //
    //        Покупка туров. Негативный сценарий (карта валидная, поле "Год" не заполнено)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме года действия)
    //        Поле "Год" оставить пустым
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса: «Неверный формат»
    //
    //        Покупка туров. Негативный сценарий (карта валидная, поле "Год" заполнено одной цифрой)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме года действия)
    //        В поле "Год" ввести 1
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса: «Неверный формат»
    //
    //        Покупка туров. Негативный сценарий (карта с правильными данными, но с номером менее 16 знаков)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме номера)
    //        В поле "Номер карты" ввести: 4444 4444 4444 444
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса: «Неверный формат»
    //
    //        Покупка туров. Негативный сценарий (карта с правильными данными, но поле "номер" пустое)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме номера)
    //        Поле "Номер карты" оставить пустым
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
        //        Ожидание: получить уведомление от сервиса: «Неверный формат»
    //
    //        Покупка туров. Негативный сценарий (карта валидная, не заполнено поле с именем владельца)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме имени владельца)
    //        Поле "Владелец" оставить пустым
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса: «Поле обязательно для заполнения»
        //
    //        Покупка туров. Негативный сценарий (карта валидная, не поле "Владелец" заполено на кириллице)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме имени владельца)
    //        В поле "Владелец" ввести "Василий"
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса: «Неверный формат»
    //
    //        Покупка туров. Негативный сценарий (карта валидная, не заполнено поле с CVC/CVV)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме CVC/CVV)
    //        Поле " CVC/CVV " оставить пустым
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса: «Неверный формат»
    //
    //        Покупка туров. Негативный сценарий (карта валидная, в поле с CVC/CVV двузначное число)
    //        Нажать кнопку "Купить"
    //        Ввести данные валидной карты (кроме CVC/CVV)
    //        В поле " CVC/CVV " ввести 42
    //        Подтвердить ввод (нажать кнопку «Продолжить»)
    //        Ожидание: получить уведомление от сервиса: «Неверный формат»

//        Покупка туров. Позитивный сценарий (карта валидная, не поле "Владелец" заполено на латинице имя-фамилия)
//        Нажать кнопку "Купить"
//        Ввести данные валидной карты (кроме имени владельца)
//        В поле "Владелец" ввести "Ivan Ivanov"
//        Подтвердить ввод (нажать кнопку «Продолжить»)
//         Ожидание:
//        получить уведомление от сервиса об успешной операции: «Успешно. Операция одобрена банком»
//        получить от базы данных статус "APPROVED"
