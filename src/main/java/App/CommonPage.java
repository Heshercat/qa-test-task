package App;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CommonPage {
    private final Page page;

    public CommonPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void navigateTo(String url) {
        page.navigate(url);
    }

    public void click(String locator) {
        page.click(locator);
    }

    public void type(String locator, String text) {
        page.type(locator, text);
    }

    public String getTitle() {
        return page.title();
    }

    public void checkPageRoute(String pageRouter) {
        ElementHandle router = page.querySelector("router." + pageRouter);
        Assertions.assertNotNull(router, "Home page not found");
    }

    public void checkTitleByLocator(String titleLocator, String title) {
        assertThat(page.locator(titleLocator)).hasText(title);
    }

    public boolean isSectionVisibleByLocator(String locator) {
        return page.isVisible(locator);
    }
}