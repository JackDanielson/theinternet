package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SecurePage {

    private final Page page;
    private Locator sucessFlash;
    private Locator logoutButton;

    public SecurePage(Page page) {
        this.page = page;
        this.sucessFlash = page.locator("#flash.success");
        this.logoutButton = page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Logout").setExact(true));
    }

    public Locator getSucessFlash() {
        return sucessFlash;
    }

    public Locator getLogoutButton() {
        return logoutButton;
    }

    public void clickLogoutButton() {
        logoutButton.click();
    }
}
