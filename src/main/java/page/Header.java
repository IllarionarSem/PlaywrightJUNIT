package page;

import com.microsoft.playwright.Page;

public class Header extends Form {
    public Header(Page page) {
        super(page);
    }

    private final String logoutBtnLoc = "//a/i[contains(text(),'Logout')]";

    public void clickLogOut() {
        page.click(logoutBtnLoc);
    }
}
