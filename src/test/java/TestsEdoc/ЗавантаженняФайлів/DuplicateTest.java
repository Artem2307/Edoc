package TestsEdoc.ЗавантаженняФайлів;

import TestsEdoc.BaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DuplicateTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 10: Завантаження декількох документів документа")
    public void DuplicateTest() {
        homePage
                .clickUploadButton();

        uploadDocumentPage
                .uploadDocument("Авансовий звіт","pdf-auto.pdf")
                .uploadDocument("test.pdf")
                .clickSaveButton();

        documentsCollectionPage
                .searchDocument("pdf-auto.pdf")
                .clickDocument("pdf-auto");

        editDocumentPage
                .deleteDocument();

        documentsCollectionPage
                .searchDocument("test.pdf")
                .clickDocument("test");

        editDocumentPage
                .deleteDocument();
    }
}
