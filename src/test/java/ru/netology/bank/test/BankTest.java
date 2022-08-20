package ru.netology.bank.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.bank.data.DataHelper;
import ru.netology.bank.pages.*;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BankTest {

    @BeforeEach
    public void setup(){
    //Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void souldTopUpCard1(){
        int amaunt = 200;
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.login(authInfo);
        var virificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.verify(virificationCode);
        var cabinetPage = new CabinetPage();
        var balance1 = cabinetPage.getCardBalance("92df3f1c-a033-48e6-8390-206f6b1f56c0");
        var topUpCard = cabinetPage.refilCard1().topUpCard(String.valueOf(amaunt), "5559 0000 0000 0002");
        var balanceE = balance1 + amaunt;
        $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'").shouldHave(Condition.text(String.valueOf(balanceE)));
    }
    @Test
    void souldTopUpCard2(){
        int amaunt = 100;
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.login(authInfo);
        var virificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.verify(virificationCode);
        var cabinetPage = new CabinetPage();
        var balance1 = cabinetPage.getCardBalance("0f3f5c2a-249e-4c3d-8287-09f7a039391d");
        var topUpCard = cabinetPage.refilCard2().topUpCard(String.valueOf(amaunt), "5559 0000 0000 0001");
        var balanceE = balance1 + amaunt;
        $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'").shouldHave(Condition.text(String.valueOf(balanceE)));
    }
    @Test
    void souldTranferMoreThenHaveCard2(){
        int amaunt = 1000;
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.login(authInfo);
        var virificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.verify(virificationCode);
        var cabinetPage = new CabinetPage();
        var balance = cabinetPage.getCardBalance("0f3f5c2a-249e-4c3d-8287-09f7a039391d");
        var topUpCard = cabinetPage.refilCard1().topUpCard(String.valueOf(balance + amaunt), "5559 0000 0000 0002");
        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
    }
}
