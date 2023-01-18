package UI;

import com.microsoft.playwright.*;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.FileUtils;
import org.junit.jupiter.api.*;
import util.ConfigReader;

import java.io.IOException;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    //     Shared between all tests in the class.
    protected Playwright playwright;
    protected Browser browser;
    // New instance for each test method.
    protected BrowserContext context;
    protected Page page;
    private TestInfo testInfo;

    @BeforeAll
    public void launchBrowser() {
        playwright = Playwright.create();
        browser = getBrowserType().launch(Option.launchOptions.get());
    }

    @AfterAll
    public void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    public void createContextAndPage(TestInfo testInfo) {
        context = browser.newContext(Option.contextOptions.get());
        context.tracing().start(Option.startTracingOptions.get());
        page = context.newPage();
        this.testInfo = testInfo;
    }

    @AfterEach
    public void closeContext() {
        context.tracing().stop(Option.getStopTracingOptions(testInfo.getDisplayName()));
        context.close();
    }

    private BrowserType getBrowserType() {
        return switch (ConfigReader.getValue("browserType")) {
            case "CHROME":
                yield playwright.chromium();
            case "FIREFOX":
                yield playwright.firefox();
            default:
                yield null;
        };
    }
}
