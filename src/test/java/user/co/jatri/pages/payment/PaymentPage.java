package user.co.jatri.pages.payment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import user.co.jatri.pages.service.bus.BookingSuccessOrFailedPage;
import user.co.jatri.pages.base.BasePage;

public class PaymentPage extends BasePage {
    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    final By bkashNumberField = By.xpath("//input[@id='WALLET']");
    final By confirmButton = By.xpath("//button[normalize-space()='Confirm']");

    final By otpField = By.xpath("//input[@id='OTP' or @type='text' or @type='password']");
    final By pinField = By.xpath("//input[@id='PIN' or @type='password']");

    public BookingSuccessOrFailedPage completeBkashPaymentProcess(String bkashNumber, String otpCode, String pinCode) {
        try {
            WebElement numberInput = wait.until(ExpectedConditions.visibilityOfElementLocated(bkashNumberField));
            numberInput.clear();
            numberInput.sendKeys(bkashNumber);
            System.out.println("bKash number entered.");
            Thread.sleep(2000);

            driver.findElement(confirmButton).click();
            System.out.println("Clicked Confirm after entering number.");
            Thread.sleep(2000);

            WebElement otpInput = wait.until(ExpectedConditions.visibilityOfElementLocated(otpField));
            otpInput.clear();
            otpInput.sendKeys(otpCode);
            System.out.println("OTP entered.");
            Thread.sleep(2000);

            driver.findElement(confirmButton).click();
            System.out.println("Clicked Confirm after entering OTP.");
            Thread.sleep(2000);

            WebElement pinInput = wait.until(ExpectedConditions.visibilityOfElementLocated(pinField));
            pinInput.clear();
            pinInput.sendKeys(pinCode);
            System.out.println("PIN entered.");
            Thread.sleep(2000);

            driver.findElement(confirmButton).click();
            System.out.println("Clicked Confirm after entering PIN. Waiting for success redirection...");
            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("Exception occurred during bKash payment process: " + e.getMessage());
        }
        return goTo(BookingSuccessOrFailedPage.class);
    }
}
