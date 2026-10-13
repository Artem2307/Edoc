package api.base.ApiBaseTest.Steps;

import api.base.ApiBaseTest.Response.AttributesResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

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
            Assertions.assertEquals("failure", statusField, "Ожидался статус failure для кода " + expectedStatus);

            if (response.jsonPath().get("result.message") != null) {
                Assertions.assertNotNull(response.jsonPath().getString("result.message"), "Сообщение об ошибке не должно быть пустым");
            }
        }
    }
}
