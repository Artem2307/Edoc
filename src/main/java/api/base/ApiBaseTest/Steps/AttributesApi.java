package api.base.ApiBaseTest.Steps;

import api.base.ApiBaseTest.Response.AttributesResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.*;

import static io.restassured.RestAssured.given;

public class AttributesApi {
    public final List<String> createdAttributeIds = new ArrayList<>();

    private static final String BASE_URL = "https://v2.edoc.dev/api";

    /**
     * Выполняет GET-запрос на получение атрибутов с параметрами.
     */
    public Response getAttributes(String token, Map<String, ?> queryParams) {
        return given()
                .baseUri(BASE_URL)
                .basePath("/attributes")
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .queryParams(queryParams)
                .when()
                .get()
                .thenReturn();
    }

    /**
     * Выполняет POST-запрос на создание атрибута.
     */
    public Response createAttribute(String token, Map<String, Object> body) {
        return given()
                .baseUri(BASE_URL)
                .basePath("/attributes")
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post()
                .thenReturn();
    }

    /**
     * Выполняет DELETE-запрос на удаление атрибута.
     */
    public Response deleteAttribute(String token, String attributeId) {
        return given()
                .baseUri(BASE_URL)
                .basePath("/attributes/" + attributeId)
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .when()
                .delete()
                .thenReturn();
    }

    /**
     * Выполняет PATCH-запрос на обновление атрибута.
     */
    public Response patchAttribute(String token, String attributeId, Map<String, Object> body) {
        return given()
                .baseUri(BASE_URL)
                .basePath("/attributes/" + attributeId)
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .patch()
                .thenReturn();
    }

    /**
     * Выполняет GET-запрос на получение атрибута по ID.
     */
    public Response getAttributeById(String token, String attributeId) {
        return given()
                .baseUri(BASE_URL)
                .basePath("/attributes/" + attributeId)
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .when()
                .get()
                .thenReturn();
    }

    /**
     * Базовая валидация ответа (HTTP статус и поле status в теле).
     */
    private void validateBaseResponse(Response response, int expectedStatus, String expectedStatusField) {
        System.out.println(response.getBody().asPrettyString());
        Assertions.assertEquals(expectedStatus, response.getStatusCode(),
                "Ожидался HTTP статус " + expectedStatus + ", но был: " + response.getStatusCode());

        String statusField = response.jsonPath().getString("status");
        Assertions.assertNotNull(statusField, "Поле status отсутствует в ответе");
        Assertions.assertEquals(expectedStatusField, statusField,
                "Ожидался статус в теле: " + expectedStatusField);

        if (expectedStatus != 200) {
            if (response.jsonPath().get("result.message") != null) {
                Assertions.assertNotNull(response.jsonPath().getString("result.message"), "Сообщение об ошибке не должно быть пустым");
            }
        }
    }

    /**
     * Валидация ответа получения атрибутов.
     */
    @Step("Проверка получения списка атрибутов")
    public void validateAttributes(String token, Map<String, ?> queryParams, int expectedStatus, String expectedStatusField) {
        Response response = getAttributes(token, queryParams);
        validateBaseResponse(response, expectedStatus, expectedStatusField);

        if (expectedStatus == 200) {
            AttributesResponse attributesResponse = response.getBody().as(AttributesResponse.class);
            AttributesResponse.Result result = attributesResponse.getResult();
            Assertions.assertNotNull(result, "Результат (result) отсутствует в ответе");
            Assertions.assertNotNull(result.getList(), "Список атрибутов (result.list) отсутствует в ответе");
            Assertions.assertTrue(result.getTotal() >= 0, "Total должен быть >= 0, но было: " + result.getTotal());

            if (!result.getList().isEmpty()) {
                for (AttributesResponse.Result.Attribute attribute : result.getList()) {
                    Assertions.assertNotNull(attribute.getId(), "ID атрибута отсутствует");
                    Assertions.assertNotNull(attribute.getTitle(), "Title атрибута отсутствует");
                    Assertions.assertNotNull(attribute.getType(), "Type атрибута отсутствует");
                    Assertions.assertNotNull(attribute.getType().getId(), "ID типа атрибута отсутствует");
                    Assertions.assertFalse(attribute.getTitle().isEmpty(), "Название атрибута не должно быть пустым");
                }
            }
        }
    }

    /**
     * Валидация создания атрибута.
     */
    @Step("Проверка создания атрибута")
    public Response validateCreateAttribute(String token, Map<String, Object> body, int expectedStatus, String expectedStatusField) {
        Response response = createAttribute(token, body);
        validateBaseResponse(response, expectedStatus, expectedStatusField);

        if (expectedStatus == 200) {
            Assertions.assertNotNull(response.jsonPath().getString("result.id"), "ID созданного атрибута отсутствует");
            Assertions.assertEquals(body.get("title"), response.jsonPath().getString("result.title"), "Title не совпадает");
        }
        return response;
    }

    /**
     * Валидация обновления атрибута.
     */
    @Step("Проверка обновления атрибута")
    public void validatePatchAttribute(String token, String attributeId, Map<String, Object> body, int expectedStatus, String expectedStatusField) {
        Response response = patchAttribute(token, attributeId, body);
        validateBaseResponse(response, expectedStatus, expectedStatusField);

        if (expectedStatus == 200) {
            Assertions.assertEquals(attributeId, response.jsonPath().getString("result.id"), "ID атрибута не совпадает");
            if (body.containsKey("title")) {
                Assertions.assertEquals(body.get("title"), response.jsonPath().getString("result.title"), "Title не обновился");
            }
        }
    }

    /**
     * Валидация удаления атрибута.
     */
    @Step("Проверка удаления атрибута")
    public void validateDeleteAttribute(String token, String attributeId, int expectedStatus, String expectedStatusField) {
        Response response = deleteAttribute(token, attributeId);
        validateBaseResponse(response, expectedStatus, expectedStatusField);

        if (expectedStatus == 200) {
            Assertions.assertTrue(response.jsonPath().getBoolean("result.success"), "Success должен быть true");
        }
    }

    /**
     * Валидация получения атрибута по ID.
     */
    @Step("Проверка получения атрибута по ID")
    public Response validateGetAttributeById(String token, String attributeId, int expectedStatus, String expectedStatusField) {
        Response response = getAttributeById(token, attributeId);
        validateBaseResponse(response, expectedStatus, expectedStatusField);

        if (expectedStatus == 200) {
            Assertions.assertEquals(attributeId, response.jsonPath().getString("result.id"), "ID атрибута не совпадает");
        }
        return response;
    }

    /**
     * Проверка наличия или отсутствия атрибута в списке.
     */
    @Step("Проверка наличия атрибута в списке")
    public void verifyAttributeInList(String token, String attributeId, String expectedTitle, boolean shouldBePresent) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("limit", 100);
        Response response = getAttributes(token, params);
        Assertions.assertEquals(200, response.getStatusCode(), "Не удалось получить список атрибутов для проверки");

        var list = response.jsonPath().getList("result.list", Map.class);
        boolean found = false;
        for (Map item : list) {
            if (attributeId.equals(item.get("id").toString())) {
                found = true;
                if (shouldBePresent && expectedTitle != null) {
                    Assertions.assertEquals(expectedTitle, item.get("title"), "Title не совпадает в списке");
                }
                break;
            }
        }

        if (shouldBePresent) {
            Assertions.assertTrue(found, "Атрибут с ID " + attributeId + " не найден в списке");
        } else {
            Assertions.assertFalse(found, "Атрибут с ID " + attributeId + " все еще присутствует в списке");
        }
    }

    /**
     * Получает ID первого доступного типа атрибута, используя AttributeTypesApi.
     */
    public String getTypeId(String token) {
        return new AttributeTypesApi().getTypeId(token);
    }

    /**
     * Создает тестовый атрибут, регистрирует его для очистки и возвращает его ID.
     */
    public String createTestAttribute(String token) {
        String typeId = getTypeId(token);
        String attributeId = createTestAttribute(token, typeId);
        createdAttributeIds.add(attributeId);
        return attributeId;
    }

    /**
     * Создает тестовый атрибут по указанному ID типа и возвращает его ID.
     */
    public String createTestAttribute(String token, String typeId) {
        String title = "Test Attribute " + UUID.randomUUID();
        Map<String, Object> createBody = new HashMap<>();
        createBody.put("title", title);
        createBody.put("type", typeId);
        createBody.put("data", new HashMap<>());

        Response createResponse = validateCreateAttribute(token, createBody, 200, "success");
        return createResponse.jsonPath().getString("result.id");
    }
}
