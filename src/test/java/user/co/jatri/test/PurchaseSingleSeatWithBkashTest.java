package user.co.jatri.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import user.co.jatri.pages.BookingSuccessOrFailedPage;
import user.co.jatri.pages.HomePage;

public class PurchaseSingleSeatWithBkashTest extends BaseTest{
    @Test(priority = 1)
    public void purchaseSingleSeatWithBkash() throws InterruptedException {
        String bkashNumber = System.getenv("BKASH_NUMBER");
        String otpCode     = System.getenv("BKASH_OTP");
        String pinCode     = System.getenv("BKASH_PIN");

        if (bkashNumber == null || otpCode == null || pinCode == null) {
            System.out.println("Error: One or more environment variables (BKASH_NUMBER, BKASH_OTP, BKASH_PIN) are missing!");
            Assert.fail("Test failed due to missing Environment Variables.");
        }

        BookingSuccessOrFailedPage bookingSuccessOrFailedPage = page.goTo(HomePage.class)
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
//          .selectTripByCompanyNameAndTripNumber("Shohagh Paribahan (Pvt) Ltd", "700")
                .navigateToSeatViewPage()
                .selectSingleSeatFromSeatView()
                .fillBookingDetailsAndContinue("Gabtoli Counter-1", "Cox's Bazar Sadar Counter", "DT", "Test", "Male", "01983285059", "mhshovon.jatri@gmail.com")
//          .fillBookingDetailsAndContinue("ABDULLAHPUR", "KOLATOLI", "DT", "Test", "Male", "01983285059", "mhshovon.jatri@gmail.com")
                .navigateToSelectingPaymentMethodPage()
                .selectBkashPaymentMethod()
                .clickOnProceedToPaymentButton()
                .completeBkashPaymentProcess(bkashNumber, otpCode, pinCode)
                .navigateToBookingSuccessOrFailedPage();

        Assert.assertTrue(bookingSuccessOrFailedPage.isBookingConfirmedMessageDisplayed(), "Booking success message was not displayed!");
    }
}
