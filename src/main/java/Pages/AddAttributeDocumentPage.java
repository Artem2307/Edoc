package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class AddAttributeDocumentPage {
    private static final SelenideElement ADD_ATTRIBUTE_BUTTON = $x("//div[2]/button");
    private static final SelenideElement NAME_ATTRIBUTE = $x("//input[@class='U5jLn3WZrO7Mymsndgxe']");
    private static final SelenideElement TYPE_ATTRIBUTE = $x("//div//button[@class='U5jLn3WZrO7Mymsndgxe']");
    private static final SelenideElement SAVE_BUTTON = $x("//button[text()='Зберегти']");
    private static final ElementsCollection NAME_TYPE_ATTRIBUTE = $$x("//li//button[@type='button']");
    private static final ElementsCollection ATTRIBUTE_NAMES = $$x("//div/div/div/div[1]/span");
    private static final ElementsCollection DELETE_BUTTONS = $$x("//div/div/div/button");
    private static final SelenideElement DELETE_BUTTONS_POP_UP = $x("//button[text()='Видалити']");

    @Step("Додати атрубут {type} + {attribute}")
    public AddAttributeDocumentPage addAttribute(String name,String attribute){
        ADD_ATTRIBUTE_BUTTON.should(Condition.visible, Duration.ofSeconds(10)).click();
        NAME_ATTRIBUTE.should(Condition.visible, Duration.ofSeconds(10)).sendKeys(name);
        TYPE_ATTRIBUTE.should(Condition.visible, Duration.ofSeconds(10)).click();
        NAME_TYPE_ATTRIBUTE.filter(Condition.text(attribute)).first().click();
        SAVE_BUTTON.should(Condition.visible, Duration.ofSeconds(10)).click();
        return this;
    }


    @Step("Видалити атрибут {attribute}")
    public AddAttributeDocumentPage deleteAttribute(String attribute) {
        Selenide.sleep(3000);
        for (int i = 0; i < ATTRIBUTE_NAMES.size(); i++) {
            if (ATTRIBUTE_NAMES.get(i).getText().trim().equals(attribute)) {
                DELETE_BUTTONS.get(i)
                        .shouldBe(Condition.visible, Duration.ofSeconds(10))
                        .click();
                DELETE_BUTTONS_POP_UP.shouldBe(Condition.visible, Duration.ofSeconds(10))
                        .click();
                return this;
            }
        }

        throw new AssertionError("Атрибут '" + attribute + "' не знайдено.");
    }
}
