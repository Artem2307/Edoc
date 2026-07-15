package Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class RequiredAttributesPage {
    private static final SelenideElement ADD_ATTRIBUTE_BUTTON = $x("//button[@class='sc-iIPlFl difteE']");
    private static final SelenideElement TYPE_DOCUMENT_BUTTON = $x("//div/input[@type='text']");
    private static final ElementsCollection TYPE_DOCUMENTS = $$x("//div/input[@type='text']");
    private static final SelenideElement SELECT_ATTRIBUTE = $x("//span[text()='Додати атрибут']");
}
