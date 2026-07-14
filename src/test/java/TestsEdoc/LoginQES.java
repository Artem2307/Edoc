package TestsEdoc;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginQES extends BaseTest{

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 2: Вхід в аккаунт через КЕП")
    public void test() {
        loginPage
                .loginQES("pb_36729062772314321431243124312431243412.jks");
    }
}
