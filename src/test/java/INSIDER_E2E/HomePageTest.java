package INSIDER_E2E;

import RunConfig.RunConfigurations;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

@Feature("Insider Home page opening")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HomePageTest extends RunConfigurations {


    @Test
    @Order(1)
    @DisplayName("Check that Insider home page opened by checking DOM router")
    public void checkHomePageRouter() {
        homePage.navigateHome();
        homePage.checkHomePageRoute();

    }

    @Test
    @Order(2)
    @DisplayName("Check that Insider home page opened by displayed page title")
    public void checkHomePageTitle() {
        homePage.checkHomePageTitle();
    }
}
