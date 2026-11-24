package com.automationexercise.listeners;

import com.automationexercise.base.DriverFactory;
import com.automationexercise.utils.ExtentManager;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentManager.createTest(testName);
        log.info("===== STARTING TEST: {} =====", testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().pass("Test Passed");
        log.info("===== PASSED: {} =====", result.getMethod().getMethodName());
        ExtentManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.error("===== FAILED: {} =====", testName, result.getThrowable());

        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            ExtentManager.getTest().fail(result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot, testName).build());
        } else {
            ExtentManager.getTest().fail(result.getThrowable());
        }
        ExtentManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.getTest().skip("Test Skipped: " + result.getThrowable());
        log.warn("===== SKIPPED: {} =====", result.getMethod().getMethodName());
        ExtentManager.unload();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flushReports();
        log.info("ExtentReport flushed successfully.");
    }
}