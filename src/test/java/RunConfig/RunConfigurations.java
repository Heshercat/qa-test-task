package RunConfig;

import App.HomePage;
import Core.Config;
import com.microsoft.playwright.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.File;
import java.nio.file.Paths;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RunConfigurations extends Config implements TestWatcher {

    static Playwright playwright;
    static Browser browser;
    public static HomePage homePage;
    private boolean testFailed = false;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(Config.headless)));

        File screenshotsDir = new File("screenshots");
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdir();
        }
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    static BrowserContext context;
    public static Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
        homePage = new HomePage(page);
        testFailed = false;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = context.getRequiredTestMethod().getName();
        try {
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots", className + "_" + methodName + "_failed.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = context.getRequiredTestMethod().getName();
        try {
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots", className + "_" + methodName + "_failed.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void closeContext() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
    }
}
