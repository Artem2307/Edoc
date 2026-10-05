package api.base.ApiBaseTest;

import io.restassured.response.Response;

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
}