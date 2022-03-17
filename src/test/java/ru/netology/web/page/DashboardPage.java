package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement topUpCard1Button = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button");
    private SelenideElement topUpCard2Button = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    private int extractBalance(String cardInfo) {
        var value = cardInfo.substring
                        (cardInfo.indexOf(balanceStart) + balanceStart.length(),
                                cardInfo.indexOf(balanceFinish))
                .trim();
        return Integer.parseInt(value);
    }

    public ReplenishmentPage transferMonyFromCard1() {
        topUpCard1Button.click();
        return new ReplenishmentPage();
    }

    public ReplenishmentPage transferMonyFromCard2() {
        topUpCard2Button.click();
        return new ReplenishmentPage();
    }

    public int getFirstCardBalance() {
        var text = cards.first().text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        var text = cards.last().text();
        return extractBalance(text);
    }

    public int getCardBalance(DataHelper.CardId cardId) {
        return extractBalance($("[data-test-id='" + cardId.getId() + "']").getText());
    }

    public ReplenishmentPage transfer(DataHelper.CardId cardId) {
        $("[data-test-id='" + cardId.getId() + "'] [data-test-id=action-deposit]").click();
        return new ReplenishmentPage();
    }

}
