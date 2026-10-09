package api.base.ApiBaseTest.Response;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class AccountsResponse {
    private String status;
    private Result result;

    @Data
    public static class Result {
        private List<Account> list;
        private boolean more;
        private int total;

        @Data
        public static class Account {
            private String code;
            private String firstname;
            private String email;
            private UUID id;
            private String lastname;
            private String middlename;
            private boolean verified;
            private Company company;
        }

        @Data
        public static class Company {
            private String code;
            private UUID id;
            private boolean stamp;
            private String title;
        }
    }
}
