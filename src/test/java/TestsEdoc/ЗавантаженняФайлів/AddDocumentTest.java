package TestsEdoc.ЗавантаженняФайлів;

import TestsEdoc.BaseTest;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;


public class AddDocumentTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 1: Завантаження,підпис, видалення документа")
    public void addDocument() {
        accountSelectionPage
                .selectAccount("3672906277");

        homePage
                .clickUploadButton();

        uploadDocumentPage
                .uploadDocument("Авансовий звіт","pdf-auto.pdf")
                .clickSaveButton();

        editDocumentPage
                .SignDocumentButtonClick();

        Selenide.sleep(3000);
        editDocumentInformsPage
                .sendNumberDocument("324324");

        signDocumentPage
                .selectSigningOptions("Підпис КЕП","pb_36729062772314321431243124312431243412.jks",properties.getPropValues("Password"));

        editDocumentPage
                .isVisibleDocumentSigningSuccess();
    }

    @AfterEach()
    public void after(){
        editDocumentPage
                .deleteDocument();
    }
}
