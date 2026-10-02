package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class RequiredAttributesPage {
    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    private static final SelenideElement ADD_DOCUMENT_TYPE_BUTTON =
            $x("//button[normalize-space()='Додати тип документу']");

    private static final SelenideElement DOCUMENT_TYPE_INPUT =
            $x("//div[@role='dialog']//input[@placeholder='Оберіть або введіть']");

    private static final SelenideElement ATTRIBUTE_INPUT_IN_DIALOG =
            $x("//span[text()='Додати атрибут']");

    private static final SelenideElement ADD_ATTRIBUTE_BUTTON_IN_DIALOG =
            $x("//div[@role='dialog']//button[normalize-space()='Додати атрибут']");

    public static final SelenideElement SAVE_BUTTON =
            $x("//div[@role='dialog']//button[normalize-space()='Зберегти']");

    private SelenideElement documentTypeOption(String documentType) {
        return $x("//li//button[normalize-space()='" + documentType + "']");
    }

    private SelenideElement firstDocumentTypeOption() {
        return $x("(//li//button[normalize-space()])[1]");
    }

    private SelenideElement firstAttributeOption() {
        return $x("//ul[@class=\"_0Zo0ugilw_lJ9ovcfUQH\"]//li//button[@type=\"button\"]");
    }

    private SelenideElement documentTypeButton(String documentType) {
        return $x("//button[normalize-space()='" + documentType + "']");
    }

    private SelenideElement expandedDocumentTypeBlock(String documentType) {
        return $x("//button[normalize-space()='" + documentType + "']/ancestor::div[.//*[normalize-space()='Атрибути']][1]");
    }

    private SelenideElement attributeInputInExpandedDocumentType(String documentType, String attribute) {
        return $x("//button[normalize-space()='" + documentType + "']/ancestor::div[.//*[normalize-space()='Атрибути']][1]" +
                "//input[@placeholder='Оберіть атрибут...' and @value='" + attribute + "']");
    }

    private SelenideElement clearAddedAttributeButton(String documentType, String attribute) {
        return $x("//button[normalize-space()='" + documentType + "']/ancestor::div[.//*[normalize-space()='Атрибути']][1]" +
                "//input[@placeholder='Оберіть атрибут...' and @value='" + attribute + "']" +
                "/following::button[@aria-label='Clear'][1]");
    }

    @Step("Відкрити форму додавання типу документу")
    public RequiredAttributesPage openAddDocumentTypeForm() {
        ADD_DOCUMENT_TYPE_BUTTON
                .shouldBe(Condition.visible, TIMEOUT)
                .click();

        DOCUMENT_TYPE_INPUT
                .shouldBe(Condition.visible, TIMEOUT);

        return this;
    }

    @Step("Обрати тип документу {0} або перший доступний")
    public String selectDocumentTypeOrFirstAvailable(String preferredDocumentType) {
        DOCUMENT_TYPE_INPUT
                .shouldBe(Condition.visible, TIMEOUT)
                .click();

        SelenideElement preferredOption = documentTypeOption(preferredDocumentType);

        if (preferredOption.exists()) {
            preferredOption
                    .shouldBe(Condition.visible, TIMEOUT)
                    .click();

            return preferredDocumentType;
        }

        SelenideElement firstOption = firstDocumentTypeOption()
                .shouldBe(Condition.visible, TIMEOUT);

        String selectedDocumentType = firstOption.getText().trim();
        firstOption.click();

        return selectedDocumentType;
    }

    @Step("Обрати перший доступний атрибут і додати його до типу документу")
    public String selectAndAddFirstAvailableAttribute() {
        ATTRIBUTE_INPUT_IN_DIALOG
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .click();

        SelenideElement firstAttribute = firstAttributeOption()
                .shouldBe(Condition.visible, Duration.ofSeconds(10));

        String selectedAttribute = firstAttribute.getText().trim();
        firstAttribute.click();

        ADD_ATTRIBUTE_BUTTON_IN_DIALOG
                .shouldBe(Condition.enabled, TIMEOUT)
                .click();

        return selectedAttribute;
    }

    @Step("Зберегти обов'язкові атрибути")
    public RequiredAttributesPage save() {
        SAVE_BUTTON
                .shouldBe(Condition.enabled, TIMEOUT)
                .click();

        SAVE_BUTTON
                .shouldBe(Condition.disappear, TIMEOUT);

        return this;
    }

    @Step("Відкрити тип документу {0}")
    public RequiredAttributesPage openDocumentType(String documentType) {
        documentTypeButton(documentType)
                .shouldBe(Condition.visible, TIMEOUT)
                .click();

        expandedDocumentTypeBlock(documentType)
                .shouldBe(Condition.visible, TIMEOUT);

        return this;
    }

    @Step("Перевірити, що атрибут {1} додано до типу документу {0}")
    public RequiredAttributesPage shouldHaveAttribute(String documentType, String attribute) {
        attributeInputInExpandedDocumentType(documentType, attribute)
                .shouldBe(Condition.visible, TIMEOUT);

        return this;
    }

    @Step("Видалити доданий атрибут {1} з типу документу {0}")
    public RequiredAttributesPage deleteAddedAttribute(String documentType, String attribute) {
        attributeInputInExpandedDocumentType(documentType, attribute)
                .shouldBe(Condition.visible, TIMEOUT);

        clearAddedAttributeButton(documentType, attribute)
                .shouldBe(Condition.visible, TIMEOUT)
                .click();

        return this;
    }

    @Step("Перевірити, що атрибут {1} більше не відображається у типі документу {0}")
    public RequiredAttributesPage shouldNotHaveAttribute(String documentType, String attribute) {
        attributeInputInExpandedDocumentType(documentType, attribute)
                .shouldNotBe(Condition.visible, TIMEOUT);

        return this;
    }
}