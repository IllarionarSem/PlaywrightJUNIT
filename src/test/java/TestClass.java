import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestClass {

    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)).newPage();
            page.navigate("https://playwright.dev/java/docs/trace-viewer");
            page.click("");
        }
    }
}
