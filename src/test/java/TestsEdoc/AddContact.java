package TestsEdoc;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AddContact extends BaseTest{
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Disabled("Баг на дублі")
    @DisplayName("Сценарій 5: Додати контакт контерагента")
    public void addContact() {
        accountSelectionPage
                .selectAccount("45664002");

        homePage
                .selectSection("Контакти контрагентів");

        addContactPage
                .addContact("a.pravdiuk32143241234@gmail.com","1234567890","Test","Test1","TEST2")
                .deleteContact("1234567890");
    }
}
