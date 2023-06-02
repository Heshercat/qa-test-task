package App;

import Core.Config;
import com.microsoft.playwright.*;

public class HomePage extends CommonPage {

    Page page;

    public HomePage(Page page) {
        super(page);
        this.page = page;
        Config.customWait(page);
    }

    public static final String HOME_PAGE_DOM_ROUTER = "home-page";
    public static final String HOME_PAGE_TITLE = "One platform for individualized, cross-channel customer experiences";

    public static final String HOME_PAGE_TITLE_LOCATOR = "xpath = //*[@id=\"main-head-desktop\"]/div/div/div/div/h1";

    //Header locators
    //TO DO: Move header module to separate file, if/when new tests will be written for other features of this module
    public static final String MORE_DROPDOWN_LOCATOR = "xpath = /html/body/nav/div[2]/div/ul[1]/li[6]/a";
    public static final String CAREERS_OPTION_LOCATOR = "xpath = //*[@id=\"navbarNavDropdown\"]/ul[1]/li[6]/div/div[1]/div[3]/div/a/h5";
    public static final String ACCEPT_ALL_BUTTON_LOCATOR = "xpath = //*[@id=\"wt-cli-accept-all-btn\"]";


    public Page getPage() {
        return page;
    }

    public void navigateHome() {
        page.navigate(Config.HOME_URL, new Page.NavigateOptions()
                .setTimeout(Double.parseDouble(Config.navigateTimeout)));
    }

    public void checkHomePageRoute() {

        checkPageRoute(HOME_PAGE_DOM_ROUTER);
    }

    public void checkHomePageTitle() {
        checkTitleByLocator(HOME_PAGE_TITLE_LOCATOR, HOME_PAGE_TITLE);
    }

    //Header methods
    //TODO: Move header module to separate file, if/when new tests will be written for other features of this module
    public void clickMoreDropDown() {
        click(MORE_DROPDOWN_LOCATOR);
        Config.customWait(page);
    }

    public void clickCareersOption() {
        click(CAREERS_OPTION_LOCATOR);
        Config.customWait(page);
    }

    public void acceptAll() {
        click(ACCEPT_ALL_BUTTON_LOCATOR);
    }
}
