package TestsEdoc;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;


public class LoginTest extends BaseTest{

    @BeforeEach
    public void beforeTest(){
        loginPage.login(
                properties.getPropValues("Login"),
                "Password");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 1:")
    public void test() {

    }

    @AfterEach()
    public void after(){

    }
}
