package ApiEdocTest;

import Settings.PropertyReader;
import api.base.ApiBaseTest.Steps.AccountsApi;
import api.base.ApiBaseTest.Steps.PermissionsApi;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BaseApiTest {
    PropertyReader propertyReader = new PropertyReader();
    public final String validToken = propertyReader.getPropValues("token");

    protected static final String BASE_URL = "https://edoc.dev/api";

    public final PermissionsApi permissionsApi = new PermissionsApi();
    public final AccountsApi accountsApi = new AccountsApi();
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