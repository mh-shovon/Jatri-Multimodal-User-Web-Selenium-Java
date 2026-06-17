package user.co.jatri.test.services.bus;

import org.testng.Assert;
import org.testng.annotations.Test;
import user.co.jatri.pages.home.HomePage;
import user.co.jatri.pages.payment.SelectingPaymentMethodPage;
import user.co.jatri.pages.service.bus.SeatViewPage;
import user.co.jatri.test.base.BaseTest;

public class SeatViewTest extends BaseTest {
    @Test(priority = 1)
    public void checkScrollAndSelectTrip() throws InterruptedException {
        SeatViewPage seatViewPage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickAcceptAllCookiesBtn()
                .isCookieBannerHide()
                .navigateToSearchBookingPage()
                .selectLeavingFrom("Dhaka")
                .selectGoingTo("Cox's Bazar")
                .selectDate(1)
                .clickOnSearchButtonForSuccess()
                .navigateToTripListPage()
                .selectTripByCompanyNameAndTripNumber("Jeddah Express(জেদ্দা এক্সপ্রেস)", "Trip For Automation-1")
//                .selectTripByCompanyNameAndTripNumber("Green Line", "319-Business-Kalabagan-2")
                .navigateToSeatViewPage();
        Assert.assertTrue(seatViewPage.isSelectedSeatPanelDisplayed());
    }

    @Test(priority = 2)
    public void selectSeatAndFillBookingDetailsAndContinue() throws InterruptedException {
        SelectingPaymentMethodPage selectingPaymentMethodPage = page.goTo(HomePage.class)
                .isCookieBannerDisplayed()
                .clickAcceptAllCookiesBtn()
                .isCookieBannerHide()
                .navigateToSearchBookingPage()
                .selectLeavingFrom("Dhaka")
                .selectGoingTo("Cox's Bazar")
                .selectDate(1)
                .clickOnSearchButtonForSuccess()
                .navigateToTripListPage()
                .selectTripByCompanyNameAndTripNumber("Jeddah Express(জেদ্দা এক্সপ্রেস)", "Trip For Automation-1")
//              .selectTripByCompanyNameAndTripNumber("Shohagh Paribahan (Pvt) Ltd", "700")
                .navigateToSeatViewPage()
                .selectSingleSeatFromSeatView()
                .fillBookingDetailsAndContinue("Gabtoli Counter-1", "Cox's Bazar Sadar Counter", "DT", "Test", "Male", "01983285059", "mhshovon.jatri@gmail.com");
//              .fillBookingDetailsAndContinue("ABDULLAHPUR", "KOLATOLI", "DT", "Test", "Male", "01983285059", "mhshovon.jatri@gmail.com");
        Assert.assertTrue(selectingPaymentMethodPage.isSelectingPaymentMethodPageDisplayed());
    }
}
