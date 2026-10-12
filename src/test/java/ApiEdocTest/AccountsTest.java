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
    @DisplayName("Сценарій 14: Отримання списку акаунтів з дефолтними параметрами")
    public void testGetAccountsDefault() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("limit", 10);
        
        accountsApi.validateAccounts(validToken, params, 200, "success");
    }

    @Test
    @DisplayName("Сценарій 15: Отримання списку акаунтів з пошуком та сортуванням")
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
    @DisplayName("Сценарій 16: Перевірка помилок авторизації при отриманні списку акаунтів")
    @CsvSource({
            "invalid_token, , , 401, failure",
            "'', , , 401, failure",
            "VALID_TOKEN, -1, 10, 400, failure"
    })
    public void testGetAccountsErrors(String token, Integer page, Integer limit, int expectedStatus, String expectedStatusField) {
        if ("VALID_TOKEN".equals(token)) {
            token = validToken;
        }
        
        Map<String, Object> params = new HashMap<>();
        if (page != null) params.put("page", page);
        if (limit != null) params.put("limit", limit);

        accountsApi.validateAccounts(token, params, expectedStatus, expectedStatusField);
    }
}
