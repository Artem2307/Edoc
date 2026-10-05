package ApiEdocTest;

import api.base.ApiBaseTest.PermissionsApi;
import api.base.ApiBaseTest.PermissionsResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PermissionsTest extends BaseApiTest {

    private final PermissionsApi permissionsApi = new PermissionsApi();

    @ParameterizedTest
    @CsvSource({
            "a20935dc-c598-4566-b7db-df0cd1cf9486, eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3OTE3OTU3MTcsImlhdCI6MTc5MTE5MDkxNywianRpIjoiOWY5NDhiMTEtYjc2OS00ZGVhLWIyMzAtM2MwY2I1ZjEzNDdiIiwic3ViIjoiYTIwOTM1ZGMtYzU5OC00NTY2LWI3ZGItZGYwY2QxY2Y5NDg2In0.TTtoy8zE-woMJCUQl3Sq0fmGOhYNUJmmXEkMWVUiGpA, 200, success, true",
            "a20935dc-c598-4566-b7db-df0cd1cf9486, invalid_token, 401, Token expired, false",
            "a20935dc-c598-4566-b7db-df0cd1cf9486, iOWY5NDhiMTEtYjc2OS00ZGVhLWIyMzAtM2MwY2I1ZjEzNDdiIiwic3ViIjoiYTIwOTM1ZGMtYzU5OC00NTY2LWI3ZGItZGYwY2QxY2Y5NDg2In0.TTtoy8zE-woMJCUQl3Sq0fmGOhYNUJmmXEkMWVUiGpA, 401, Token expired, false",
            //"a20935dc-c598-4566-b7db-df0cd1cf9486, token_without_permissions, 403, Forbidden, false"
    })

    public void testPermissions(String accountId, String token, int expectedStatus, String expectedMessage, boolean checkResult) {
        validatePermissions(accountId, token, expectedStatus, expectedMessage, checkResult);
    }

    /**
     * Универсальный метод для проверки успешного и ошибочного ответов API.
     */
    private void validatePermissions(String accountId, String token, int expectedStatus, String expectedMessage, boolean checkResult) {
        Response response = permissionsApi.getPermissions(accountId, token);

        assertStatusCode(response, expectedStatus);

        if (expectedStatus == 200) {
            // Проверяем успешный ответ
            PermissionsResponse permissionsResponse = response.getBody().as(PermissionsResponse.class);
            assertEquals(expectedMessage, permissionsResponse.getStatus(), "Ожидался статус: success");
            if (checkResult) {
                assertNotNull(permissionsResponse.getResult(), "Результат не должен быть null");
                if (!permissionsResponse.getResult().getList().isEmpty()) {
                    assertNotNull(permissionsResponse.getResult().getList().get(0).getId(), "ID разрешения не должен быть null");
                }
            }
        } else {
            // Для ошибок (статус не 200) проверяем сообщение об ошибке
            String errorMessage = response.jsonPath().getString("result.message");
            if (expectedMessage != null) {
                assertEquals(expectedMessage, errorMessage, "Ожидалась ошибка: " + expectedMessage);
            }
        }
    }
}