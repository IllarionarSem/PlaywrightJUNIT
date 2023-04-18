package util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class ConfigReader {
    private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/config.properties";

    public static String getValue(String key) {
        String value = "";
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            Properties prop = new Properties();
            prop.load(fis);
            value = prop.getProperty(key);
        } catch (IOException e) {
            log.error(String.format("Property %s not found", key));
        }
        return value;
    }
}
