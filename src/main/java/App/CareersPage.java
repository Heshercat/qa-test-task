package App;

import Core.Config;
import com.microsoft.playwright.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CareersPage extends CommonPage {

    Page page;

    public CareersPage(Page page) {
        super(page);
        this.page = page;
        Config.customWait(page);
    }

    public static final String CAREERS_PAGE_DOM_ROUTER = "career-page";
    public static final String CAREERS_PAGE_TITLE = "Ready to disrupt?";
    public static final String TEAMS_SECTION_TITLE = "Find your calling";
    public static final String LOCATIONS_SECTION_TITLE = "Our Locations";
    public static final String LIFE_AT_INSIDER_SECTION_TITLE = "Life at Insider";
    public static final String JOBS_LEVER_URL = "https://jobs.lever.co";


    public static final String CAREER_PAGE_TITLE_LOCATOR = "xpath = //*[@id=\"page-head\"]/div/div/div[1]/div/h1";
    public static final String TEAMS_TITLE_LOCATOR = "xpath = //*[@id=\"career-find-our-calling\"]/div/div/div[1]/h3 ";
    public static final String TEAMS_SECTION_LOCATOR = "xpath = //*[@id=\"career-find-our-calling\"]";
    public static final String LOCATIONS_TITLE_LOCATOR = "xpath = //*[@id=\"career-our-location\"]/div/div/div/div[1]/h3";
    public static final String LOCATIONS_LIST_LOCATOR = "xpath = //*[@id=\"location-slider\"]/div/ul";
    public static final String LIFE_AT_INSIDER_TITLE_LOCATOR = "xpath = /html/body/div[1]/div/div/section[4]/div/div/div/div/div/div[1]/div/h2";
    public static final String LIFE_AT_INSIDER_SECTION_LOCATOR = "xpath = /html/body/div[1]/div/div/section[4]";
    public static final String SEE_ALL_TEAMS_BUTTON_LOCATOR = "xpath = //*[@id=\"career-find-our-calling\"]/div/div/a";
    public final static String QA_TEAM_LOCATOR = "xpath = //*[@id=\"career-find-our-calling\"]/div/div/div[2]/div[12]/div[2]";
    public final static String SEE_ALL_QA_JOBS_BUTTON_LOCATOR = "xpath = //*[@id=\"page-head\"]/div/div/div[1]/div/div/a";
    public final static String FILTER_BY_LOCATIONS_LOCATOR = "xpath = //*[@id=\"select2-filter-by-location-container\"]";
    public final static String LOCATION_OPTION_LOCATOR = "#select2-filter-by-location-results > li";
    public final static String OPEN_POSITIONS_SECTION_LOCATOR = "xpath = //*[@id=\"career-position-list\"]/div/div";
    public static final String JOB_LIST_LOCATOR = "xpath = //*[@id=\"jobs-list\"]";

    public final static String APPLY_NOW_BUTTON_LOCATOR = "xpath = //*[@id=\"jobs-list\"]/div[1]/div/a";


    public void checkCareersPageRoute() {
        checkPageRoute(CAREERS_PAGE_DOM_ROUTER);
    }

    public void checkCareersPageTitle() {
        checkTitleByLocator(CAREER_PAGE_TITLE_LOCATOR, CAREERS_PAGE_TITLE);
    }

    public void checkTeamsSectionTitle() {
        checkTitleByLocator(TEAMS_TITLE_LOCATOR, TEAMS_SECTION_TITLE);
    }

    public void checkTeamsSectionVisible() {
        assertTrue(isSectionVisibleByLocator(TEAMS_SECTION_LOCATOR), "Section is not visible");
    }

    public void checkLocationsSectionTitle() {
        checkTitleByLocator(LOCATIONS_TITLE_LOCATOR, LOCATIONS_SECTION_TITLE);
    }

    public boolean checkLocationOptions(String locator) {
        boolean allOptionsPresent = true;
        Locator optionsLocator = page.locator(locator);

        for (ElementHandle option : optionsLocator.elementHandles()) {
            if (option.innerText().isEmpty()) {
                allOptionsPresent = false;
                break;
            }
        }

        return allOptionsPresent;
    }

    public void checkLocationOptionsVisible() {
        assertTrue(checkLocationOptions(LOCATIONS_LIST_LOCATOR));
    }

    public void checkLifeAtInsiderSectionTitle() {
        checkTitleByLocator(LIFE_AT_INSIDER_TITLE_LOCATOR, LIFE_AT_INSIDER_SECTION_TITLE);
    }

    public void checkLifeAtInsiderSectionVisible() {
        assertTrue(isSectionVisibleByLocator(LIFE_AT_INSIDER_SECTION_LOCATOR), "Section is not visible");
    }

    public void clickSeeAllButton() {
        click(SEE_ALL_TEAMS_BUTTON_LOCATOR);
    }

    public void clickQaTeam() {
        click(QA_TEAM_LOCATOR);
    }

    public void clickSeeAllQaButton() {
        click(SEE_ALL_QA_JOBS_BUTTON_LOCATOR);
    }

    public void clickLocationsDropDown() {
        click(FILTER_BY_LOCATIONS_LOCATOR);
    }

    public void selectLocationByText(String selectedLocation) {
        ElementHandle locationOption = page.waitForSelector("#select2-filter-by-location-results");
        List<ElementHandle> options = page.querySelectorAll(LOCATION_OPTION_LOCATOR);
        for (ElementHandle option : options) {
            String text = option.innerText();
            if (text.equals(selectedLocation)) {
                option.click();
                break;
            }
        }
    }


    public void checkOpenPositionsSectionVisible() {
        assertTrue(isSectionVisibleByLocator(OPEN_POSITIONS_SECTION_LOCATOR), "Section is not visible");
    }

    //TO DO: Create separate method for each check so that the principle of unitary checks is preserved
    //TO DO: Rework button
    public boolean hasQualityAssuranceJobInIstanbul() {
        String jobDepartment = "Quality Assurance";
        String jobLocation = "Istanbul, Turkey";

        String jobDepartmentSelector = ".position-department";
        String jobLocationSelector = ".position-location";
        String applyButtonSelector = "a[href^='https://jobs.lever.co/useinsider']";

        page.waitForSelector(JOB_LIST_LOCATOR);

        for (ElementHandle job : page.querySelectorAll(JOB_LIST_LOCATOR)) {
            String department = job.querySelector(jobDepartmentSelector).textContent();
            String location = job.querySelector(jobLocationSelector).textContent();
            boolean hasApplyButton = job.querySelector(applyButtonSelector) != null;

            if (department.contains(jobDepartment) && location.contains(jobLocation) && hasApplyButton) {
                return true;
            }
        }

        return false;
    }

    public void checkAllJobCardElementsPresent() {
        assertTrue(hasQualityAssuranceJobInIstanbul(), "Some elements are invisible");
    }

    public void clickApplyNowButton() {
        page.locator(APPLY_NOW_BUTTON_LOCATOR).hover();
        click(APPLY_NOW_BUTTON_LOCATOR);
        Config.customWait(page);
    }

    //TO DO: finish and rework this method, currently it doesn't work in right way and takes the url from the previous page
    public void checkNewTabOpened(String expectedUrl) {
        page.bringToFront();
        String currentUrl = page.url();
        assertTrue(currentUrl.contains(expectedUrl));
    }

    public void checkJobsLeverOpened() {
        checkNewTabOpened(JOBS_LEVER_URL);
    }
}





