package user.co.jatri.pages.payment;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import user.co.jatri.pages.base.BasePage;
import user.co.jatri.utils.MultimodalUserUtil;

public class SelectingPaymentMethodPage extends BasePage {

     public SelectingPaymentMethodPage(WebDriver driver) {
         super(driver);
     }

    public SelectingPaymentMethodPage navigateToSelectingPaymentMethodPage() {
         MultimodalUserUtil.waitForDomStable(2000);
         return goTo(SelectingPaymentMethodPage.class);
    }

     public boolean isSelectingPaymentMethodPageDisplayed() {
         By bkashLocator = By.xpath("//label[@for='bkash']");
         By sslcommerzLocator = By.xpath("//label[@for='sslcommerz']");

         try {
             boolean isBkashPresent = !driver.findElements(bkashLocator).isEmpty();
             boolean isSslPresent   = !driver.findElements(sslcommerzLocator).isEmpty();

             return isBkashPresent || isSslPresent;

         } catch (Exception e) {
             System.out.println("Exception while checking payment method elements: " + e.getMessage());
             return false;
         }
     }

    public SelectingPaymentMethodPage selectBkashPaymentMethod() {
        By bkashRadioLabel = By.xpath("//label[@for='bkash']");

        try {
            WebElement bkashBtn = wait.until(ExpectedConditions.elementToBeClickable(bkashRadioLabel));

            bkashBtn.click();
            System.out.println("bKash payment method selected successfully.");

            MultimodalUserUtil.waitForDomStable(1000);

        } catch (Exception e) {
            System.out.println("Exception in selectBkashPaymentMethod: " + e.getMessage());

            try {
                WebElement bkashBtn = driver.findElement(bkashRadioLabel);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bkashBtn);
                System.out.println("bKash payment method selected via JavaScript injection.");
            } catch (Exception ex) {
                System.out.println("Failed to click bKash even with JavaScript: " + ex.getMessage());
            }
        }
        return goTo(SelectingPaymentMethodPage.class);
    }

    public PaymentPage clickOnProceedToPaymentButton() {
        By proceedToPaymentButtonLocator = By.xpath("//span[normalize-space()='Proceeded to payment']");

        try {
            WebElement proceedToPaymentButton = wait.until(ExpectedConditions.elementToBeClickable(proceedToPaymentButtonLocator));
            proceedToPaymentButton.click();
            System.out.println("Continue button clicked successfully.");
        } catch (Exception e) {
            System.out.println("Exception in clickOnContinueButton: " + e.getMessage());

            try {
                WebElement continueBtn = driver.findElement(proceedToPaymentButtonLocator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);
                System.out.println("Continue button clicked via JavaScript injection.");
            } catch (Exception ex) {
                System.out.println("Failed to click Continue button even with JavaScript: " + ex.getMessage());
            }
        }
        return goTo(PaymentPage.class);
    }
}