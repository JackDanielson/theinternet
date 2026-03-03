package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;


public abstract class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected ExtentReports extent;
    protected Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)
                .setName("Ejecución completa - " + System.currentTimeMillis()));

    }

    @AfterAll
    static void closeBrowser() {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("target/trace-" + System.currentTimeMillis() + ".zip")));
        browser.close();
        playwright.close();
    }
}
