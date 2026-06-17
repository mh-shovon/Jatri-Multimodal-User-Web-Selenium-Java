package user.co.jatri.test.base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import user.co.jatri.pages.base.BasePage;
import user.co.jatri.pages.base.Page;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class BaseTest {
    WebDriver driver;
    public Page page;
    final Properties properties;

    public BaseTest() {
        properties = new Properties();
        String projectPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
        System.out.println("Project Path: " + projectPath);
        try {
            FileInputStream fileInputStream = new FileInputStream(projectPath);
            properties.load(fileInputStream);
        } catch (java.io.FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeMethod
    public void setupBrowser() {
        String browserName = properties.getProperty("browserName");
        if (Objects.equals(browserName, "firefox")) {
            driver = new FirefoxDriver();
        } else if (Objects.equals(browserName, "firefox-headless")) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--headless");
            driver = new FirefoxDriver(firefoxOptions);
        } else if (Objects.equals(browserName, "chrome")) {
            driver = new ChromeDriver();
        } else if (Objects.equals(browserName, "chrome-headless")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            driver = new ChromeDriver(chromeOptions);
        } else if (Objects.equals(browserName, "edge")) {
            driver = new EdgeDriver();
        } else if (Objects.equals(browserName, "edge-headless")) {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--headless");
            driver = new EdgeDriver(edgeOptions);
        } else if (Objects.equals(browserName, "safari")) {
            driver = new SafariDriver();
        } else {
            throw new RuntimeException("Browser is not supported: " + browserName);
        }

        driver.manage().window().maximize();
        String devUrl  = properties.getProperty("devBaseUrl");
        String liveUrl = properties.getProperty("liveBaseUrl");
        if (devUrl != null && !devUrl.isBlank()) {
            driver.get(devUrl);
        } else if (liveUrl != null && !liveUrl.isBlank()) {
            driver.get(liveUrl);
        } else {
            throw new RuntimeException("No active base URL found in config. Please uncomment one.");
        }

        page = new BasePage(driver);
    }

    @AfterMethod
    public void closeBrowser(ITestResult result) {
        System.out.println("Test " + result.getName() + " - " + (result.isSuccess() ? "PASSED" : "FAILED"));
        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(result.getName());
        }
        driver.quit();
    }

    public String getNewUserMobileNumber() {
        return properties.getProperty("newUserMobileNumber");
    }

    public String getExistingUserMobileNumber() {
        return properties.getProperty("existingUserMobileNumber");
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public void takeScreenshot(String name) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String currentDir = System.getProperty("user.dir") + "/build/screenshots/";
            File destFile = new File(currentDir + name + "_" + System.currentTimeMillis() + ".png");
            destFile.getParentFile().mkdirs();
            FileUtils.copyFile(scrFile, destFile);
            System.out.println("Screenshot saved at: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screenshot: " + e.getMessage(), e);
        }
    }
}
