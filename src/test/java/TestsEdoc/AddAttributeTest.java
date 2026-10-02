package TestsEdoc;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AddAttributeTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 3: Додати обов'язковий атрибут до типу документу і видалити його")
    public void addAndDeleteRequiredAttribute() {
        accountSelectionPage
                .selectAccount("45664002");

        homePage
                .selectSection("Обов’язкові атрибути");

        requiredAttributesPage
                .openAddDocumentTypeForm();

        String documentType = requiredAttributesPage
                .selectDocumentTypeOrFirstAvailable("Авансовий звіт");

        String addedAttribute = requiredAttributesPage
                .selectAndAddFirstAvailableAttribute();

        requiredAttributesPage
                .save()
                .openDocumentType(documentType)
                .shouldHaveAttribute(documentType, addedAttribute)
                .deleteAddedAttribute(documentType, addedAttribute)
                .shouldNotHaveAttribute(documentType, addedAttribute);
    }
}
