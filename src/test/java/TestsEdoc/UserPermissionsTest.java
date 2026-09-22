package TestsEdoc;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserPermissionsTest extends BaseTest{
    private String email = "pravdyk1@gmail.com";

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 11: Налаштування прав Керування типами документів")
    public void UserPermissionsTest() {
        accountSelectionPage
                .selectAccount("45664002");

        homePage
                .selectSection("Керування правами");

        accessControlPage
                .searchUser(email)
                .openUser("Правдюк Артем Геннадійович")
                .settingUser("Редагування типів документів","Створення типів документів","Додавання атрибутів для типів документів");

        accountSelectionPage
                .selectAccount("3672906277");
        homePage
                .noVisibleSection("Обов’язкові атрибути");

        accountSelectionPage
                .selectAccount("45664002");

        homePage
                .selectSection("Керування правами");

        accessControlPage
                .searchUser(email)
                .openUser("Правдюк Артем Геннадійович")
                .settingUser("Редагування типів документів","Створення типів документів","Додавання атрибутів для типів документів");

        accountSelectionPage
                .selectAccount("3672906277");
        homePage
                .selectSection("Обов’язкові атрибути");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 12: Налаштування прав Керування додаткових атрибутів")
    public void UserPermissionsTest1() {
        accountSelectionPage
                .selectAccount("45664002");

        homePage
                .selectSection("Керування правами");

        accessControlPage
                .searchUser(email)
                .openUser("Правдюк Артем Геннадійович")
                .settingUser("Видалення додаткових атрибутів","Редагування додаткових атрибутів","Створення додаткових атрибутів");

        accountSelectionPage
                .selectAccount("3672906277");
        homePage
                .noVisibleSection("Атрибути документів");

        accountSelectionPage
                .selectAccount("45664002");

        homePage
                .selectSection("Керування правами");

        accessControlPage
                .searchUser(email)
                .openUser("Правдюк Артем Геннадійович")
                .settingUser("Видалення додаткових атрибутів","Редагування додаткових атрибутів","Створення додаткових атрибутів");

        accountSelectionPage
                .selectAccount("3672906277");
        homePage
                .selectSection("Атрибути документів");
    }
}
