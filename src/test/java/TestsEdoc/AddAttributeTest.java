package TestsEdoc;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class AddAttributeTest extends BaseTest{
    @BeforeEach
    public void beforeTest(){
        loginPage.login(properties.getPropValues("Login")
                ,properties.getPropValues("Password"));

        accountSelectionPage
                .selectAccount("45664002");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 3: Додати обов'язковий атрибут і видалити")
    public void addAttribute() {
        homePage
                .selectSection("Обов’язкові атрибути");

        requiredAttributesPage
                .addAttribute("Авансовий звіт","Date");
    }

    @AfterEach()
    public void after(){
        requiredAttributesPage
                .deleteAttribute("Авансовий звіт");
    }
}
