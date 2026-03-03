import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import tests.LoginLogoutTest;

@Suite
@SelectClasses({
        LoginLogoutTest.class
})
public class LoginSuite {

}