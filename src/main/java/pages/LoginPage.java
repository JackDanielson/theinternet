package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    private final Locator locatorUsername;
    private final Locator locatorPassword;
    private final Locator locatorButton;
    //private final Locator locatorMessage;

    public LoginPage(Page page) {
        this.page = page;
        this.locatorUsername =page.getByLabel("Username");
        this.locatorPassword = page.getByLabel("Password");
        this.locatorButton = page.getByLabel("Login");
    }



}
