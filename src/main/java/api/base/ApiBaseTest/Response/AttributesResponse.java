package api.base.ApiBaseTest.Response;

import lombok.Data;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class AttributesResponse {
    private String status;
    private Result result;

    @Data
    public static class Result {
        private List<Attribute> list;
        private boolean more;
        private int total;

        @Data
        public static class Attribute {
            private UUID id;
            private String title;
            private Type type;
            private Object data;
            private boolean mandatory;
            private Object value;

            @Data
            public static class Type {
                private UUID id;
                private String title;
                private String alias;
            }
        }
    }
}
