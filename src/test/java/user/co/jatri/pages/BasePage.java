package user.co.jatri.pages;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import user.co.jatri.report.ReportTestManager;

import java.util.List;

public class BasePage extends Page {
    public BasePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getText(By selector) {
        try {
            addInfo("Selenium Webdriver going to find a WebElement with " + selector + " Locator");
            return getWebElement(selector).getText().trim();
        } catch (NoSuchElementException e) {
            addFailInfo("Selenium Webdriver is not found a Web Element with " + selector + " Locator");
            System.err.println("Element not found: " + selector);
        } catch (Exception e) {
            addFailInfo("Unexpected error in getWebElement with " + selector + " Locator: " + e.getMessage());
            System.err.println("Unexpected error in getWebElement: " + e.getMessage());
        }
        return null;
    }

    public WebElement getWebElement(By selector) {
        try {
            addInfo("Selenium Webdriver going to find a WebElement with " + selector + " Locator");
            return driver.findElement(selector);
        } catch (NoSuchElementException e) {
            addFailInfo("Selenium Webdriver is not found a Web Element with " + selector + " Locator");
            System.err.println("Element not found: " + selector);
        } catch (Exception e) {
            addFailInfo("Unexpected error in getWebElement with " + selector + " Locator: " + e.getMessage());
            System.err.println("Unexpected error in getWebElement: " + e.getMessage());
        }
        return null;
    }

    public List<WebElement> getWebElements(By selector) {
        try {
            addInfo("Selenium Webdriver going to find a WebElement with " + selector + " Locator");
            return driver.findElements(selector);
        } catch (NegativeArraySizeException e) {
            addFailInfo("Unexpected error in getWebElement with " + selector + " Locator: " + e.getMessage());
            System.err.println("Elements not found: " + selector);
        } catch (Exception e) {
            System.out.println("Unexpected error in getWebElements: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int getWebElementSize(By selector) {
        return getWebElements(selector).size();
    }

    @Override
    public Select getSelect(By selector) {
        addInfo("Selenium Webdriver going to create a Select object for WebElement with " + selector + " Locator");
        return new Select(getWebElement(selector));
    }

    @Override
    public void setWait(By selector) {
        try {
            addInfo("Selenium Webdriver going to wait for visibility of WebElement with " + selector + " Locator");
            wait.until(ExpectedConditions.visibilityOf(getWebElement(selector)));
            addInfo("Selenium Webdriver confirmed visibility of WebElement with " + selector + " Locator");
        } catch (NoSuchElementException e) {
            System.err.println("Element not clickable: " + selector);
        } catch (Exception e) {
            System.err.println("Unexpected error during Wait: " + e.getMessage());
        }
    }

    @Override
    public void clickElement(By selector) {
        try {
            addInfo("Selenium Webdriver going to click a WebElement with " + selector + " Locator");
            wait.until(ExpectedConditions.elementToBeClickable(getWebElement(selector))).click();
            addInfo("Selenium Webdriver clicked a WebElement with " + selector + " Locator");
        } catch (NoSuchElementException e) {
            addFailInfo("Selenium Webdriver is not able to click a Web Element with " + selector + " Locator");
            System.err.println("Element not clickable: " + selector);
        } catch (Exception e) {
            addFailInfo("Unexpected error during clicking with " + selector + " Locator: " + e.getMessage());
            System.err.println("Unexpected error during clicking: " + e.getMessage());
        }
    }

    public void addInfo(String message) {
        if (ReportTestManager.getTest() != null) {
            ReportTestManager.getTest().log(Status.INFO, message);
        }
    }

    public void addFailInfo(String message) {
        if (ReportTestManager.getTest() != null) {
            ReportTestManager.getTest().log(Status.FAIL, message);
        }
    }
}
