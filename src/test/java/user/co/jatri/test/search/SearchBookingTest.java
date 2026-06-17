package user.co.jatri.test.search;

import org.testng.Assert;
import org.testng.annotations.Test;
import user.co.jatri.pages.home.HomePage;
import user.co.jatri.pages.search.SearchBookingPage;
import user.co.jatri.pages.service.ServiceSelectionPage;
import user.co.jatri.test.base.BaseTest;
import user.co.jatri.utils.MultimodalUserUtil;

public class SearchBookingTest extends BaseTest {
    @Test(priority = 1)
    public void checkSearchTripWithInValidDataShouldFailed() throws InterruptedException {
        SearchBookingPage SearchBookingPage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickAcceptAllCookiesBtn()
                .isCookieBannerHide()
                .navigateToSearchBookingPage()
                .clickOnSearchButtonForFailed();
        Assert.assertTrue(SearchBookingPage.isFromPlaceAndDestinationPlaceIsRequiredErrorMessageFound());
    }

    @Test(priority = 2)
    public void checkSearchTripWithLeavingFromOnlyShouldFailed() throws InterruptedException {
        SearchBookingPage SearchBookingPage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickAcceptAllCookiesBtn()
                .isCookieBannerHide()
                .navigateToSearchBookingPage()
                .selectLeavingFrom("Dhaka")
                .clickOnSearchButtonForFailed();
        Assert.assertTrue(SearchBookingPage.isDestinationIsRequiredErrorMessageFound());
    }

    @Test(priority = 3)
    public void checkSearchTripWithGoingToOnlyShouldFailed() throws InterruptedException {
        SearchBookingPage SearchBookingPage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickAcceptAllCookiesBtn()
                .isCookieBannerHide()
                .navigateToSearchBookingPage()
                .selectGoingToByClick("Cox's Bazar")
                .clickOnSearchButtonForFailed();
        Assert.assertTrue(SearchBookingPage.isFromPlaceIsRequiredErrorMessageFound());
    }

    @Test(priority = 4)
    public void checkSearchTripWithValidDataShouldSucceed() throws InterruptedException {
        ServiceSelectionPage serviceSelectionPage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickAcceptAllCookiesBtn()
                .isCookieBannerHide()
                .navigateToSearchBookingPage()
                .selectLeavingFrom("Dhaka")
                .selectGoingTo("Cox's Bazar")
                .selectDate(1)
                .clickOnSearchButtonForSuccess();
        Assert.assertEquals(serviceSelectionPage.getPageTitle(), MultimodalUserUtil.BUS_TRIP_LIST_PAGE_TITLE);
    }
}
