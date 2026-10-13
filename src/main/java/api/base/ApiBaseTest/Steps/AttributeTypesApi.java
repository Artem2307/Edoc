package api.base.ApiBaseTest.Steps;

import api.base.ApiBaseTest.Response.AttributeTypesResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class AttributeTypesApi {

    private static final String BASE_URL = "https://v2.edoc.dev/api";

    /**
     * Выполняет GET-запрос на получение типов атрибутов.
     */
    public Response getAttributeTypes(String token) {
        return given()
                .baseUri(BASE_URL)
                .basePath("/attribute-types")
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .when()
                .get()
                .thenReturn();
    }

    /**
     * Валидация ответа получения типов атрибутов.
     */
    @Step("Проверка получения списка типов атрибутов")
    public void validateAttributeTypes(String token, int expectedStatus, String expectedStatusField) {
        Response response = getAttributeTypes(token);

        System.out.println(response.getBody().asPrettyString());
        Assertions.assertEquals(expectedStatus, response.getStatusCode(),
                "Ожидался HTTP статус " + expectedStatus + ", но был: " + response.getStatusCode());

        if (expectedStatus == 200) {
            AttributeTypesResponse attributeTypesResponse = response.getBody().as(AttributeTypesResponse.class);
            Assertions.assertEquals(expectedStatusField, attributeTypesResponse.getStatus(), "Ожидался статус в теле: " + expectedStatusField);
            
            AttributeTypesResponse.Result result = attributeTypesResponse.getResult();
            Assertions.assertNotNull(result, "Результат (result) отсутствует в ответе");
            Assertions.assertNotNull(result.getList(), "Список типов атрибутов (result.list) отсутствует в ответе");

            if (!result.getList().isEmpty()) {
                for (AttributeTypesResponse.Result.AttributeType type : result.getList()) {
                    Assertions.assertNotNull(type.getId(), "ID типа атрибута отсутствует");
                    Assertions.assertNotNull(type.getTitle(), "Title типа атрибута отсутствует");
                    Assertions.assertNotNull(type.getAlias(), "Alias типа атрибута отсутствует");
                    Assertions.assertFalse(type.getTitle().isEmpty(), "Title типа атрибута не должен быть пустым");
                    Assertions.assertFalse(type.getAlias().isEmpty(), "Alias типа атрибута не должен быть пустым");
                }
            }
        } else {
            String statusField = response.jsonPath().getString("status");
            Assertions.assertEquals(expectedStatusField, statusField, "Ожидался статус в теле: " + expectedStatusField);

            if (response.jsonPath().get("result.message") != null) {
                Assertions.assertNotNull(response.jsonPath().getString("result.message"), "Сообщение об ошибке не должно быть пустым");
            }
        }
    }
}
