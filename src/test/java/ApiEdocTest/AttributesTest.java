package ApiEdocTest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AttributesTest extends BaseApiTest {

    @Test
    @DisplayName("Сценарій 20: Отримання списку атрибутів з дефолтними параметрами")
    public void testGetAttributesDefault() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("limit", 10);

        attributesApi.validateAttributes(validToken, params, 200, "success");
    }

    @Test
    @DisplayName("Сценарій 21: Отримання списку атрибутів з іншим лімітом")
    public void testGetAttributesWithLimit() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("limit", 5);

        attributesApi.validateAttributes(validToken, params, 200, "success");
    }

    @ParameterizedTest
    @DisplayName("Сценарій 22: Перевірка помилок при отриманні списку атрибутів")
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

    @Test
    @DisplayName("Сценарій 23: Створення атрибута")
    public void testCreateAttribute() {
        attributesApi.createTestAttribute(validToken);
    }

    @Test
    @DisplayName("Сценарій 24: Оновлення атрибута")
    public void testUpdateAttribute() {
        String attributeId = attributesApi.createTestAttribute(validToken);

        String newTitle = "Updated Test Attribute " + UUID.randomUUID();
        Map<String, Object> patchBody = new HashMap<>();
        patchBody.put("title", newTitle);

        attributesApi.validatePatchAttribute(validToken, attributeId, patchBody, 200, "success");
        attributesApi.verifyAttributeInList(validToken, attributeId, newTitle, true);
    }

    @Test
    @DisplayName("Сценарій 25: Видалення атрибута")
    public void testDeleteAttribute() {
        String attributeId = attributesApi.createTestAttribute(validToken);

        attributesApi.validateDeleteAttribute(validToken, attributeId, 200, "success");
        attributesApi.verifyAttributeInList(validToken, attributeId, null, false);
        attributesApi.validateDeleteAttribute(validToken, attributeId, 404, "failure");
    }

    @Test
    @DisplayName("Сценарій 26: Помилка 401 при створенні атрибута (неавторизований)")
    public void testCreateAttributeUnauthorized() {
        String typeId = attributesApi.getTypeId(validToken);
        Map<String, Object> body = new HashMap<>();
        body.put("title", "Unauthorized Test");
        body.put("type", typeId);
        body.put("data", new HashMap<>());
        attributesApi.validateCreateAttribute("invalid_token", body, 401, "failure");
    }

    @Test
    @DisplayName("Сценарій 27: Помилка 400 при створенні атрибута (невалідні дані)")
    public void testCreateAttributeInvalidData() {
        Map<String, Object> invalidBody = new HashMap<>();
        invalidBody.put("title", "Valid Title");
        invalidBody.put("type", "invalid-uuid");
        invalidBody.put("data", new HashMap<>());
        attributesApi.validateCreateAttribute(validToken, invalidBody, 400, "failure");
    }

    @Test
    @DisplayName("Сценарій 28: Помилка 404 при оновленні неіснуючого атрибута")
    public void testUpdateNonExistentAttribute() {
        String nonExistentId = UUID.randomUUID().toString();
        Map<String, Object> patchBody = new HashMap<>();
        patchBody.put("title", "Non-existent Update");
        attributesApi.validatePatchAttribute(validToken, nonExistentId, patchBody, 404, "failure");
    }

    @Test
    @DisplayName("Сценарій 29: Помилка 404 при видаленні неіснуючого атрибута")
    public void testDeleteNonExistentAttribute() {
        String nonExistentId = UUID.randomUUID().toString();
        attributesApi.validateDeleteAttribute(validToken, nonExistentId, 404, "failure");
    }

    @Test
    @DisplayName("Сценарій 30: Помилка 401 при оновленні атрибута (неавторизований)")
    public void testUpdateAttributeUnauthorized() {
        String attributeId = attributesApi.createTestAttribute(validToken);
        Map<String, Object> patchBody = new HashMap<>();
        patchBody.put("title", "Unauthorized Update");
        attributesApi.validatePatchAttribute("invalid_token", attributeId, patchBody, 401, "failure");
    }

    @Test
    @DisplayName("Сценарій 31: Помилка 401 при видаленні атрибута (неавторизований)")
    public void testDeleteAttributeUnauthorized() {
        String attributeId = attributesApi.createTestAttribute(validToken);
        attributesApi.validateDeleteAttribute("invalid_token", attributeId, 401, "failure");
    }

    @AfterEach
    public void cleanup() {
        for (String id : attributesApi.createdAttributeIds) {
            try {
                attributesApi.deleteAttribute(validToken, id);
            } catch (Exception e) {
                System.err.println("Failed to delete attribute " + id + ": " + e.getMessage());
            }
        }
        attributesApi.createdAttributeIds.clear();
    }
}
