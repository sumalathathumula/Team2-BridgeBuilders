package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;


public class ConfigReader {
	private static final Properties properties = new Properties();
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";
    static {
        loadConfigProperties();
    }
    public static void loadConfigProperties() {
        try (InputStream inputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(inputStream);
            System.out.println("Config File Loaded: " + CONFIG_FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getAdminEmail(){
        return properties.getProperty("userLoginEmailId");
    }

    public static String getAdminPassword(){
        return properties.getProperty("password");
    }
    
    public static String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }
	}
