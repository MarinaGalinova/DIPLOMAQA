package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import org.junit.jupiter.api.*;
import ru.netology.data.Cards;
import ru.netology.data.DataGenerator;
import ru.netology.data.TestSQLHelper;
import ru.netology.page.PaymentPage;
import ru.netology.page.StartPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CashTest {

    Cards declinedCard = DataGenerator.getDeclinedCard();
    Cards validCard = DataGenerator.getValidCard();
    Cards fakeCard = DataGenerator.getFakeCard();


    @BeforeEach
    public void openPage() throws SQLException {
        TestSQLHelper.cleanTables();
        open(System.getProperty("test.host"));
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("1.Positive API. Valid card, status APPROVED")
    void ShouldBeCorrect() {
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(validCard);
        paymentPage.notificationOkIsVisible();
        assertEquals("APPROVED", TestSQLHelper.getOperationStatus("payment_entity"));
    }

    @Test
    @DisplayName("2.Negative API. Invalid card, status DECLINED")
    void ShouldntBeCorrect() {
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(declinedCard);
        paymentPage.notificationErrorIsVisible();
//        paymentPage.notificationOkIsVisible();
        assertEquals("DECLINED", TestSQLHelper.getOperationStatus("payment_entity"));
    }

    @Test
    @DisplayName("3.Negative API. Other card number, empty status")
    void ShouldntBeCorrectOtherCardNumber() {
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(fakeCard);
        assertEquals("", TestSQLHelper.getOperationStatus("payment_entity"));
    }

    @Test
    @DisplayName("4.Negative UI. Expired card (month), interlinear message visible")
    void ShouldntBeCorrectExpiredCardMonth() {
        Cards expiredCard = DataGenerator.getInvalidExpDateCard(-1);
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение об ошибке, если срок карты истек, страница оплаты");
    }

    @Test
    @DisplayName("5.Negative UI. Expired card (year), interlinear message visible")
    void ShouldntBeCorrectExpiredCardYear() {
        Cards expiredCard = DataGenerator.getInvalidExpDateCardYear(-1);
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение об ошибке, если срок карты истек, страница оплаты");
    }

    @Test
    @DisplayName("6.Negative UI. Empty month field, interlinear message visible")
    void ShouldntBeCorrectEmptyMonth() {
        Cards expiredCard = DataGenerator.getInvalidEmptyMonthField();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение о том, что поле не может быть пустым");
    }

    @Test
    @DisplayName("7.Negative UI. One Digit month field, interlinear message visible")
    void ShouldntBeCorrectOneDigitMonth() {
        Cards expiredCard = DataGenerator.getInvalidOneDigitMonthField();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение о том, что формат неверный");
    }

    @Test
    @DisplayName("8.Negative UI. Double Zero month field, interlinear message visible")
    void ShouldntBeCorrectDoubleZeroMonth() {
        Cards expiredCard = DataGenerator.getInvalidDoubleZeroMonthField();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение о том, что срок действия карты неверный");
    }

    @Test
    @DisplayName("9.Negative UI. Over 12 month field, interlinear message visible")
    void ShouldntBeCorrectOver12Month() {
        Cards expiredCard = DataGenerator.getInvalidDateCardMonthOver12();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение о том, что срок действия карты неверный");
    }

    @Test
    @DisplayName("10.Negative UI. Empty year field, interlinear message visible")
    void ShouldntBeCorrectEmptyYear() {
        Cards expiredCard = DataGenerator.getInvalidEmptyYearField();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение о том, что поле не может быть пустым");
    }

    @Test
    @DisplayName("11.Negative UI. One Digit year field, interlinear message visible")
    void ShouldntBeCorrectOneDigitYear() {
        Cards expiredCard = DataGenerator.getInvalidOneDigitYearField();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение о том, что формат неверный");
    }

    @Test
    @DisplayName("12.Negative UI. Less than 16 digits card number, interlinear message visible")
    void ShouldntBeCorrectLess16DigitsCardNumber() {
        Cards expiredCard = DataGenerator.getInvalidNumberLess16Digits();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение об ошибке Неверный формат");
    }

    @Test
    @DisplayName("13.Negative UI. Empty card number field, interlinear message visible")
    void ShouldntBeCorrectEmptyCardNumber() {
        Cards expiredCard = DataGenerator.getInvalidEmptyNumber();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение об ошибке Поле не может быть пустым");
    }

    @Test
    @DisplayName("14.Negative UI. Empty cardholder field, interlinear message visible")
    void ShouldntBeCorrectEmptyCardholder() {
        Cards expiredCard = DataGenerator.getInvalidEmptyCardholderField();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение об ошибке Поле не может быть пустым");
    }

    @Test
    @DisplayName("15.Negative UI. Cyrillic cardholder field, interlinear message visible")
    void ShouldntBeCorrectCyrillicCardholder() {
        Cards expiredCard = DataGenerator.getInvalidCyrillicCardholderField();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение об ошибке Поле должно быть заполнено на латинице");
    }

    @Test
    @DisplayName("16.Negative UI. Empty CVC field, interlinear message visible")
    void ShouldntBeCorrectEmptyCVC() {
        Cards expiredCard = DataGenerator.getInvalidEmptyCVCField();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение об ошибке Поле не может быть пустым");
    }

    @Test
    @DisplayName("17.Negative UI. Two digits CVC field, interlinear message visible")
    void ShouldntBeCorrect2DigitsCVC() {
        Cards expiredCard = DataGenerator.getInvalidTwoDigitsCVCField();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), "Должен показывать сообщение об ошибке Неверный формат");
    }

}
