package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static Pages.RequiredAttributesPage.SAVE_BUTTON;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class AddContactPage {
    private static final SelenideElement ADD_CONTACT_BUTTON = $x("//div/button");
    private static final SelenideElement EMAIL_STRING = $x("//div[1]/input[@type=\"email\"]");
    private static final SelenideElement EDRPU_STRING = $x("//div[1]/input[@type=\"number\"]");
    private static final SelenideElement SURNAME_STRING = $x("//div/input[@placeholder=\"Петренко\"]");
    private static final SelenideElement NAME_STRING = $x("//div/input[@placeholder=\"Петр\"]");
    private static final SelenideElement MIDDLE_NAME_STRING = $x("//div/input[@placeholder=\"Петрович\"]");
    private static final ElementsCollection CONTACT_NAMES = $$x("//div/div[1]/div/span");
    private static final ElementsCollection DELETE_BUTTONS = $$x("//*[@id=\"root\"]/main/section/div[2]/div/div/div/button");
    private static final SelenideElement DELETE_BUTTONS_POP_UP = $x("//button[text()='Видалити']");

    @Step("Додати контакт {email} + {number} + {surname} + {name} + {middleName}")
    public AddContactPage addContact(String email,String number,String surname,String name,String middleName){
        ADD_CONTACT_BUTTON.should(Condition.visible, Duration.ofSeconds(10)).click();
        EMAIL_STRING.should(Condition.visible, Duration.ofSeconds(10)).sendKeys(email);
        EDRPU_STRING.should(Condition.visible, Duration.ofSeconds(10)).sendKeys(number);
        SURNAME_STRING.should(Condition.visible, Duration.ofSeconds(10)).sendKeys(surname);
        NAME_STRING.should(Condition.visible, Duration.ofSeconds(10)).sendKeys(name);
        MIDDLE_NAME_STRING.should(Condition.visible, Duration.ofSeconds(10)).sendKeys(middleName);
        SAVE_BUTTON.should(Condition.visible, Duration.ofSeconds(10)).click();
        return this;
    }

    @Step("Видалити атрибут {Contact}")
    public AddContactPage deleteContact(String Contact) {
        Selenide.sleep(3000);
        for (int i = 0; i < CONTACT_NAMES.size(); i++) {
            if (CONTACT_NAMES.get(i).getText().trim().equals(Contact)) {
                DELETE_BUTTONS.get(i)
                        .shouldBe(Condition.visible, Duration.ofSeconds(10))
                        .click();
                DELETE_BUTTONS_POP_UP.shouldBe(Condition.visible, Duration.ofSeconds(10))
                        .click();
                return this;
            }
        }

        throw new AssertionError("Контакт '" + Contact + "' не знайдено.");
    }
}
