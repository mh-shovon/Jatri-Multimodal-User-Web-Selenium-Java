package user.co.jatri.test.auth;

import org.testng.Assert;
import org.testng.annotations.Test;
import user.co.jatri.pages.home.HomePage;
import user.co.jatri.pages.auth.LoginPage;
import user.co.jatri.test.base.BaseTest;
import user.co.jatri.utils.MultimodalUserUtil;

public class LoginTest extends BaseTest {
    @Test(priority = 1)
    public void checkLoginPageTitle() throws InterruptedException {
        LoginPage loginPage = page.goTo(HomePage.class)
                .clickAcceptAllCookiesBtn()
                .clickOnLoginButton();
        Assert.assertEquals(loginPage.getPageTitle(), MultimodalUserUtil.LOGIN_PAGE_TITLE);
    }

    @Test(priority = 2)
    public void checkGetOtpWithoutMobileNumberShouldFail() throws InterruptedException {
        LoginPage loginPage = page.goTo(HomePage.class)
                .clickAcceptAllCookiesBtn()
                .clickOnLoginButton()
                .enterUserMobileNumber("")
                .clickOnGetOtpButtonForFail();
        Assert.assertTrue(loginPage.isRequiredFieldErrorMessageFound());
    }

    @Test(priority = 3)
    public void checkGetOtpWithInvalidBangladeshiMobileNumberShouldFail() throws InterruptedException {
        LoginPage loginPage = page.goTo(HomePage.class)
                .clickAcceptAllCookiesBtn()
                .clickOnLoginButton()
                .enterUserMobileNumber("1111111111")
                .clickOnGetOtpButtonForFail();
        Assert.assertTrue(loginPage.isInvalidBangladeshiMobileNumberErrorMessageFound());
    }

    @Test(priority = 4)
    public void checkGetOtpWithMobileNumberLessThanElevenDigitShouldFail() throws InterruptedException {
        LoginPage loginPage = page.goTo(HomePage.class)
                .clickAcceptAllCookiesBtn()
                .clickOnLoginButton()
                .enterUserMobileNumber("0190000000")
                .clickOnGetOtpButtonForFail();
        Assert.assertTrue(loginPage.isMobileNumberLessThanElevenDigitErrorMessageFound());
    }

    @Test(priority = 5)
    public void checkGetOtpWithMobileNumberGraterThanElevenDigitShouldFail() throws InterruptedException {
        LoginPage loginPage = page.goTo(HomePage.class)
                .clickAcceptAllCookiesBtn()
                .clickOnLoginButton()
                .enterUserMobileNumber("019000000000")
                .clickOnGetOtpButtonForFail();
        Assert.assertTrue(loginPage.isMobileNumberGraterThanElevenDigitErrorMessageFound());
    }

    @Test(priority = 6)
    public void checkGetOtpWithValidMobileNumberShouldSucceed() throws InterruptedException {
        String phoneNumber = getNewUserMobileNumber();
        LoginPage loginPage = page.goTo(HomePage.class)
                .clickAcceptAllCookiesBtn()
                .clickOnLoginButton()
                .enterUserMobileNumber(phoneNumber)
                .clickOnGetOtpButtonForSuccess()
                .setOtp(phoneNumber);
    }
}
