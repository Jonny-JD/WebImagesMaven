package util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.Properties;

@UtilityClass
public class PropertiesUtil {
    private static Properties PROPERTIES;

    static {
        loadProperties();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    @SneakyThrows
    private static void loadProperties() {
        PROPERTIES = new Properties();
        var resourceAsStream = ConnectionManager
                .class.getClassLoader()
                .getResourceAsStream("application.properties");

        PROPERTIES.load(resourceAsStream);

    }

}
