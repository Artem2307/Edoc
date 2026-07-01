package TestsEdoc;

import Settings.PropertyReader;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class ChangeOperatorTest {

    PropertyReader propertyReader = new PropertyReader();
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 1:")
    public void test() {
        Selenide.open(propertyReader.getPropValues("mainUrl"));
    }

    @AfterEach()
    public void after(){

    }
}
