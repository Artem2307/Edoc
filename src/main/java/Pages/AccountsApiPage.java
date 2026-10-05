package Pages;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class AccountsApiPage {

    private static final String BASE_URL = "https://edoc.dev/api"; // Базовый URL
    private static final String ENDPOINT = "/accounts/{account}/permissions"; // Эндпоинт API

    /**
     * Выполнение GET-запроса для получения разрешений аккаунта.
     *
     * @param accountUuid UUID аккаунта
     * @param token       Токен авторизации
     * @return Ответ (Response) с API.
     */
    public Response getPermissions(String accountUuid, String token) {
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + token) // Устанавливаем токен авторизации
                .when()
                .get(ENDPOINT, accountUuid); // Передаём UUID аккаунта в путь
    }

    /**
     * GET-запрос без авторизационного токена (Unauthorized тест).
     *
     * @param accountUuid UUID аккаунта
     * @return Ответ (Response) с API.
     */
    public Response getPermissionsWithoutAuth(String accountUuid) {
        return given()
                .baseUri(BASE_URL)
                .when()
                .get(ENDPOINT, accountUuid);
    }

    /**
     * GET-запрос с некорректным UUID.
     *
     * @param invalidAccountUuid Некорректный UUID
     * @param token              Токен авторизации
     * @return Ответ (Response) с API.
     */
    public Response getInvalidPermissions(String invalidAccountUuid, String token) {
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(ENDPOINT, invalidAccountUuid);
    }
}