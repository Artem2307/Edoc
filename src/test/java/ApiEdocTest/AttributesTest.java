package ApiEdocTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

public class AttributesTest extends BaseApiTest {

    @Test
    @DisplayName("Сценарій 17: Отримання списку атрибутів з дефолтними параметрами")
    public void testGetAttributesDefault() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("limit", 10);

        attributesApi.validateAttributes(validToken, params, 200, "success");
    }

    @Test
    @DisplayName("Сценарій 18: Отримання списку атрибутів з іншим лімітом")
    public void testGetAttributesWithLimit() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("limit", 5);

        attributesApi.validateAttributes(validToken, params, 200, "success");
    }

    @ParameterizedTest
    @DisplayName("Сценарій 19: Перевірка помилок при отриманні списку атрибутів")
    @CsvSource({
            "invalid_token, 0, 10, 401, failure",
            "'', 0, 10, 401, failure"
    })
    public void testGetAttributesErrors(String token, Integer page, Integer limit, int expectedStatus, String expectedStatusField) {
        if ("VALID_TOKEN".equals(token)) {
            token = validToken;
        }

        Map<String, Object> params = new HashMap<>();
        if (page != null) params.put("page", page);
        if (limit != null) params.put("limit", limit);

        attributesApi.validateAttributes(token, params, expectedStatus, expectedStatusField);
    }
}
