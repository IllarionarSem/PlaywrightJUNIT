import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.util.function.Supplier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

//     Shared between all tests in the class.
    protected Playwright playwright;
    protected Browser browser;

    @BeforeAll
    public void launchBrowser() {
        playwright = Playwright.create();
        String browserTypeName = "FIREFOX";
        BrowserType browserType = switch (browserTypeName) {
            case "CHROME":
                yield playwright.chromium();
            case "FIREFOX":
                yield playwright.firefox();
            default:
                yield null;
        };
        browser = browserType.launch(launchOptions.get());
    }

    private final Supplier<BrowserType.LaunchOptions> launchOptions = () ->
            new BrowserType.LaunchOptions()
                    .setSlowMo(1000)
                    .setHeadless(false);

    @AfterAll
    public void closeBrowser() {
        playwright.close();
    }

    // New instance for each test method.
    protected BrowserContext context;
    protected Page page;

    @BeforeEach
    public void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    public void closeContext() {
        context.close();
    }
}
