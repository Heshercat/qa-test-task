package INSIDER_E2E;

import App.CareersPage;
import RunConfig.RunConfigurations;
import com.microsoft.playwright.Page;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

@Feature("Careers page tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CareersPageTest extends RunConfigurations {

    private static CareersPage careersPage;

    @BeforeEach
    void setUp() {
        homePage.navigateHome();
        Page page = homePage.getPage();
        careersPage = new CareersPage(page);
        homePage.acceptAll();
        homePage.clickMoreDropDown();
        homePage.clickCareersOption();
    }

    //TO DO: move out each check in a separate test so that the principle of unitary checks is preserved
    @Test
    @Order(1)
    @DisplayName("Check that Careers page, its Locations, Teams and Life at Insider blocks are opened")
    public void checkCareerPageElements() {
        careersPage.checkCareersPageRoute();
        careersPage.checkCareersPageTitle();
        careersPage.checkTeamsSectionTitle();
        careersPage.checkTeamsSectionVisible();
        careersPage.checkLocationsSectionTitle();
        careersPage.checkLocationOptionsVisible();
        careersPage.checkLifeAtInsiderSectionTitle();
        careersPage.checkLifeAtInsiderSectionVisible();
    }

    //TO DO: move out each check in a separate test so that the principle of unitary checks is preserved
    //Filter by department must be prefilled by default with current department value
    @Test
    @Order(2)
    @DisplayName("Check job list after applying filters")
    public void checkJobListDisplayed() {
        careersPage.clickSeeAllButton();
        careersPage.clickQaTeam();
        careersPage.clickSeeAllQaButton();
        careersPage.clickLocationsDropDown();
        careersPage.selectLocationByText("Istanbul, Turkey");
        careersPage.checkOpenPositionsSectionVisible();
        careersPage.checkAllJobCardElementsPresent();
    }

    @Test
    @Order(3)
    @DisplayName("Lever Application form page opens by click on Apply Now button")
    public void leverApplicationFormOpens() {
        careersPage.clickSeeAllButton();
        careersPage.clickQaTeam();
        careersPage.clickSeeAllQaButton();
        careersPage.clickLocationsDropDown();
        careersPage.selectLocationByText("Istanbul, Turkey");
        careersPage.clickApplyNowButton();
        careersPage.checkJobsLeverOpened();
    }

}
