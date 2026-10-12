package ApiEdocTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttributeTypesTest extends BaseApiTest {

    @Test
    @DisplayName("Сценарій 18: Отримання списку типів атрибутів")
    public void testGetAttributeTypesSuccess() {
        attributeTypesApi.validateAttributeTypes(validToken, 200, "success");
    }

    @ParameterizedTest
    @DisplayName("Сценарій 19: Перевірка помилок авторизації при отриманні типів атрибутів")
    @CsvSource({
            "invalid_token, 401, failure",
            "'', 401, failure"
    })
    public void testGetAttributeTypesErrors(String token, int expectedStatus, String expectedStatusField) {
        if ("invalid_token".equals(token)) {
            token = "wrong_token";
        }
        attributeTypesApi.validateAttributeTypes(token, expectedStatus, expectedStatusField);
    }
}
