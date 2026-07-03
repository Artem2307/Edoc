package TestsEdoc;

import Pages.LoginPage;
import Settings.PropertyReader;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;

import static Settings.SelenideSetting.selenideSetting;

public class BaseTest {

    public static LoginPage loginPage = new LoginPage();

    public static PropertyReader properties = new PropertyReader();

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
