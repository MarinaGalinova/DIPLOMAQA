import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CashTest {
//    private WebDriver driver;
//
//    //поле под вебдрайвер с переменной driver, которую мы будем использовать для обращения к драйверу
//    @BeforeAll //устанавливаем драйвер хрома, прописываем путь к нему в этом проекте
//    static void SetUpAll() {
//        System.setProperty("web-driver.chrome.driver", "driver/chromedriver.exe");
//    }
//
//    @BeforeEach
//    void setUpChrome() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
//        driver = new ChromeDriver(options);
//    }
     //перед каждым тестом открываем новую версию хрома, чтобы не переходили какие-то ранее запущенные действия


    @Test
    @DisplayName("1.Positive. Valid card, status APPROVED")
    void ShouldBeCorrect() {
        //driver.get("http://localhost:8080/");
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText("Купить")).parent().parent().click();
        $(byText("Номер карты")).parent().sendKeys("4444 4444 4444 4441");//ввести номер валидной карты
        $(byText("Месяц")).parent().sendKeys("12");//ввести месяц
        $(byText("Год")).parent().sendKeys("23");//ввести год
        $(byText("Владелец")).parent().sendKeys("Марина");//ввести имя кардхолдера
        $(byText("CVC/CVV")).parent().sendKeys("111");//ввести cvc
        $(byText("Продолжить")).parent().parent().click();//нажать продолжить
        String expected = "Операция одобрена банком";
        String actual = $(By.cssSelector("[notification__content]")).getText(); //определить актуальное сообщение
        assertEquals(expected, actual);

    }

}

//
//    @Test
//    void shouldBook() {
//        Configuration.holdBrowserOpen = true;
//        open("http://localhost:9999/");
//        $("[data-test-id='city'] input").setValue("Москва");
//        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        $("[data-test-id='date'] input").setValue(bookDate);
//        $("[data-test-id='name'] input").setValue("Тигр Евфратыч");
//        $("[data-test-id='phone'] input").setValue("+79128800000");
//        $("[data-test-id='agreement']").click();
//        $("button.button").click();
//        $(".notification__content")
//                .shouldBe(Condition.visible, Duration.ofSeconds(25))
//                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + bookDate));
//    }
//    @Test
//    @DisplayName("Should successfully login with active registered user")
//    void shouldSuccessfulLoginIfRegisteredActiveUser() {
//        var registeredUser = getRegisteredUser("active");
//        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
//        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
//        $("button.button").click();
//        $("h2")
//                .shouldHave(Condition.exactText("  Личный кабинет"))
//                .shouldBe(Condition.visible);
//        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
//        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
//        //  пользователя registeredUser
//    }
//
//    @Test
//    @DisplayName("Should get error message if login with not registered user")
//    void shouldGetErrorIfNotRegisteredUser() {
//        var notRegisteredUser = getUser("active");
//        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
//        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
//        $("button.button").click();
//        $("[data-test-id= 'error-notification'] .notification__content")
//                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
//                .shouldBe(Condition.visible);
//
//        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
//        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
//    }
//
//    @Test
//    @DisplayName("Should get error message if login with blocked registered user")
//    void shouldGetErrorIfBlockedUser() {
//        var blockedUser = getRegisteredUser("blocked");
//
//        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
//        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
//        $("button.button").click();
//        $("[data-test-id= 'error-notification'] .notification__content")
//                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
//                .shouldBe(Condition.visible);
//    }
//
//    @Test
//    @DisplayName("Should get error message if login with wrong login")
//    void shouldGetErrorIfWrongLogin() {
//        var registeredUser = getRegisteredUser("active");
//        var wrongLogin = getRandomLogin();
//        $("[data-test-id='login'] input").setValue(wrongLogin);
//        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
//        $("button.button").click();
//        $("[data-test-id= 'error-notification'] .notification__content")
//                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
//                .shouldBe(Condition.visible);
//    }
//
//    @Test
//    @DisplayName("Should get error message if login with wrong password")
//    void shouldGetErrorIfWrongPassword() {
//        var registeredUser = getRegisteredUser("active");
//        var wrongPassword = getRandomPassword();
//        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
//        $("[data-test-id='password'] input").setValue(wrongPassword);
//        $("button.button").click();
//        $("[data-test-id= 'error-notification'] .notification__content")
//                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
//                .shouldBe(Condition.visible);
//    }
//}



//    Покупка туров. Позитивный сценарий (карта валидная, статус APPROVED)
//        Нажать кнопку "Купить"
//        Ввести данные валидной карты
//        Подтвердить ввод (нажать кнопку «Продолжить»)
//        Ожидание:
//
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
//        Ожидание: получить уведомление от сервиса: «Неверный формат»
//
//        Покупка туров. Негативный сценарий (карта валидная, поле "Месяц" заполнено двузначным числом, больше 12)
//        Нажать кнопку "Купить"
//        Ввести данные валидной карты (кроме поля месяц действия)
//        В поле "Месяц" ввести 13
//        Подтвердить ввод (нажать кнопку «Продолжить»)
//        Ожидание: получить уведомление от сервиса: «Неверный формат»
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


