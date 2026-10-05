package ApiEdocTest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BaseApiTest {

    protected static final String BASE_URL = "https://edoc.dev/api";

    /**
     * Выполняет запрос GET с использованием базового URL
     *
     * @param endpoint Конечная точка API
     * @param token    Токен авторизации
     * @return Response Ответ от сервера
     */
    protected Response performGetRequest(String endpoint, String token) {
        return given()
                .baseUri(BASE_URL)
                .basePath(endpoint)
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .when()
                .get()
                .thenReturn();
    }

    /**
     * Метод для проверки статус-кода и возврата ответа
     *
     * @param response      Ответ на запрос
     * @param expectedStatus Ожидаемый статус HTTP
     */
    protected void assertStatusCode(Response response, int expectedStatus) {
        if (response == null) {
            throw new AssertionError("Ответ не получен (Response is null)");
        }
        int actualStatus = response.statusCode();
        if (actualStatus != expectedStatus) {
            throw new AssertionError("Ожидался HTTP статус " + expectedStatus + ", но был: " + actualStatus);
        }
    }
}