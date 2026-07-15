package Settings;



import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class PropertyReader {

    String result;
    InputStream inputStream;

    @SneakyThrows
    public String getPropValues(String propertyName) {
        Properties prop = new Properties();

        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("config.properties not found");
            }

            prop.load(inputStream);

            String value = prop.getProperty(propertyName);

            if (value == null) {
                throw new IllegalArgumentException(
                        "Property '" + propertyName + "' not found. Available keys: " + prop.keySet()
                );
            }

            return value;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
