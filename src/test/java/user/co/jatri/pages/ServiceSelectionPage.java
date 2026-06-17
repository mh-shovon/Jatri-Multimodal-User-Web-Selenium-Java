package user.co.jatri.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import user.co.jatri.utils.MultimodalUserUtil;

import java.time.Duration;

public class ServiceSelectionPage extends BasePage {
    public ServiceSelectionPage(WebDriver driver) {
        super(driver);
    }

    public TripListPage navigateToTripListPage() {
        By tripMainContainer = By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > main:nth-child(2) > div:nth-child(1) > section:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1)");
        System.out.println("=== Waiting for the specific trip container to load (Max 60 seconds) ===");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tripMainContainer));
            System.out.println("=== Target container loaded successfully! ===");
        } catch (Exception e) {
            System.out.println("=== Error: Element did not appear within 60 seconds! === " + e.getMessage());
        }
        return goTo(TripListPage.class);
    }

    public ServiceSelectionPage clickOnTheCarRentalTab() throws InterruptedException {
        Thread.sleep(3000);
        clickElement(By.xpath("//*[@id=\"__nuxt\"]/div/main/div/section[2]/div/div/div[1]/div/div[1]/button[2]"));
        Thread.sleep(3000);
        return goTo(ServiceSelectionPage.class);
    }
}
