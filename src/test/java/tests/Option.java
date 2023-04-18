package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Tracing;
import util.ConfigReader;

import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Supplier;

public class Option {

    public static final Supplier<Tracing.StartOptions> startTracingOptions = () ->
            new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true);

    public static final Function<String ,Tracing.StopOptions> stopTracingOptions = methodName -> {
        methodName = methodName.replace("()", "");
        return new Tracing.StopOptions()
                .setPath(Paths.get(String.format("tracing/%s_trace.zip", methodName)));
    };

    public static final Supplier<BrowserType.LaunchOptions> launchOptions = () ->
            new BrowserType.LaunchOptions()
                    .setSlowMo(Integer.parseInt(ConfigReader.getValue("slowMo")))
                    .setHeadless(Boolean.getBoolean(ConfigReader.getValue("isHeadless")))
                    .setDownloadsPath(Paths.get("downloads"));

    public static final Supplier<Browser.NewContextOptions> contextOptions = () ->
            new Browser.NewContextOptions()
                    .setAcceptDownloads(true);
}
