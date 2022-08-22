package ru.netology.bank.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CabinetPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection topUpCardButton = $$("[data-test-id='action-deposit']");
    private SelenideElement reloadButton = $("[data-test-id='action-reload'");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public CabinetPage() {

        heading.shouldBe(visible);
    }

    public CabinetPage reload() {
        reloadButton.click();
        return new CabinetPage();
    }

    public TopUpCardPage refilCard1() {
        topUpCardButton.first().click();
        return new TopUpCardPage();
    }

    public TopUpCardPage refilCard2() {
        topUpCardButton.last().click();
        return new TopUpCardPage();
    }

    public int getCardBalance(String id) {
        String selector = "[data-test-id='" + id + "']";
        var text = $(selector).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
