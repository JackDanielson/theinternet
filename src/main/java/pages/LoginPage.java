package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    private final Locator locatorUsername;
    private final Locator locatorPassword;
    private final Locator locatorButton;
    private final Locator locatorFlash;

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public LoginPage(Page page) {
        this.page = page;
        this.locatorUsername =page.getByLabel("Username");
        this.locatorPassword = page.getByLabel("Password");
        this.locatorButton = page.getByLabel("Login");
        this.locatorFlash = page.locator("#flash");
    }

    public LoginPage fill(String inputText, String text){
        Locator locator = null;
        switch (inputText){
            case USERNAME:
                locator = locatorUsername;
                break;
            case PASSWORD:
                locator = locatorPassword;
                break;
        }
        locator.click();
        locator.clear();
        locator.fill(text);
        return this;
    }

    public LoginPage clickLoginButton(){
        locatorButton.click();
        return this;
    }

    public Locator getLocatorUsername() {
        return locatorUsername;
    }

    public Locator getLocatorPassword() {
        return locatorPassword;
    }

    public Locator getLocatorButton() {
        return locatorButton;
    }

    public Locator getLocatorFlash() {
        return locatorFlash;
    }
}
