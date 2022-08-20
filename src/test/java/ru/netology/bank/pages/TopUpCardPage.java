package ru.netology.bank.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class TopUpCardPage {

    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement to = $("[data-test-id='to'] input");
    private SelenideElement topUpButton = $("[data-test-id='action-transfer']");
    private SelenideElement abortButton = $("[data-test-id='action-cancel']");


    public TopUpCardPage(String cardNumber) {
        to.shouldHave(Condition.value(cardNumber));
    }

    public CabinetPage topUpCard(String amValue, String fromCard){
        amount.setValue(amValue);
        from.setValue(fromCard);
        topUpButton.click();
        return new CabinetPage();
    }

    public CabinetPage cancelTopUp(){
        abortButton.click();
        return new CabinetPage();
    }
}
