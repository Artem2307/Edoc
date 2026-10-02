package TestsEdoc;


import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserPermissionsTest extends BaseTest {
    private final String email = "pravdyk1@gmail.com"; // Общий email для всех тестов

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 11: Налаштування прав Керування типами документів")
    public void testManageDocumentTypesPermissions() {
        runPermissionTest(
                "45664002",
                "3672906277",
                "Керування правами",
                "Обов’язкові атрибути",
                "Правдюк Артем Геннадійович",
                "Редагування типів документів",
                "Створення типів документів",
                "Додавання атрибутів для типів документів"
        );
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 12: Налаштування прав Керування додаткових атрибутів")
    public void testManageAdditionalAttributesPermissions() {
        runPermissionTest(
                "45664002",
                "3672906277",
                "Керування правами",
                "Атрибути документів",
                "Правдюк Артем Геннадійович",
                "Видалення додаткових атрибутів",
                "Редагування додаткових атрибутів",
                "Створення додаткових атрибутів"
        );
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 13: Налаштування компанії")
    public void testCompanySettingsPermissions() {
        runPermissionTest(
                "45664002",
                "3672906277",
                "Керування правами",
                "Налаштування компанії",
                "Правдюк Артем Геннадійович",
                "Перегляд акаунту компанії",
                "Редагування компанії"
        );
    }

    /**
     * Универсальный метод для настройки и проверки прав.
     *
     * @param adminAccountId    ID аккаунта администратора.
     * @param userAccountId     ID аккаунта пользователя для проверки.
     * @param manageRightsPage  Наименование секции "Керування правами".
     * @param expectedSection   Ожидаемая секция, видимая/невидимая для пользователя.
     * @param userName          Имя пользователя.
     * @param permissions       Список прав, которые нужно установить.
     */
    private void runPermissionTest(String adminAccountId, String userAccountId, String manageRightsPage, String expectedSection, String userName, String... permissions) {
        // Admin устанавливает права
        accountSelectionPage.selectAccount(adminAccountId);
        homePage.selectSection(manageRightsPage);
        accessControlPage.searchUser(email).openUser(userName).settingUser(permissions);

        // Проверяем, что пользователь НЕ видит секцию
        accountSelectionPage.selectAccount(userAccountId);
        homePage.noVisibleSection(expectedSection);

        // Admin проверяет повторное присвоение тех же прав
        accountSelectionPage.selectAccount(adminAccountId);
        homePage.selectSection(manageRightsPage);
        accessControlPage.searchUser(email).openUser(userName).settingUser(permissions);

        // Проверяем, что пользователь видит секцию
        accountSelectionPage.selectAccount(userAccountId);
        homePage.selectSection(expectedSection);
    }
}

