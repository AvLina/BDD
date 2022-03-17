package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;


public class ReplenishmentPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final SelenideElement title = $("h1.heading");
    private final SelenideElement amountField = $("[data-test-id=amount] input");
    private final SelenideElement fromField = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]");
    private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");

    public ReplenishmentPage() {
        heading.shouldBe(visible);
        title.shouldHave(text("Пополнение карты"));
    }

    public DashboardPage transferMany(int transfersum, DataHelper.Card cardInfo) {
        amountField.setValue(String.valueOf(transfersum));
        fromField.setValue(cardInfo.getNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage transferError(int transfersum, DataHelper.Card cardInfo) {
        amountField.setValue(String.valueOf(transfersum));
        fromField.setValue(cardInfo.getNumber());
        transferButton.click();
        errorNotification.shouldBe(visible)
                .shouldHave(exactText("Превышен остаток по карте"));
        return new DashboardPage();
    }



}
