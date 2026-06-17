package user.co.jatri.test.home;

import org.testng.Assert;
import org.testng.annotations.Test;
import user.co.jatri.pages.home.HomePage;
import user.co.jatri.test.base.BaseTest;
import user.co.jatri.utils.MultimodalUserUtil;

public class HomePageTest extends BaseTest {
    @Test(priority = 1)
    public void checkHomePageTitle() {
        HomePage homePage = page.goTo(HomePage.class);
        String pageTitle = homePage.getPageTitle();
        System.out.println("Home Page Title: " + pageTitle);
        Assert.assertEquals(pageTitle, MultimodalUserUtil.HOME_PAGE_TITLE);
    }

    @Test(priority = 2)
    public void checkCookieBannerIsDisplayed() {
        HomePage homePage = page.goTo(HomePage.class);
        homePage.isCookieBannerDisplayed();
    }

    @Test(priority = 3)
    public void checkClickOnAcceptAllCookiesButtonShouldSucceed() {
        HomePage homePage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickAcceptAllCookiesBtn()
                .isCookieBannerHide();
    }

    @Test(priority = 4)
    public void checkClickOnDeclineAllCookiesButtonShouldSucceed() {
        HomePage homePage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickDeclineAllCookiesBtn()
                .isCookieBannerHide();
    }

    @Test(priority = 5)
    public void checkClickOnAcceptAllCookiesButtonShouldSucceed2() {
        HomePage homePage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickDeclineAllCookiesBtn()
                .isCookieBannerHide();
        Assert.assertTrue(homePage.isBusMenuVisibleAndSelected(), "Bus menu should be visible and selected after accepting cookies");
    }
}
