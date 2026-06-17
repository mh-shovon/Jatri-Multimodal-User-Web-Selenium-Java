package user.co.jatri.pages.services.bus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import user.co.jatri.pages.base.BasePage;
import user.co.jatri.utils.MultimodalUserUtil;

public class BookingSuccessOrFailedPage extends BasePage {
    public BookingSuccessOrFailedPage(WebDriver driver) {
        super(driver);
    }

    public BookingSuccessOrFailedPage navigateToBookingSuccessOrFailedPage() {
        MultimodalUserUtil.waitForDomStable(2000);
        return goTo(BookingSuccessOrFailedPage.class);
    }

    final By successMessage   = By.xpath("//h5[normalize-space()='Your Bus Ticket Booking is Confirmed']");
    public boolean isBookingConfirmedMessageDisplayed() {
        try {
            System.out.println("Checking for ticket confirmation message...");
            WebElement successMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));

            if (successMsgElement.isDisplayed()) {
                System.out.println("Test Passed: 'Your Bus Ticket Booking is Confirmed' message is displayed!");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Success message not found or timed out: " + e.getMessage());
        }
        return false;
    }
}
