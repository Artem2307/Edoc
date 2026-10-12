package api.base.ApiBaseTest.Response;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class AttributeTypesResponse {
    private String status;
    private Result result;

    @Data
    public static class Result {
        private List<AttributeType> list;
        private boolean more;
        private int total;

        @Data
        public static class AttributeType {
            private UUID id;
            private String title;
            private String alias;
        }
    }
}
