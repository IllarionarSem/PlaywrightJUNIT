package tests;

import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import page.modules.Header;
import page.modules.LoginForm;
import page.modules.MessageForm;
import util.ConfigReader;

@Slf4j
public class LoginTest extends BaseTest {

    @Test
    public void loginViaFormLoginTest() {
        log.info("Navigate to URL: " + ConfigReader.getValue("url"));
        page.navigate(ConfigReader.getValue("url"), new Page.NavigateOptions().setTimeout(10000));
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
    public void loginViaFormLoginTest2() {
        log.info("Navigate to URL: " + ConfigReader.getValue("url"));
        page.navigate(ConfigReader.getValue("url"), new Page.NavigateOptions().setTimeout(10000));
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
}
