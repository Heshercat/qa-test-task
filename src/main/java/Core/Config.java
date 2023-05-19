package Core;

import com.microsoft.playwright.Page;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    public static final String HOME_URL = "https://useinsider.com/";

    public static String navigateTimeout;
    public static String customWaitTimeout;
    public static String headless;

    static {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            props.load(input);
            navigateTimeout = props.getProperty("navigateTimeout");
            customWaitTimeout = props.getProperty("customWaitTimeout");
            headless = props.getProperty("headless");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void customWait(Page page) {
        page.waitForTimeout(Double.parseDouble(customWaitTimeout));
    }
}
