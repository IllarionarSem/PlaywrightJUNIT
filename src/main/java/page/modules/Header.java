package page.modules;

import com.microsoft.playwright.Page;

public class Header extends BaseForm {

    private final String logoutBtnLoc = "//a/i[contains(text(),'Logout')]";

    public Header(Page page) {
        super(page);
    }

    public void clickLogOut() {
        page.click(logoutBtnLoc);
    }
}
