package UI;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Tracing;

import java.nio.file.Paths;
import java.util.function.Supplier;

public class Option {

    static final Supplier<Tracing.StartOptions> startTracingOptions = () ->
            new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true);

    static Tracing.StopOptions getStopTracingOptions(String methodName) {
        methodName = methodName.replace("()", "");
        return new Tracing.StopOptions()
                .setPath(Paths.get(String.format("tracing/%s_trace.zip", methodName)));
    }

    static final Supplier<BrowserType.LaunchOptions> launchOptions = () ->
            new BrowserType.LaunchOptions()
                    .setSlowMo(1000)
                    .setHeadless(false)
                    .setDownloadsPath(Paths.get("downloads"));

    static final Supplier<Browser.NewContextOptions> contextOptions = () ->
            new Browser.NewContextOptions()
                    .setAcceptDownloads(true);
}
