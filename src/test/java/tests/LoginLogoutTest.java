package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.SecurePage;
import utils.ColorHelper;
import utils.Credentials;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginLogoutTest extends BaseTest {

    private static ExtentReports extent;
    private ExtentTest test;
    private LoginPage loginPage;
    private SecurePage securePage;

    @BeforeAll
    static void setupReport() {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String reportPath = "target/ReportePlaywright_" + timeStamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Reporte Pruebas - The Internet Login/Logout");
        spark.config().setReportName("Automatización Playwright - Casos de Login y Logout");
        spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Ambiente", "Herokuapp - The Internet");
        extent.setSystemInfo("Browser", "Chromium");
        extent.setSystemInfo("Fecha de ejecución", LocalDateTime.now().toString());
    }

    @BeforeEach
    void setupTest(TestInfo testInfo) {
        page = context.newPage();
        page.navigate("https://the-internet.herokuapp.com/login");
        loginPage = new LoginPage(page);
        securePage = null;

        test = extent.createTest(testInfo.getDisplayName())
                .assignCategory("Tests de Login y Logout")
                .assignAuthor("Federico Andrés Danielo");
        test.info("Inicio del test: " + testInfo.getDisplayName());
    }

    @AfterEach
    void cleanupPage() {
        if (page != null && !page.isClosed()) {
            page.close();
        }
    }

    @AfterAll
    static void finishReport() {
        if (extent != null) {
            extent.flush();
            System.out.println("Reporte generado en: target/ReportePlaywright_*.html");
        }
    }

    private void attachScreenshot(String description, Status status) {
        try {
            byte[] bytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            String base64 = Base64.getEncoder().encodeToString(bytes);
            test.log(status, description,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
        } catch (Exception e) {
            test.warning("No se pudo capturar screenshot: " + e.getMessage());
        }
    }

    @Test
    void testCorrectLogin() {
        var cred = Credentials.valid();
        test.info("Login con credenciales válidas → Usuario: " + cred.username());
        loginPage.fill(LoginPage.USERNAME, cred.username())
                .fill(LoginPage.PASSWORD, cred.password())
                .clickLoginButton();
        securePage = new SecurePage(page);
        assertThat(page).hasURL("https://the-internet.herokuapp.com/secure");
        attachScreenshot("Redirección a /secure", Status.PASS);
        Locator flash = securePage.getSucessFlash();
        assertThat(flash).isVisible();
        assertThat(flash).containsText("You logged into a secure area!");
        assertThat(flash).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#5da423"));
        test.pass("Login correcto validado exitosamente");
        attachScreenshot("Mensaje de éxito visible", Status.PASS);
    }

    @Test
    void testIncorrectLoginWrongUser() {
        var cred = Credentials.invalidUsername();
        test.info("Usuario inválido: " + cred.username());
        loginPage.fill(LoginPage.USERNAME, cred.username())
                .fill(LoginPage.PASSWORD, cred.password())
                .clickLoginButton();
        Locator flash = loginPage.getLocatorFlash();
        assertThat(flash).isVisible();
        assertThat(flash).containsText("Your username is invalid!");
        assertThat(flash).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#c60f13"));
        test.pass("Mensaje de error por usuario inválido validado");
    }

    @Test
    void testIncorrectLoginWrongPassword() {
        var cred = Credentials.invalidPassword();
        test.info("Contraseña inválida para usuario válido");
        loginPage.fill(LoginPage.USERNAME, cred.username())
                .fill(LoginPage.PASSWORD, cred.password())
                .clickLoginButton();
        Locator flash = loginPage.getLocatorFlash();
        assertThat(flash).isVisible();
        assertThat(flash).containsText("Your password is invalid!");
        assertThat(flash).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#c60f13"));
        test.pass("Mensaje de error por contraseña inválida validado");
    }

    @Test
    void testIncorrectLoginWithoutUser() {
        var cred = Credentials.noUsername();
        test.info("Login sin nombre de usuario");
        loginPage.fill(LoginPage.USERNAME, cred.username())
                .fill(LoginPage.PASSWORD, cred.password())
                .clickLoginButton();
        Locator flash = loginPage.getLocatorFlash();
        assertThat(flash).isVisible();
        assertThat(flash).containsText("Your username is invalid!");
        assertThat(flash).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#c60f13"));
        test.pass("Error por usuario vacío validado");
    }

    @Test
    void testIncorrectLoginWithoutPassword() {
        var cred = Credentials.noPassword();
        test.info("Login sin contraseña");
        loginPage.fill(LoginPage.USERNAME, cred.username())
                .fill(LoginPage.PASSWORD, cred.password())
                .clickLoginButton();
        Locator flash = loginPage.getLocatorFlash();
        assertThat(flash).isVisible();
        assertThat(flash).containsText("Your password is invalid!");
        assertThat(flash).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#c60f13"));
        test.pass("Error por contraseña vacía validado");
    }

    @Test
    void testLoginWithoutCredentials() {
        test.info("Login con ambos campos vacíos");
        loginPage.clickLoginButton();
        Locator flash = loginPage.getLocatorFlash();
        assertThat(flash).isVisible();
        assertThat(flash).containsText("Your username is invalid!");
        assertThat(flash).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#c60f13"));
        test.pass("Error por campos vacíos validado");
    }

    @Test
    void testCorrectLogout() {
        test.info("Prueba completa: Login → Logout");
        var cred = Credentials.valid();
        loginPage.fill(LoginPage.USERNAME, cred.username())
                .fill(LoginPage.PASSWORD, cred.password())
                .clickLoginButton();
        securePage = new SecurePage(page);
        assertThat(page).hasURL("https://the-internet.herokuapp.com/secure");
        securePage.clickLogoutButton();
        assertThat(page).hasURL("https://the-internet.herokuapp.com/login");
        Locator flash = loginPage.getLocatorFlash();
        assertThat(flash).isVisible();
        assertThat(flash).containsText("You logged out of the secure area!");
        assertThat(flash).hasCSS("background-color", ColorHelper.convertHexToIntForCSS("#5da423"));

        test.pass("Logout correcto y mensaje de cierre de sesión validado");
        attachScreenshot("Logout completado", Status.PASS);
    }
}