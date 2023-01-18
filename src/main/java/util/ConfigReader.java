package util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ResourceBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigReader {
    public static String getValue(String key) {
        return ResourceBundle.getBundle("config").getString(key);
    }
}
