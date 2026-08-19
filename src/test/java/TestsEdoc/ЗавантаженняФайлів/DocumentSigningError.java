package TestsEdoc.ЗавантаженняФайлів;

import TestsEdoc.BaseTest;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DocumentSigningError extends BaseTest {
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 8: Помилка підписання документа ")
    public void signDocumentError() {
        accountSelectionPage
                .selectAccount("3672906277");

        homePage
                .clickUploadButton();

        uploadDocumentPage
                .uploadDocument("Авансовий звіт","pdf-auto.pdf")
                .clickSaveButton();

        editDocumentPage
                .SignDocumentButtonClick();

        editDocumentInformsPage
                .sendNumberDocument("324324");

        signDocumentPage
                .selectSigningOptions("Підпис КЕП","pb_36729062772314321431243124312431243412.jks","43123412");

        editDocumentPage
                .isVisibleDocumentSigningError();
    }

    @AfterEach()
    public void after(){
        editDocumentPage
                .deleteDocument();
    }
}
