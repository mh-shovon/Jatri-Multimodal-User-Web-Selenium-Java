package user.co.jatri.pages.services.bus;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import user.co.jatri.pages.base.BasePage;
import user.co.jatri.utils.MultimodalUserUtil;

import java.time.Duration;
import java.util.List;

public class TripListPage extends BasePage {
    public TripListPage(WebDriver driver) {
        super(driver);
    }

    public boolean isServiceTabVisible() {
        try {
            return getWebElement(By.cssSelector(".flex.mb-5")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTripAvailable() {
        try {
            MultimodalUserUtil.waitForDomStable(2000);
            By tripCountLocator = By.xpath("//div[contains(normalize-space(), 'Trips:')]");
            setWait(tripCountLocator);
            if (getWebElement(tripCountLocator) != null) {
                String entirePageText = getWebElement(tripCountLocator).getText().trim();
                String targetLine = "";
                String[] lines = entirePageText.split("\n");
                for (String line : lines) {
                    if (line.contains("Trips:")) {
                        targetLine = line;
                        break;
                    }
                }
                System.out.println("=== Target Line Found: " + targetLine + " ===");
                if (!targetLine.isEmpty()) {
                    String countOnly = targetLine.replaceAll("[^0-9]", "");
                    if (!countOnly.isEmpty()) {
                        int tripCount = Integer.parseInt(countOnly);
                        System.out.println("=== Final Dynamic Trip Count: " + tripCount + " ===");
                        return tripCount > 0;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exception while parsing trip text: " + e.getMessage());
            return false;
        }
    }

    public boolean isTripListEmpty() {
        By noTripElement = By.cssSelector(".w-full.pt-14");
        System.out.println("=== Waiting for 'No Trips Found' layout to load (Max 20 seconds) ===");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(noTripElement));
            return getWebElement(noTripElement).isDisplayed();

        } catch (Exception e) {
            System.out.println("=== Notification element (.w-full.pt-14) did not appear within timeout ===");
            return false;
        }
    }

//    public SeatViewPage selectTripByCompanyNameAndTripNumber(String companyName, String tripNumber) {
//        MultimodalUserUtil.waitForDomStable(10000);
//
//        By tripNoLocator = By.cssSelector(
//                "body > div:nth-child(1) > div:nth-child(1) > main:nth-child(2) > " +
//                        "div:nth-child(1) > section:nth-child(2) > div:nth-child(1) > " +
//                        "div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > " +
//                        "div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > " +
//                        "div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > " +
//                        "div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > p:nth-child(2)"
//        );
//
//        By companyLocator = By.cssSelector(
//                "body > div:nth-child(1) > div:nth-child(1) > main:nth-child(2) > " +
//                        "div:nth-child(1) > section:nth-child(2) > div:nth-child(1) > " +
//                        "div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > " +
//                        "div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > " +
//                        "div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > " +
//                        "div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > h4:nth-child(1)"
//        );
//
//        try {
//            System.out.println("=== Searching for: " + companyName + " | Trip: " + tripNumber + " ===");
//
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
//            wait.until(ExpectedConditions.presenceOfElementLocated(tripNoLocator));
//
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("window.scrollTo(0, 0);");
//            Thread.sleep(500);
//
//            boolean isFound    = false;
//            int     maxSteps   = 50;
//            int     scrollStep = 300;
//
//            for (int step = 0; step < maxSteps; step++) {
//
//                List<WebElement> tripNos   = driver.findElements(tripNoLocator);
//                List<WebElement> companies = driver.findElements(companyLocator);
//
//                System.out.println("Step " + (step + 1)
//                        + " | TripNo elements: " + tripNos.size()
//                        + " | Company elements: " + companies.size());
//
//                // ── Trip No list loop করো, match হলে same index এর Select Bus click ──
//                for (int i = 0; i < tripNos.size(); i++) {
//                    try {
//                        String currentTrip    = tripNos.get(i).getText().trim();
//                        String currentCompany = i < companies.size()
//                                ? companies.get(i).getText().trim() : "";
//
//                        System.out.println("  [" + i + "] Company: " + currentCompany
//                                + " | Trip: " + currentTrip);
//
//                        boolean tripMatch    = currentTrip.equals(tripNumber);
//                        boolean companyMatch = currentCompany.contains(companyName);
//
//                        if (tripMatch && companyMatch) {
//                            System.out.println("=== Match Found at index: " + i
//                                    + " | " + currentCompany + " | " + currentTrip + " ===");
//
//                            // ── XPath index is 1-based ────────────────────────────
//                            int xpathIndex = i + 1;
//                            By selectBusLocator = By.xpath(
//                                    "(//span[contains(text(),'Select Bus')])[" + xpathIndex + "]");
//
//                            WebElement selectBusBtn = driver.findElement(selectBusLocator);
//
//                            js.executeScript(
//                                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
//                                    selectBusBtn);
//                            Thread.sleep(800);
//
//                            try {
//                                selectBusBtn.click();
//                                System.out.println("=== Successfully Clicked 'Select Bus' at index "
//                                        + xpathIndex + " ===");
//                            } catch (ElementClickInterceptedException e) {
//                                System.out.println(">>> Intercepted, using JS click...");
//                                js.executeScript("arguments[0].click();", selectBusBtn);
//                                System.out.println("=== JS click executed ===");
//                            }
//
//                            isFound = true;
//                            break;
//                        }
//
//                    } catch (StaleElementReferenceException ignored) {
//                        System.out.println(">>> StaleElement at index " + i);
//                    }
//                }
//
//                if (isFound) break;
//
//                // ── Page end check ────────────────────────────────────────────────
//                Object scrollHeightObj  = js.executeScript("return document.body.scrollHeight");
//                Object currentScrollObj = js.executeScript("return window.pageYOffset + window.innerHeight");
//
//                if (scrollHeightObj != null && currentScrollObj != null) {
//                    long scrollHeight  = ((Number) scrollHeightObj).longValue();
//                    long currentScroll = ((Number) currentScrollObj).longValue();
//                    if (currentScroll >= scrollHeight) {
//                        System.out.println("=== Reached page end. Trip not found ===");
//                        break;
//                    }
//                }
//
//                js.executeScript("window.scrollBy(0, " + scrollStep + ");");
//                Thread.sleep(400);
//            }
//
//            if (!isFound) {
//                System.out.println("=== Trip not found: " + companyName + " | " + tripNumber + " ===");
//            }
//
//        } catch (Exception e) {
//            System.out.println("Exception: " + e.getMessage());
//        }
//
//        return goTo(SeatViewPage.class);
//    }

    public SeatViewPage selectTripByCompanyNameAndTripNumber(String companyName, String tripNumber) {
        By cartLocator = By.cssSelector(
                "body > div:nth-child(1) > div:nth-child(2) > main:nth-child(2) > " +
                        "div:nth-child(1) > section:nth-child(2) > div:nth-child(1) > " +
                        "div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > " +
                        "div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1)"
        );
        System.out.println("=== Safe Check: Waiting for the first trip card to render before doing any action... ===");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.presenceOfElementLocated(cartLocator));
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartLocator));

            System.out.println("=== Page Data Loaded! Safe to start searching and scrolling ===");
        } catch (Exception e) {
            System.out.println("❌ Timeout: Trip cards did not load within 60 seconds. Page might be empty.");
            return goTo(SeatViewPage.class);
        }
        System.out.println("=== Searching for Trip -> Company: " + companyName + " | Trip No: " + tripNumber + " ===");

        boolean isTripFound = false;
        int maxScrollAttempts = 30;

        for (int attempt = 0; attempt < maxScrollAttempts; attempt++) {
            By allCartLocator = By.cssSelector(
                    "body > div:nth-child(1) > div:nth-child(2) > main:nth-child(2) > " +
                            "div:nth-child(1) > section:nth-child(2) > div:nth-child(1) > " +
                            "div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > " +
                            "div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div"
            );

            List<WebElement> allCarts = driver.findElements(allCartLocator);

            for (WebElement cart : allCarts) {
                try {
                    String uiCompanyName = cart.findElement(By.xpath(".//h4")).getText().trim();
                    String uiTripNumber = cart.findElement(By.xpath(".//p[contains(@class,'mt-0.5 truncate text-xs text-dark-shade2')]")).getText().trim();

                    if (uiCompanyName.contains(companyName) && uiTripNumber.equalsIgnoreCase(tripNumber)) {
                        System.out.println("🎯 Match Found! Company: " + uiCompanyName + " [" + uiTripNumber + "]");

                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", cart);
                        Thread.sleep(800);

                        WebElement selectBusBtn = cart.findElement(By.xpath(".//button[contains(., 'Select Bus') or .//span[contains(text(), 'Select Bus')]]"));
                        selectBusBtn.click();

                        System.out.println("Successfully Entered the Desired Trip!");
                        isTripFound = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            if (isTripFound) {
                break;
            }
            try {
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 600);");
                System.out.println("Trip not found in current view. Scrolling down... (Attempt " + (attempt + 1) + ")");
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        if (!isTripFound) {
            System.out.println("❌ Error: Requested Trip [" + companyName + " - " + tripNumber + "] was NOT found on this page!");
        }
        return goTo(SeatViewPage.class);
    }
}