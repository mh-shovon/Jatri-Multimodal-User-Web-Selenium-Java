package user.co.jatri.pages.service.bus;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import user.co.jatri.pages.base.BasePage;
import user.co.jatri.pages.payment.SelectingPaymentMethodPage;
import user.co.jatri.utils.MultimodalUserUtil;

import java.time.Duration;
import java.util.List;

public class SeatViewPage extends BasePage {
    public SeatViewPage(WebDriver driver) {
        super(driver);
    }

    public SeatViewPage navigateToSeatViewPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(MultimodalUserUtil.WAIT_TIME));
        wait.until(d -> isSelectedSeatPanelDisplayed());
        return goTo(SeatViewPage.class);
    }

    public boolean isSelectedSeatPanelDisplayed() {
        MultimodalUserUtil.waitForDomStable(5000);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(MultimodalUserUtil.WAIT_TIME));
            WebElement panel = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > main:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > section:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)"))
            );
            boolean isDisplayed = panel.isDisplayed();
            System.out.println("=== Selected Seat Panel isDisplayed: " + isDisplayed + " ===");
            return isDisplayed;
        } catch (TimeoutException e) {
            System.out.println("=== Selected Seat Panel NOT visible within timeout ===");
            return false;
        }
    }

    public SeatViewPage selectSingleSeatFromSeatView() {
        By ALL_SEATS     = By.cssSelector("div div div div div div div div div div div div button[class] svg[fill='none']");
        By BLOCKED_SEATS = By.cssSelector("div div div div div div div div div div div div button[disabled]");

        MultimodalUserUtil.waitForDomStable(2000);

        try {
            List<WebElement> allSeats     = driver.findElements(ALL_SEATS);
            List<WebElement> blockedSeats = driver.findElements(BLOCKED_SEATS);

            int totalSeats        = allSeats.size();
            int blockedSeatsCount = blockedSeats.size();

            System.out.println("Total seats: "        + totalSeats);
            System.out.println("Sold/Blocked seats: " + blockedSeatsCount);

            for (int i = 0; i < totalSeats; i++) {
                WebElement seat = allSeats.get(i);
                Thread.sleep(1000);

                WebElement parentButton = (WebElement) ((JavascriptExecutor) driver)
                        .executeScript("return arguments[0].closest('button');", seat);

                boolean isBlocked = parentButton != null && (parentButton.getAttribute("disabled") != null);

                if (!isBlocked) {
                    Thread.sleep(500);

                    if (parentButton != null) {
                        parentButton.click();
                    } else {
                        seat.click();
                    }

                    System.out.println("Seat " + (i + 1) + " selected.");
                    break;
                } else {
                    System.out.println("Seat " + (i + 1) + " is sold/blocked.");
                }
            }

        } catch (Exception e) {
            System.out.println("Exception in selectSingleSeatFromSeatView: " + e.getMessage());
        }
        return goTo(SeatViewPage.class);
    }

    public boolean isSelectedSeatNumberDisplayed() {
        MultimodalUserUtil.waitForDomStable(1000);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(MultimodalUserUtil.WAIT_TIME));
            WebElement selectedSeats = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("//div[@class='pointer-events-none']"))
            );
            boolean isDisplayed = selectedSeats.isDisplayed();
            System.out.println("=== Selected Seats are Displayed: " + isDisplayed + " ===");
            return isDisplayed;
        } catch (TimeoutException e) {
            System.out.println("=== Selected Seats are NOT visible within timeout ===");
            return false;
        }
    }

    final By boardingPointDropdown = By.xpath("(//select)[1]");
    final By droppingPointDropdown = By.xpath("(//select)[2]");

    final By firstNameInput    = By.xpath("//input[contains(@placeholder, 'Enter your first name')]");
    final By lastNameInput     = By.xpath("//input[contains(@placeholder, 'Enter your last name')]");
    final By maleGenderRadio   = By.xpath("//div[contains(text(), 'Male')] | //label[contains(., 'Male')]");
    final By femaleGenderRadio = By.xpath("//div[contains(text(), 'Female')] | //label[contains(., 'Female')]");
    final By mobileNumberInput = By.xpath("//input[contains(@placeholder, 'Enter mobile number')]");
    final By emailAddressInput = By.xpath("//input[contains(@placeholder, 'Enter your email address')]");
    final By continueButton    = By.xpath("//button[contains(., 'Continue')]");

    public SelectingPaymentMethodPage fillBookingDetailsAndContinue(String boarding, String dropping, String firstName, String lastName, String gender, String mobile, String email) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(boardingPointDropdown));
            Select boardingSelect = new Select(driver.findElement(boardingPointDropdown));
            boardingSelect.selectByVisibleText(boarding);
            Thread.sleep(1000);

            Select droppingSelect = new Select(driver.findElement(droppingPointDropdown));
            droppingSelect.selectByVisibleText(dropping);
            Thread.sleep(1000);

            WebElement fName = driver.findElement(firstNameInput);
            fName.clear();
            fName.sendKeys(firstName);

            WebElement lName = driver.findElement(lastNameInput);
            lName.clear();
            lName.sendKeys(lastName);
            Thread.sleep(500);

            if (gender.equalsIgnoreCase("Male")) {
                driver.findElement(maleGenderRadio).click();
            } else {
                driver.findElement(femaleGenderRadio).click();
            }
            Thread.sleep(500);

            WebElement mobileField = driver.findElement(mobileNumberInput);
            mobileField.clear();
            mobileField.sendKeys(mobile);

            WebElement emailField = driver.findElement(emailAddressInput);
            emailField.clear();
            emailField.sendKeys(email);
            Thread.sleep(1000);

            WebElement btnContinue = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            btnContinue.click();
            System.out.println("Form automation completed successfully using indexed dropdowns.");

        } catch (Exception e) {
            System.out.println("Exception in fillBookingDetailsAndContinue: " + e.getMessage());
        }
        return goTo(SelectingPaymentMethodPage.class);
    }
}
