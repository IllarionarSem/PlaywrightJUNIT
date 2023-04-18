package page.modules;

import com.microsoft.playwright.Page;

public class MessageForm extends BaseForm {
    public static final String LOGGED_INTO_MSG = "logged into";
    public static final String LOGGED_OUT_MSG = "logged out";

    private final String commonMessageLoc = "//div[@id='flash']";

    public MessageForm(Page page) {
        super(page);
    }

    public Boolean isMessageShown(String message) {
        return page.locator(commonMessageLoc,
                new Page.LocatorOptions().setHasText(message)).isVisible();
    }
}
