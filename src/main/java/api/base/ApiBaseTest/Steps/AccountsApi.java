package api.base.ApiBaseTest.Steps;

import api.base.ApiBaseTest.Response.AccountsResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AccountsApi {

    private static final String BASE_URL = "https://edoc.dev/api";

    /**
     * Выполняет GET-запрос на получение списка аккаунтов с параметрами.
     */
    public Response getAccounts(String token, Map<String, ?> queryParams) {
        return given()
                .baseUri(BASE_URL)
                .basePath("/accounts")
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .queryParams(queryParams)
                .when()
                .get()
                .thenReturn();
    }

    /**
     * Валидация ответа получения аккаунтов.
     */
    @Step("Проверка получения списка аккаунтов")
    public void validateAccounts(String token, Map<String, ?> queryParams, int expectedStatus, String expectedStatusField) {
        Response response = getAccounts(token, queryParams);

        Assertions.assertEquals(expectedStatus, response.getStatusCode(),
                "Ожидался HTTP статус " + expectedStatus + ", но был: " + response.getStatusCode());

        if (expectedStatus == 200) {
            AccountsResponse accountsResponse = response.getBody().as(AccountsResponse.class);
            Assertions.assertEquals(expectedStatusField, accountsResponse.getStatus(), "Ожидался статус в теле: " + expectedStatusField);
            Assertions.assertNotNull(accountsResponse.getResult(), "Результат не должен быть null");
        } else {
            String statusField = response.jsonPath().getString("status");
            Assertions.assertEquals("failure", statusField, "Ожидался статус failure для кода " + expectedStatus);
        }
    }
}
