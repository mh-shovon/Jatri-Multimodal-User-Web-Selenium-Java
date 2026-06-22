package user.co.jatri.pages.search;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import user.co.jatri.pages.services.ServiceSelectionPage;
import user.co.jatri.pages.services.bus.TripListPage;
import user.co.jatri.pages.base.BasePage;
import user.co.jatri.utils.MultimodalUserUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SearchBookingPage extends BasePage {
    public SearchBookingPage(WebDriver driver) {
        super(driver);
    }

    public SearchBookingPage selectLeavingFrom(String leavingFrom) {
        MultimodalUserUtil.waitForDomStable(500);
        clickElement(By.xpath("//span[normalize-space()='Leaving From']"));
        getWebElement(By.xpath("//input[@id='search-from']")).sendKeys(leavingFrom);
        MultimodalUserUtil.waitForDomStable(2000);
        clickElement(By.cssSelector("div[class='flex items-center gap-x-2 px-3 py-2 hover:bg-[#EFF7FD] cursor-pointer']"));
//        clickElement(By.xpath("//*[@id=\"radix-vue-popover-content-1\"]/div[2]/ul[2]/li[1]/div"));
        return goTo(SearchBookingPage.class);
    }

    public SearchBookingPage selectGoingTo(String goingTo) {
        MultimodalUserUtil.waitForDomStable(3000);
        getWebElement(By.xpath("//input[@id='search-to']")).sendKeys(goingTo);
        MultimodalUserUtil.waitForDomStable(2000);
        clickElement(By.cssSelector("div[class='flex items-center gap-x-2 px-3 py-2 hover:bg-[#EFF7FD] cursor-pointer']"));
//        clickElement(By.xpath("//*[@id=\"radix-vue-popover-content-2\"]/div[2]/ul[2]/li[1]/div"));
        return goTo(SearchBookingPage.class);
    }

    public SearchBookingPage selectGoingToByClick(String goingTo) {
        MultimodalUserUtil.waitForDomStable(500);
        clickElement(By.xpath("//div[@id='to']"));
        MultimodalUserUtil.waitForDomStable(2000);
        getWebElement(By.xpath("//input[@id='search-to']")).sendKeys(goingTo);
        MultimodalUserUtil.waitForDomStable(2000);
        clickElement(By.cssSelector("div[class='flex items-center gap-x-2 px-3 py-2 hover:bg-[#EFF7FD] cursor-pointer']"));
//        clickElement(By.xpath("//*[@id=\"radix-vue-popover-content-2\"]/div[2]/ul[2]/li[1]/div"));
        return goTo(SearchBookingPage.class);
    }

    public SearchBookingPage selectDate(int daysToAdd) {
        LocalDate targetDate = LocalDate.now().plusDays(daysToAdd);
//
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMM yyyy", Locale.ENGLISH);
        MultimodalUserUtil.waitForDomStable(2000);
        String selectedDate = targetDate.format(formatter);
        System.out.println("Target Selected Date: " + selectedDate);
        clickElement(By.cssSelector("#date"));
        String nextDateSelector = "//div[@aria-label='" + selectedDate + "']";
        clickElement(By.xpath(nextDateSelector));
        System.out.println("Selected Date is: " + selectedDate);
        return goTo(SearchBookingPage.class);
    }

    public SearchBookingPage selectTraveller() {
        System.out.println("Select Traveller");
        return goTo(SearchBookingPage.class);
    }

    public boolean isSearchButtonDisplayed() {
        return getWebElementSize(By.xpath("//*[@id=\"__nuxt\"]/div/main/div/section[1]/div/div/div[4]/button")) > 0;
    }

    public SearchBookingPage clickOnSearchButtonForFailed() {
        MultimodalUserUtil.waitForDomStable(500);
        clickElement(By.xpath("//span[normalize-space()='Search']"));
        MultimodalUserUtil.waitForDomStable(500);
        return goTo(SearchBookingPage.class);
    }

    public ServiceSelectionPage clickOnSearchButtonForSuccess() {
        MultimodalUserUtil.waitForDomStable(500);
        clickElement(By.xpath("//span[normalize-space()='Search']"));
        MultimodalUserUtil.waitForDomStable(2000);
        return goTo(ServiceSelectionPage.class);
    }

    public boolean isFromPlaceAndDestinationPlaceIsRequiredErrorMessageFound() {
        boolean isFromPlaceRequired = getWebElementSize(By.xpath("//p[normalize-space()='From place is required']")) > 0;
        boolean isDestinationPlaceRequired = getWebElementSize(By.xpath("//p[normalize-space()='Destination place is required']")) > 0;

        return isFromPlaceRequired && isDestinationPlaceRequired;
    }

    public boolean isFromPlaceIsRequiredErrorMessageFound() {
        return getWebElementSize(By.xpath("//p[normalize-space()='From place is required']")) > 0;
    }

    public boolean isDestinationIsRequiredErrorMessageFound() {
        return getWebElementSize(By.xpath("//p[normalize-space()='Destination place is required']")) > 0;
    }

    public TripListPage navigateToTripListPage() {
        MultimodalUserUtil.waitForDomStable(500);
        return goTo(TripListPage.class);
    }
}
