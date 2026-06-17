package user.co.jatri.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import user.co.jatri.pages.HomePage;
import user.co.jatri.pages.SeatViewPage;
import user.co.jatri.pages.TripListPage;

public class TripListTest extends BaseTest {
    @Test (priority = 1)
    public void checkServiceTabIsVisible() throws InterruptedException {
        TripListPage tripListPage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickAcceptAllCookiesBtn()
                .isCookieBannerHide()
                .navigateToSearchBookingPage()
                .selectLeavingFrom("Dhaka")
                .selectGoingTo("Cox's Bazar")
                .selectDate(1)
                .clickOnSearchButtonForSuccess()
                .navigateToTripListPage();
        Assert.assertTrue(tripListPage.isServiceTabVisible());
    }

    @Test (priority = 2)
    public void checkTripIsAvailable() throws InterruptedException {
        TripListPage tripListPage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickAcceptAllCookiesBtn()
                .isCookieBannerHide()
                .navigateToSearchBookingPage()
                .selectLeavingFrom("Dhaka")
                .selectGoingTo("Cox's Bazar")
                .selectDate(1)
                .clickOnSearchButtonForSuccess()
                .navigateToTripListPage();
        Assert.assertTrue(tripListPage.isTripAvailable());
    }

    @Test (priority = 3)
    public void checkTripIsNotAvailable() throws InterruptedException {
        TripListPage tripListPage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickAcceptAllCookiesBtn()
                .isCookieBannerHide()
                .navigateToSearchBookingPage()
                .selectLeavingFrom("Dhaka")
                .selectGoingTo("Cox's Bazar")
                .selectDate(40)
                .clickOnSearchButtonForSuccess()
                .navigateToTripListPage();
        Assert.assertTrue(tripListPage.isTripListEmpty());
    }
}
