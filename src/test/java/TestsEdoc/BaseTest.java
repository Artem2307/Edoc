package TestsEdoc;

import Pages.EditDocumentPage;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.UploadDocumentPage;
import Settings.PropertyReader;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;

import static Settings.SelenideSetting.selenideSetting;

public class BaseTest {

    public static LoginPage loginPage = new LoginPage();
    public static UploadDocumentPage uploadDocumentPage = new UploadDocumentPage();
    public static HomePage homePage = new HomePage();
    public static PropertyReader properties = new PropertyReader();
    public static EditDocumentPage editDocumentPage = new EditDocumentPage();

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
