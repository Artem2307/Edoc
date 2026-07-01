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
        String propFileName = "config.properties";
        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        Optional.of(inputStream);
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result = prop.getProperty(propertyName);
        return result;
    }
}
