package ApiEdocTest;

import Settings.PropertyReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

public class AccountsTest extends BaseApiTest {

    @Test
    @DisplayName("Получение списка аккаунтов с дефолтными параметрами")
    public void testGetAccountsDefault() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("limit", 10);
        
        accountsApi.validateAccounts(validToken, params, 200, "success");
    }

    @Test
    @DisplayName("Получение списка аккаунтов с поиском и сортировкой")
    public void testGetAccountsWithFilters() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("limit", 5);
        params.put("search", "test");
        params.put("order", "firstname");
        params.put("sort", "desc");

        accountsApi.validateAccounts(validToken, params, 200, "success");
    }

    @ParameterizedTest
    @DisplayName("Проверка обработки ошибок авторизации")
    @CsvSource({
            "invalid_token, 401",
            "'', 401"
    })
    public void testGetAccountsUnauthorized(String token, int expectedStatus) {
        accountsApi.validateAccounts(token, new HashMap<>(), expectedStatus, "failure");
    }
}
