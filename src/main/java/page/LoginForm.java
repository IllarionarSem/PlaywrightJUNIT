package page;

import com.microsoft.playwright.Page;

public class LoginForm extends Form {

    private final String commonTxtLoc = "//input[@name='%s']";
    private final String submitBtnLoc = "//button[@type='submit']";

    public LoginForm(Page page) {
        super(page);
    }

    public void fillLoginAndSubmit(String username, String password) {
        page.fill(String.format(commonTxtLoc, "username"), username);
        page.fill(String.format(commonTxtLoc, "password"), password);
        page.click(submitBtnLoc);
    }
}
