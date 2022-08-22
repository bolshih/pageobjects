package ru.netology.bank.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TopUpCardPage {

    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement to = $("[data-test-id='to'] input");
    private SelenideElement topUpButton = $("[data-test-id='action-transfer']");
    private SelenideElement abortButton = $("[data-test-id='action-cancel']");
    private SelenideElement topUpCard = $x("//*[contains(text(), 'Пополнение карты')]");
    private SelenideElement error = $("[data-test-id='error-notification']");

    public TopUpCardPage() {
        topUpCard.shouldBe(Condition.visible);
    }

    public CabinetPage topUpCard(String amValue, String fromCard) {
        amount.setValue(amValue);
        from.setValue(fromCard);
        topUpButton.click();
        return new CabinetPage();
    }

    public CabinetPage cancelTopUp() {
        abortButton.click();
        return new CabinetPage();
    }

    public void error() {
        error.shouldBe(Condition.visible);
    }
}
