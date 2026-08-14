package TestsEdoc;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArchiveDocument extends BaseTest{
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 6: додавання файла в архів")
    public void addDocument() {
        accountSelectionPage
                .selectAccount("3672906277");

        homePage
                .clickUploadButton();

        uploadDocumentPage
                .uploadDocument("Авансовий звіт","pdf-auto.pdf")
                .clickSaveButton();

        Selenide.sleep(3000);
        editDocumentPage
                .arсhiveButtonClick();

        homePage
                .selectSection("Архів");

        documentsCollectionPage
                .clickDocument("pdf-auto");
    }

    @AfterEach()
    public void after(){
        editDocumentPage.deleteDocument();
    }
}
