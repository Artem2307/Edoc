package TestsEdoc;

import Pages.*;
import Settings.PropertyReader;
import com.codeborne.selenide.Selenide;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;

import static Settings.SelenideSetting.selenideSetting;

public class BaseTest {

    public static LoginPage loginPage = new LoginPage();
    public static UploadDocumentPage uploadDocumentPage = new UploadDocumentPage();
    public static HomePage homePage = new HomePage();
    public static PropertyReader properties = new PropertyReader();
    public static EditDocumentPage editDocumentPage = new EditDocumentPage();
    public static AccountSelectionPage accountSelectionPage = new AccountSelectionPage();
    public static DocumentPage documentPage = new DocumentPage();
    public static SignDocumentPage signDocumentPage = new SignDocumentPage();
    public static EditDocumentInformsPage editDocumentInformsPage = new EditDocumentInformsPage();
    public static RequiredAttributesPage requiredAttributesPage = new RequiredAttributesPage();
    public static AddAttributeDocumentPage addAttributeDocumentPage = new AddAttributeDocumentPage();
    public static AddContactPage addContactPage = new AddContactPage();
    public static DocumentsCollectionPage documentsCollectionPage = new DocumentsCollectionPage();
    public static AccessControlPage accessControlPage = new AccessControlPage();
    @BeforeAll
    public static void setUp(){
        selenideSetting();
    }

    @AfterAll
    public static void afterTests(){
        Selenide.closeWindow();
        Selenide.closeWebDriver();
    }
}
