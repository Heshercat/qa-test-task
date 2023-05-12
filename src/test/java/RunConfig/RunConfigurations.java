package RunConfig;

import App.HomePage;
import Core.Config;
import com.microsoft.playwright.*;

import org.junit.jupiter.api.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RunConfigurations extends Config {

    static Playwright playwright;
    static Browser browser;
    public static HomePage homePage;


    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(Config.headless)));
       //browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(Config.headless)));

    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    // New instance for each test method.
    static BrowserContext context;
    public static Page page;


    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
        homePage = new HomePage(page);
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
}
