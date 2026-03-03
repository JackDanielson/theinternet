package tests;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.SecurePage;
import utils.ColorHelper;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Tests para la página de login en https://the-internet.herokuapp.com/login.
 * Cubre happy path y errores comunes.
 */
public class LoginLogoutTest extends BaseTest {

    private LoginPage loginPage;
    private SecurePage securePage;

    @BeforeEach
    void createPageAndNavigate() {
        page = context.newPage();
        page.navigate("https://the-internet.herokuapp.com/login");
        loginPage = new LoginPage(page);
        securePage = null;
    }

    @Test
    void testCorrectLogin() {
        loginPage.fill(LoginPage.USERNAME,"tomsmith")
                .fill(LoginPage.PASSWORD, "SuperSecretPassword!")
                .clickLoginButton();
        securePage = new SecurePage(page);
        assertThat(page).hasURL("https://the-internet.herokuapp.com/secure");
        Locator flash = loginPage.getLocatorFlash();
        assertThat(flash).isVisible();
        assertThat(flash).containsText("You logged into a secure area!");
        assertThat(flash).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#5da423"));
        assertThat(flash).isVisible();
    }

    @Test
    void testIncorrectLoginWrongUser() {
        loginPage.fill(LoginPage.USERNAME,"misteranderson")
                .fill(LoginPage.PASSWORD, "SuperSecretPassword!")
                .clickLoginButton();
        Locator flash = loginPage.getLocatorFlash();
        assertThat(flash).isVisible();
        assertThat(flash).containsText("Your username is invalid!");
        assertThat(flash).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#c60f13"));

    }

    @Test
    void testIncorrectLoginWrongPassword() {
        loginPage.fill(LoginPage.USERNAME,"tomsmith")
                .fill(LoginPage.PASSWORD, "thisisnotthepassword")
                .clickLoginButton();

        Locator flash = loginPage.getLocatorFlash();
        assertThat(flash).isVisible();
        assertThat(flash).containsText("Your password is invalid!");
        assertThat(flash).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#c60f13"));
    }

    @Test
    void testLoginWithoutCredentials() {
        loginPage.clickLoginButton();
        Locator flash = loginPage.getLocatorFlash();
        assertThat(flash).isVisible();
        assertThat(flash).containsText("Your username is invalid!");
        assertThat(flash).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#c60f13"));
    }

    @Test
    void testCorrectLogout() {
        testCorrectLogin();
        securePage.clickLogoutButton();
        assertThat(loginPage.getLocatorFlash()).isVisible();
        assertThat(loginPage.getLocatorFlash()).containsText("You logged out of the secure area!");
        assertThat(loginPage.getLocatorFlash()).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#5da423"));
    }

}