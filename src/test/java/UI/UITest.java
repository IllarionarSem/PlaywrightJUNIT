package UI;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import page.Header;
import page.LoginForm;
import page.MessageForm;
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
}
