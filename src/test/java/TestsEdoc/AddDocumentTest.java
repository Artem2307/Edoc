package TestsEdoc;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;


public class AddDocumentTest extends BaseTest{

    @BeforeEach
    public void beforeTest(){
        loginPage.login(properties.getPropValues("Login")
                ,properties.getPropValues("Password"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 1: Завантаження і видалення документа")
    public void test() {
        homePage
                .clickUploadButton();

        uploadDocumentPage
                .uploadDocument("Авансовий звіт","pdf-auto.pdf")
                .clickSaveButton();
    }

    @AfterEach()
    public void after(){
        editDocumentPage
                .deleteDocument();
    }
}
