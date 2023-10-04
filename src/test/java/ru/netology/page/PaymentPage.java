package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Cards;
import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement heading = $(byText("Купить")).parent().parent();
    private SelenideElement cardNumberField =  $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField =  $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement ownerField = $$(".input__control").get(3);
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement notificationOK = $(".notification_status_ok");
    private SelenideElement notificationError = $(".notification_status_error");
    private SelenideElement inputInvalid = $(".input__sub");

    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public void fillData(Cards card) {
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
