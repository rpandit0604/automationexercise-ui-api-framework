package com.automationexercise.base;

import com.automationexercise.utils.ConfigReader;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initDriver(String browser) {
        String targetBrowser = (browser == null || browser.isBlank())
                ? ConfigReader.get("browser")
                : browser;
        targetBrowser = targetBrowser.trim().toLowerCase();

        boolean isHeadless = ConfigReader.getBoolean("headless");
        log.info("Initializing {} browser (headless={})", targetBrowser, isHeadless);

        WebDriver driver = switch (targetBrowser) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                if (isHeadless) {
                    options.addArguments("--headless=new");
                }
                options.addArguments(
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--window-size=1920,1080",
                        "--disable-notifications"
                );
                yield new ChromeDriver(options);
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (isHeadless) {
                    options.addArguments("-headless", "--width=1920", "--height=1080");
                }
                yield new FirefoxDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                if (isHeadless) {
                    options.addArguments("--headless=new");
                }
                options.addArguments(
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--window-size=1920,1080"
                );
                yield new EdgeDriver(options);
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + targetBrowser);
        };

        if (!isHeadless) {
            driver.manage().window().maximize();
        }
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
        driverThreadLocal.set(driver);
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            log.info("Browser session closed and ThreadLocal cleaned.");
        }
    }
}