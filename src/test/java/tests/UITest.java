package tests;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.MouseButton;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import page.modules.Header;
import page.modules.LoginForm;
import page.modules.MessageForm;
import util.ConfigReader;

@Slf4j
public class UITest extends BaseTest {

    @Test
    public void loginViaForm() {
        log.info("Navigate to URL: " + ConfigReader.getValue("url"));
        page.navigate(ConfigReader.getValue("url"));
        log.info("Fill username and password");
        new LoginForm(page).fillLoginAndSubmit(ConfigReader.getValue("username"), ConfigReader.getValue("password"));
        log.info("Check success message appears");
        MessageForm messageForm = new MessageForm(page);
        Assertions.assertTrue(messageForm.isMessageShown(MessageForm.LOGGED_INTO_MSG));
        log.info("Click Log Out");
        new Header(page).clickLogOut();
        log.info("Check success message appears");
        Assertions.assertTrue(messageForm.isMessageShown(MessageForm.LOGGED_OUT_MSG));
    }

    @Test
    public void testMouseClickAndDialogWindow() {
        String expectedDialogText = "You selected a context menu";
        Playwright playwright = Playwright.create();
        context = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(3000))
                .newContext();
        page = context.newPage();
        page.navigate("http://the-internet.herokuapp.com/context_menu");
        StringBuilder actTxt = new StringBuilder();
        page.onDialog(dialog -> {
            actTxt.append(dialog.message());
            dialog.accept();
        });
        page.locator("#hot-spot")
                .click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));
        Assertions.assertEquals(expectedDialogText, actTxt.toString());
    }
}
