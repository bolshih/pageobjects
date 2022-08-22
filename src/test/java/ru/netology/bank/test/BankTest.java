package ru.netology.bank.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.bank.data.DataHelper;
import ru.netology.bank.pages.*;

import static com.codeborne.selenide.Selenide.open;

public class BankTest {

    @BeforeEach
    public void setup() {
        // Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void souldTopUpCard1() {
        int amount = 200;
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.login(authInfo);
        var virificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.verify(virificationCode);
        var cabinetPage = new CabinetPage();
        var balance1 = cabinetPage.getCardBalance(DataHelper.getCard1Info().getCardId()); //баланс карты зачисления
        var balance2 = cabinetPage.getCardBalance(DataHelper.getCard2Info().getCardId()); //баланс карты саписания
        var topUpCard = cabinetPage.refilCard1().topUpCard(String.valueOf(amount), DataHelper.getCard2Info().getCardNumber());
        var balanceActu1 = cabinetPage.getCardBalance(DataHelper.getCard1Info().getCardId()); //баланс карты зачисления после операции
        var balanceActu2 = cabinetPage.getCardBalance(DataHelper.getCard2Info().getCardId()); //баланс карты саписания после операции
        var balanceExp1 = balance1 + amount; //ожидаемый баланс карты зачисления после операции
        var balanceExp2 = balance2 - amount; //ожидаемый баланс карты списания после операции
        Assertions.assertEquals(balanceExp1, balanceActu1);
        Assertions.assertEquals(balanceExp2, balanceActu2);
    }

    @Test
    void souldTopUpCard2() {
        int amount = 100;
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.login(authInfo);
        var virificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.verify(virificationCode);
        var cabinetPage = new CabinetPage();
        var balance2 = cabinetPage.getCardBalance(DataHelper.getCard2Info().getCardId()); //баланс карты зачисления
        var balance1 = cabinetPage.getCardBalance(DataHelper.getCard1Info().getCardId()); //баланс карты саписания
        var topUpCard = cabinetPage.refilCard2().topUpCard(String.valueOf(amount), DataHelper.getCard1Info().getCardNumber());
        var balanceActu2 = cabinetPage.getCardBalance(DataHelper.getCard2Info().getCardId()); //баланс карты зачисления после операции
        var balanceActu1 = cabinetPage.getCardBalance(DataHelper.getCard1Info().getCardId()); //баланс карты саписания после операции
        var balanceExp2 = balance2 + amount; //ожидаемый баланс карты зачисления после операции
        var balanceExp1 = balance1 - amount; //ожидаемый баланс карты списания после операции
        Assertions.assertEquals(balanceExp1, balanceActu1);
        Assertions.assertEquals(balanceExp2, balanceActu2);
    }

    @Test
    void souldTranferMoreThenHaveCard2() {
        int amount = 1000;
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.login(authInfo);
        var virificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.verify(virificationCode);
        var cabinetPage = new CabinetPage();
        var balance = cabinetPage.getCardBalance(DataHelper.getCard2Info().getCardId()); //получаем баланс карты списания
        //вводим сумму превышающую баланс карты списания поскольку сумма переменных balance и amount
        //превышает баланс карты на сумму заданную в amount
        var topUpCard = cabinetPage.refilCard1().topUpCard(String.valueOf(balance + amount), DataHelper.getCard2Info().getCardNumber());
        new TopUpCardPage().error();
    }
}
