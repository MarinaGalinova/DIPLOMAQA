package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Cards;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private SelenideElement heading = $$("h3").find(text("Кредит по данным карты"));
    private static SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private static SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private static SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private static SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private static SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private static SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement notificationOK = $(".notification_status_ok");
    private SelenideElement notificationError = $(".notification_status_error");
    private SelenideElement inputInvalid = $(".input__sub");

    public CreditPage() {
        heading.shouldBe(visible);
    }

    public static void fillData(Cards card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        ownerField.setValue(card.getHolder());
        cvcField.setValue(card.getCvc());
        continueButton.click();
    }

    public void notificationOkIsVisible() {
        notificationOK.shouldBe(Condition.visible, Duration.ofSeconds(25));
    }

    public void notificationErrorIsVisible() {

        notificationError.shouldBe(Condition.visible, Duration.ofSeconds(25));
    }

    public boolean inputInvalidIsVisible() {
        return inputInvalid.isDisplayed();
    }

}
