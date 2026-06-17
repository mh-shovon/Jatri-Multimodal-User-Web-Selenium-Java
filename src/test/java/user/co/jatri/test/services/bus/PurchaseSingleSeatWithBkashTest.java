package user.co.jatri.test.services.bus;

import org.testng.Assert;
import org.testng.annotations.Test;
import user.co.jatri.pages.service.bus.BookingSuccessOrFailedPage;
import user.co.jatri.pages.home.HomePage;
import user.co.jatri.test.base.BaseTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PurchaseSingleSeatWithBkashTest extends BaseTest {
    @Test(priority = 1)
    public void purchaseSingleSeatWithBkash() throws InterruptedException {
//        String bkashNumber = System.getenv("BKASH_NUMBER");
//        String otpCode = System.getenv("BKASH_OTP");
//        String pinCode = System.getenv("BKASH_PIN");
//
//        if (bkashNumber == null || otpCode == null || pinCode == null) {
//            System.out.println("Error: One or more environment variables (BKASH_NUMBER, BKASH_OTP, BKASH_PIN) are missing!");
//            Assert.fail("Test failed due to missing Environment Variables.");
//        }

        Properties prop = new Properties();
        String bkashNumber = "";
        String otpCode = "";
        String pinCode = "";

        try {
            String configFilePath = "src/test/resources/config.properties";
            FileInputStream fis = new FileInputStream(configFilePath);
            prop.load(fis);

            bkashNumber = prop.getProperty("BKASH_NUMBER");
            otpCode     = prop.getProperty("BKASH_OTP");
            pinCode     = prop.getProperty("BKASH_PIN");

        } catch (IOException e) {
            System.err.println("Could not load config.properties file: " + e.getMessage());
            Assert.fail("Test failed due to missing or unreadable config.properties file.");
        }

        if (bkashNumber == null || otpCode == null || pinCode == null) {
            System.err.println("Error: One or more properties (BKASH_NUMBER, BKASH_OTP, BKASH_PIN) are missing inside config.properties!");
            Assert.fail("Test failed due to missing properties data.");
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
