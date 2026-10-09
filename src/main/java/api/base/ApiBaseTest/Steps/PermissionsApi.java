package api.base.ApiBaseTest.Steps;

import api.base.ApiBaseTest.Response.PermissionsResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class PermissionsApi {

    private static final String BASE_URL = "https://edoc.dev/api";

    /**
     * Выполняет GET-запрос на получение разрешений для указанного аккаунта.
     *
     * @param accountId ID аккаунта
     * @param token     Токен авторизации
     * @return Response результат запроса
     */
    public Response getPermissions(String accountId, String token) {
        return given()
                .baseUri(BASE_URL)
                .basePath("/accounts/" + accountId + "/permissions")
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .when()
                .get()
                .thenReturn();
    }

    /**
     * Универсальный метод для проверки успешного и ошибочного ответов API.
     */
    @Step("Проверка разрешений для accountId: {0}")
    public void validatePermissions(String accountId, String token, int expectedStatus, String expectedMessage, boolean checkResult) {
        Response response = getPermissions(accountId, token);

        Assertions.assertEquals(expectedStatus, response.getStatusCode(),
                "Ожидался HTTP статус " + expectedStatus + ", но был: " + response.getStatusCode());

        if (expectedStatus == 200) {
            // Проверяем успешный ответ
            PermissionsResponse permissionsResponse = response.getBody().as(PermissionsResponse.class);
            Assertions.assertEquals(expectedMessage, permissionsResponse.getStatus(), "Ожидался статус: success");
            if (checkResult) {
                Assertions.assertNotNull(permissionsResponse.getResult(), "Результат не должен быть null");
                if (!permissionsResponse.getResult().getList().isEmpty()) {
                    Assertions.assertNotNull(permissionsResponse.getResult().getList().get(0).getId(), "ID разрешения не должен быть null");
                }
            }
        } else {
            // Для ошибок (статус не 200) проверяем сообщение об ошибке
            String errorMessage = response.jsonPath().getString("result.message");
            if (expectedMessage != null) {
                Assertions.assertEquals(expectedMessage, errorMessage, "Ожидалась ошибка: " + expectedMessage);
            }
        }
    }
}