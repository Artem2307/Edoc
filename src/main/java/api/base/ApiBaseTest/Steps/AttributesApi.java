package api.base.ApiBaseTest.Steps;

import api.base.ApiBaseTest.Response.AttributesResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class AttributesApi {

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
     * Валидация ответа получения атрибутов.
     */
    @Step("Проверка получения списка атрибутов")
    public void validateAttributes(String token, Map<String, ?> queryParams, int expectedStatus, String expectedStatusField) {
        Response response = getAttributes(token, queryParams);

        System.out.println(response.getBody().asPrettyString());
        Assertions.assertEquals(expectedStatus, response.getStatusCode(),
                "Ожидался HTTP статус " + expectedStatus + ", но был: " + response.getStatusCode());

        if (expectedStatus == 200) {
            AttributesResponse attributesResponse = response.getBody().as(AttributesResponse.class);
            Assertions.assertEquals(expectedStatusField, attributesResponse.getStatus(), "Ожидался статус в теле: " + expectedStatusField);
            
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
        } else {
            String statusField = response.jsonPath().getString("status");
            Assertions.assertNotNull(statusField, "Поле status отсутствует в ответе");
            Assertions.assertEquals(expectedStatusField, statusField, "Ожидался статус " + expectedStatusField + " для кода " + expectedStatus);

            if (response.jsonPath().get("result.message") != null) {
                Assertions.assertNotNull(response.jsonPath().getString("result.message"), "Сообщение об ошибке не должно быть пустым");
            }
        }
    }

    /**
     * Валидация создания атрибута.
     */
    @Step("Проверка создания атрибута")
    public Response validateCreateAttribute(String token, Map<String, Object> body, int expectedStatus, String expectedStatusField) {
        Response response = createAttribute(token, body);
        System.out.println(response.getBody().asPrettyString());
        Assertions.assertEquals(expectedStatus, response.getStatusCode(),
                "Ожидался HTTP статус " + expectedStatus + ", но был: " + response.getStatusCode());

        String statusField = response.jsonPath().getString("status");
        
        Assertions.assertEquals(expectedStatusField, statusField,
                "Ожидался статус в теле: " + expectedStatusField);

        if (expectedStatus == 200) {
            Assertions.assertNotNull(response.jsonPath().getString("result.id"), "ID созданного атрибута отсутствует");
            Assertions.assertEquals(body.get("title"), response.jsonPath().getString("result.title"), "Title не совпадает");
        } else {
            if (response.jsonPath().get("result.message") != null) {
                Assertions.assertNotNull(response.jsonPath().getString("result.message"), "Сообщение об ошибке не должно быть пустым");
            }
        }
        return response;
    }

    /**
     * Валидация обновления атрибута.
     */
    @Step("Проверка обновления атрибута")
    public void validatePatchAttribute(String token, String attributeId, Map<String, Object> body, int expectedStatus, String expectedStatusField) {
        Response response = patchAttribute(token, attributeId, body);
        System.out.println(response.getBody().asPrettyString());
        Assertions.assertEquals(expectedStatus, response.getStatusCode(),
                "Ожидался HTTP статус " + expectedStatus + ", но был: " + response.getStatusCode());

        Assertions.assertEquals(expectedStatusField, response.jsonPath().getString("status"),
                "Ожидался статус в теле: " + expectedStatusField);

        if (expectedStatus == 200) {
            Assertions.assertEquals(attributeId, response.jsonPath().getString("result.id"), "ID атрибута не совпадает");
            if (body.containsKey("title")) {
                Assertions.assertEquals(body.get("title"), response.jsonPath().getString("result.title"), "Title не обновился");
            }
        } else {
            if (response.jsonPath().get("result.message") != null) {
                Assertions.assertNotNull(response.jsonPath().getString("result.message"), "Сообщение об ошибке не должно быть пустым");
            }
        }
    }

    /**
     * Валидация удаления атрибута.
     */
    @Step("Проверка удаления атрибута")
    public void validateDeleteAttribute(String token, String attributeId, int expectedStatus, String expectedStatusField) {
        Response response = deleteAttribute(token, attributeId);
        System.out.println(response.getBody().asPrettyString());
        Assertions.assertEquals(expectedStatus, response.getStatusCode(),
                "Ожидался HTTP статус " + expectedStatus + ", но был: " + response.getStatusCode());

        Assertions.assertEquals(expectedStatusField, response.jsonPath().getString("status"),
                "Ожидался статус в теле: " + expectedStatusField);

        if (expectedStatus == 200) {
            Assertions.assertTrue(response.jsonPath().getBoolean("result.success"), "Success должен быть true");
        } else {
            if (response.jsonPath().get("result.message") != null) {
                Assertions.assertNotNull(response.jsonPath().getString("result.message"), "Сообщение об ошибке не должно быть пустым");
            }
        }
    }

    /**
     * Валидация получения атрибута по ID.
     */
    @Step("Проверка получения атрибута по ID")
    public Response validateGetAttributeById(String token, String attributeId, int expectedStatus, String expectedStatusField) {
        Response response = getAttributeById(token, attributeId);
        System.out.println(response.getBody().asPrettyString());
        Assertions.assertEquals(expectedStatus, response.getStatusCode(),
                "Ожидался HTTP статус " + expectedStatus + ", но был: " + response.getStatusCode());

        Assertions.assertEquals(expectedStatusField, response.jsonPath().getString("status"),
                "Ожидался статус в теле: " + expectedStatusField);

        if (expectedStatus == 200) {
            Assertions.assertEquals(attributeId, response.jsonPath().getString("result.id"), "ID атрибута не совпадает");
        }
        return response;
    }

    public String getTypeId(String token, AttributeTypesApi attributeTypesApi) {
        Response response = attributeTypesApi.getAttributeTypes(token);
        Assertions.assertEquals(200, response.getStatusCode(), "Не вдалося отримати типи атрибутів");
        
        String statusField = response.jsonPath().getString("status");
        Assertions.assertEquals("success", statusField, "Статус відповіді не success");

        // Шукаємо тип 'string', який зазвичай є в системі. Якщо ні - беремо перший.
        String typeId = response.jsonPath().getString("result.list.find { it.alias == 'string' }.id");
        if (typeId == null) {
            typeId = response.jsonPath().getString("result.list[0].id");
        }
        
        Assertions.assertNotNull(typeId, "Не вдалося знайти ID типу атрибута");
        return typeId;
    }

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
