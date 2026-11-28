package com.automationexercise.base;

import com.automationexercise.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected final Logger log = LogManager.getLogger(this.getClass());

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String browser) {
        String selectedBrowser = (browser != null && !browser.isEmpty())
                ? browser
                : ConfigReader.get("browser");

        log.info("Launching browser: {}", selectedBrowser);
        DriverFactory.initDriver(selectedBrowser);
        DriverFactory.getDriver().get(ConfigReader.get("ui.base.url"));
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("Closing browser session.");
        DriverFactory.quitDriver();
    }
}