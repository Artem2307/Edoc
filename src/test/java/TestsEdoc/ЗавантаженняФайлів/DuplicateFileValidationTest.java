package TestsEdoc.ЗавантаженняФайлів;

import TestsEdoc.BaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DuplicateFileValidationTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 7: Завантаження дубліката документа")
    public void duplicateFileValidation() {
        accountSelectionPage
                .selectAccount("3672906277");

        homePage
                .clickUploadButton();

        uploadDocumentPage
                .uploadDocument("Авансовий звіт","pdf-auto.pdf")
                .uploadDocument("pdf-auto.pdf")
                .isVisibleDuplicateFile();


    }
}
