package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class RequiredAttributesPage {
    private static final SelenideElement ADD_ATTRIBUTE_BUTTON = $x("//button[@class='sc-gicDKM ceTVwz']");
    private static final SelenideElement TYPE_DOCUMENT_BUTTON = $x("//div/input[@type='text']");
    private static final ElementsCollection TYPE_DOCUMENTS = $$x("//button[@type='button']");
    private static final SelenideElement SELECT_ATTRIBUTE = $x("//span[text()='Додати атрибут']");
    private static final ElementsCollection ATTRIBUTES = $$x("//span");
    private static final SelenideElement SAVE_BUTTON = $x("//button[text()='Зберегти']");
    private static final ElementsCollection ATTRIBUTE_NAMES = $$x("//div/div/div/button//div");
    private static final ElementsCollection DELETE_BUTTONS = $$x("//div/div/div/button");
    private static final SelenideElement DELETE_BUTTONS_POP_UP = $x("//button[text()='Видалити']");

    @Step("Додати атрубут {type} + {attribute}")
    public RequiredAttributesPage addAttribute(String type,String attribute){
        ADD_ATTRIBUTE_BUTTON.should(Condition.visible, Duration.ofSeconds(10)).click();
        TYPE_DOCUMENT_BUTTON.should(Condition.visible, Duration.ofSeconds(10)).click();
        TYPE_DOCUMENTS.filter(Condition.text(type)).first().click();
        SELECT_ATTRIBUTE.should(Condition.visible, Duration.ofSeconds(10)).click();
        ATTRIBUTES.filter(Condition.text(attribute)).first().click();
        SAVE_BUTTON.should(Condition.visible, Duration.ofSeconds(10)).click();
        return this;
    }

    @Step("Видалити атрибут {attribute}")
    public RequiredAttributesPage deleteAttribute(String attribute) {
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
