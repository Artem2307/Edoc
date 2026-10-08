package ApiEdocTest;

import api.base.ApiBaseTest.PermissionsApi;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class PermissionsTest extends BaseApiTest {


    @ParameterizedTest
    @CsvSource({
            "a20935dc-c598-4566-b7db-df0cd1cf9486, eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3OTE3OTU3MTcsImlhdCI6MTc5MTE5MDkxNywianRpIjoiOWY5NDhiMTEtYjc2OS00ZGVhLWIyMzAtM2MwY2I1ZjEzNDdiIiwic3ViIjoiYTIwOTM1ZGMtYzU5OC00NTY2LWI3ZGItZGYwY2QxY2Y5NDg2In0.TTtoy8zE-woMJCUQl3Sq0fmGOhYNUJmmXEkMWVUiGpA, 200, success, true",
            "a20935dc-c598-4566-b7db-df0cd1cf9486, invalid_token, 401, Token expired, false",
            "a20935dc-c598-4566-b7db-df0cd1cf9486, iOWY5NDhiMTEtYjc2OS00ZGVhLWIyMzAtM2MwY2I1ZjEzNDdiIiwic3ViIjoiYTIwOTM1ZGMtYzU5OC00NTY2LWI3ZGItZGYwY2QxY2Y5NDg2In0.TTtoy8zE-woMJCUQl3Sq0fmGOhYNUJmmXEkMWVUiGpA, 401, Token expired, false",
            //"a20935dc-c598-4566-b7db-df0cd1cf9486, token_without_permissions, 403, Forbidden, false"
    })

    public void testPermissions(String accountId, String token, int expectedStatus, String expectedMessage, boolean checkResult) {
        permissionsApi.validatePermissions(accountId, token, expectedStatus, expectedMessage, checkResult);
    }
}