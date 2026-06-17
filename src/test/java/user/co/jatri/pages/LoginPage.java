package user.co.jatri.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import user.co.jatri.utils.MultimodalUserUtil;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterUserMobileNumber(String mobileNumber) {
        MultimodalUserUtil.waitForDomStable(10000);
        WebElement mobileNumberInputField = getWebElement(By.cssSelector("#mobile"));
        mobileNumberInputField.clear();
        for (char c : mobileNumber.toCharArray()) {
            mobileNumberInputField.sendKeys(String.valueOf(c));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return goTo(LoginPage.class);
    }

    public boolean isRequiredFieldErrorMessageFound() {
        return getWebElementSize(By.cssSelector("li[role='status']")) > 0;
    }

    public boolean isInvalidBangladeshiMobileNumberErrorMessageFound() {
        return getWebElementSize(By.cssSelector(".mt-1.text-danger.text-xs.invalid-alert-text")) > 0;
    }

    public boolean isMobileNumberLessThanElevenDigitErrorMessageFound() {
        return getWebElementSize(By.cssSelector(".mt-1.text-danger.text-xs.invalid-alert-text")) > 0;
    }

    public boolean isMobileNumberGraterThanElevenDigitErrorMessageFound() {
        return getWebElementSize(By.cssSelector(".mt-1.text-danger.text-xs.invalid-alert-text")) > 0;
    }

    public LoginPage clickOnGetOtpButtonForFail() {
        clickElement(By.xpath("//span[normalize-space()='Get OTP']"));
        return goTo(LoginPage.class);
    }

    public LoginPage clickOnGetOtpButtonForSuccess() {
        clickElement(By.xpath("//span[normalize-space()='Get OTP']"));
        return goTo(LoginPage.class);
    }

    public LoginPage setOtp(String phoneNumber) {
        FindOtp findOtp = new FindOtp();
        String otp = null;

        for (int i = 0; i < 20; i++) {
            otp = findOtp.fetchMostRecentOtp(phoneNumber);
            System.out.println("Fetched OTP is: " + otp);
            if (otp != null && !otp.isEmpty()) break;
            MultimodalUserUtil.waitForDomStable(1000);
        }

        if (otp == null || otp.isEmpty()) {
            throw new RuntimeException("OTP not found for mobile number: " + phoneNumber);
        }

        MultimodalUserUtil.waitForDomStable(5000);

        getWebElement(By.xpath("//input[@id='otp']")).sendKeys(otp);
        System.out.println("OTP Code is: " + otp);
        return goTo(LoginPage.class);
    }

}
