package TestsEdoc;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AddAttributeDocumentTest extends BaseTest{

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 4: Додати атрибут документа")
    public void addAttribute() {
        accountSelectionPage
                .selectAccount("45664002");

        homePage
                .selectSection("Атрибути документів");

        addAttributeDocumentPage
                .addAttribute("Test","Число")
                .deleteAttribute("Test");
    }
}
