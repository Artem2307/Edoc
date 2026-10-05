package api.base.ApiBaseTest;

import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Основной объект ответа API /permissions
 */
@Data
public class PermissionsResponse {
    private String status;
    private Result result;

    @Data
    public static class Result {
        private List<Permission> list;
        private boolean more;
        private int total;

        @Data
        public static class Permission {
            private String alias;
            private UUID id;
            private String title;
            private Scope scope;

            @Data
            public static class Scope {
                private String alias;
                private UUID id;
                private String title;
            }
        }
    }
}