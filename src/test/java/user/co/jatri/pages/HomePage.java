package user.co.jatri.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import user.co.jatri.utils.MultimodalUserUtil;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage isCookieBannerDisplayed() {
        getWebElement(By.cssSelector("div[role='dialog']"));
        return goTo(HomePage.class);
    }

    public HomePage isCookieBannerHide() {
        MultimodalUserUtil.waitForDomStable(500);
        boolean isHidden = getWebElements(By.cssSelector("div[role='dialog']")).isEmpty();
        return goTo(HomePage.class);
    }

    public HomePage clickAcceptAllCookiesBtn() {
        MultimodalUserUtil.waitForDomStable(1000);
        clickElement(By.xpath("//button[normalize-space()='Accept All']"));
        return goTo(HomePage.class);
    }

    public HomePage clickDeclineAllCookiesBtn() {
        MultimodalUserUtil.waitForDomStable(1000);
        clickElement(By.xpath("//button[normalize-space()='Decline']"));
        return goTo(HomePage.class);
    }

    final By busMenuLink = By.xpath("//a[@href='/bus']");
    public boolean isBusMenuVisibleAndSelected() {
        try {
            setWait(busMenuLink);
            WebElement busLink = getWebElement(busMenuLink);
            if (busLink != null && busLink.isDisplayed()) {
                String classAttribute = busLink.getAttribute("class");
                boolean isActive = classAttribute != null &&
                        (classAttribute.contains("active") || classAttribute.contains("selected"));

                addInfo("Bus menu display status: true | Active status: " + isActive);
                return isActive;
            }
        } catch (Exception e) {
            addFailInfo("Exception while checking Bus menu visibility and selection: " + e.getMessage());
            System.err.println("Exception in isBusMenuVisibleAndSelected: " + e.getMessage());
        }
        return false;
    }

    public SearchBookingPage navigateToSearchBookingPage() {
        MultimodalUserUtil.waitForDomStable(500);
        return goTo(SearchBookingPage.class);
    }

    public LoginPage clickOnLoginButton() throws InterruptedException {
        MultimodalUserUtil.waitForDomStable(500);
        Thread.sleep(5000);
        clickElement(By.xpath("//span[@class='hidden md:block !text-base']"));

        return goTo(LoginPage.class);
    }

}
