package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.ReplenishmentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @Test
    void shouldTransferMoneyOnFirstCard() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();
        var balanceFirstCardBeforeTransfer = dashboardPage.getFirstCardBalance();
        var balanceSecondCardBeforeTransfer = dashboardPage.getSecondCardBalance();
        dashboardPage.transferMonyFromCard1();
        int transfersum = 3000;
        var replenishmentPage = new ReplenishmentPage();
        replenishmentPage.transferMany(transfersum, DataHelper.getCardSecond());
        var dashboardPage2 = new DashboardPage();
        var balanceFirstCardAfterTransfer = dashboardPage2.getFirstCardBalance();
        var balanceSecondCardAfterTransfer = dashboardPage2.getSecondCardBalance();
        assertEquals((balanceFirstCardBeforeTransfer + transfersum), balanceFirstCardAfterTransfer);
        assertEquals((balanceSecondCardBeforeTransfer - transfersum), balanceSecondCardAfterTransfer);
    }

    @Test
    void shouldNotTransferMoneyOnFirstCard() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();
        var balanceFirstCardBeforeTransfer = dashboardPage.getFirstCardBalance();
        var balanceSecondCardBeforeTransfer = dashboardPage.getSecondCardBalance();
        dashboardPage.transferMonyFromCard1();
        int transfersum = 20000;
        var replenishmentPage = new ReplenishmentPage();
        replenishmentPage.transferError(transfersum, DataHelper.getCardSecond());
        var dashboardPage2 = new DashboardPage();
        var balanceFirstCardAfterTransfer = dashboardPage2.getFirstCardBalance();
        var balanceSecondCardAfterTransfer = dashboardPage2.getSecondCardBalance();
        assertEquals((balanceFirstCardBeforeTransfer), balanceFirstCardAfterTransfer);
        assertEquals((balanceSecondCardBeforeTransfer), balanceSecondCardAfterTransfer);
    }


}
