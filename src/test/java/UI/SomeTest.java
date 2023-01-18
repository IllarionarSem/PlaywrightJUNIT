package UI;

import com.microsoft.playwright.Download;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Slf4j
public class SomeTest extends BaseTest {

    @Test
    public void download() {
        page.navigate("http://the-internet.herokuapp.com/download");
        Download download = page.waitForDownload(() -> page.click("//a[text()='some-file.txt']"));
        String downloadPath = download.path().toString();
        BufferedReader reader = new BufferedReader(new InputStreamReader(download.createReadStream()));
        log.info(reader.lines().collect(Collectors.joining()));
        download.saveAs(Paths.get("downloads/some-file.txt"));
        log.info(downloadPath);
    }

    @Test
    public void uploadFile() {
        page.navigate("http://the-internet.herokuapp.com/upload");
        page.locator("//input[@type='file']").first().setInputFiles(Paths.get("downloads/some-file.txt"));
        page.click("//input[@type='submit']");
    }
}
