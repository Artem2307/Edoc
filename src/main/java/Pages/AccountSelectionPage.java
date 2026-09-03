package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class AccountSelectionPage {
    private static final SelenideElement OPEN_COLLECTION_ACCOUNTS_BUTTON = $x("//button/div[2]");
    private static final ElementsCollection ACCOUNTS_COLLECTION = $$x("//div[2]/p[2]");

   @Step("Переключитись на аккаунт {0}")
    public AccountSelectionPage selectAccount(String name){
       OPEN_COLLECTION_ACCOUNTS_BUTTON.should(Condition.visible, Duration.ofSeconds(10)).click();
       ACCOUNTS_COLLECTION.filter(Condition.text(name)).first().click();
       return this;
   }
}
