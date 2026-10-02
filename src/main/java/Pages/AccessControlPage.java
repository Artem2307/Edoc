package Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static Pages.RequiredAttributesPage.SAVE_BUTTON;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class AccessControlPage {

    private static final SelenideElement SEARCH_STRING = $x("//input[@placeholder=\"Пошук за ПІБ, кодом, компанією або email\"]");
    private static final ElementsCollection USERS = $$x("//div[@role=\"gridcell\"]//span");
    private static final ElementsCollection USER_SETTINGS = $$x("//span");

    @Step("Пошук користувача {0}")
    public AccessControlPage searchUser(String name){
        SEARCH_STRING.should(visible, Duration.ofSeconds(10)).clear();
        SEARCH_STRING.sendKeys(name);
        return this;
    }

    @Step("Відкрити користувача {0}")
    public AccessControlPage openUser(String name){
        USERS.filter(text(name)).first().click();
        return this;
    }

    @Step("Налаштувати користувача")
    public AccessControlPage settingUser(String... settings){
        for (String setting : settings) {
            USER_SETTINGS.filter(text(setting)).first().should(visible, Duration.ofSeconds(10)).click();
        }
        SAVE_BUTTON.click();
        return this;
    }
}
