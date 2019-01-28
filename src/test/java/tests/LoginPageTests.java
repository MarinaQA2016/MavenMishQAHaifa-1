package tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.EventsAuthPageHelper;
import pages.HomePageHelper;
import pages.LoginPageHelper;
import pages.MenuPageHelper;
import ru.stqa.selenium.factory.WebDriverPool;
import util.DataProviders;
import org.apache.log4j.Logger;
import util.LogLog4j;

/**
 * Created by Inka on 19-Dec-18.
 */
public class LoginPageTests extends TestBase {
    HomePageHelper homePage;
    LoginPageHelper loginPage;
    EventsAuthPageHelper eventsAuthPage;
    MenuPageHelper menuPage;
    private static Logger Log = Logger.getLogger(LogLog4j.class.getName());

    @BeforeMethod(alwaysRun = true)
    public void initPage(){
        homePage = PageFactory
                .initElements(driver, HomePageHelper.class);
        loginPage = PageFactory
                .initElements(driver, LoginPageHelper.class);
        eventsAuthPage = PageFactory.initElements(driver,
                EventsAuthPageHelper.class);
        menuPage = PageFactory
                .initElements(driver,MenuPageHelper.class);
        Log.info("--------LoginPage Tests -BeforeMethod was started---------");
        Log.info("--BeforeMethod: homePage is opened");
        homePage.waitUntilPageLoad()
                .pressLoginButton();
        Log.info("--BeforeMethod: loginPage is opened");
        loginPage.waitUntilPageLoad();

    }

    @Test(groups = {"sanity","regression"},dataProviderClass = DataProviders.class, dataProvider = "loginPositive")
    public void loginPositive(String email, String password)  {
        Log.info("--------Test loginPositive was started---------");
        Log.info("Parameter: email = " + email);
        Log.info("Parameter: password = " + password);
        Log.info("Test login Positive: loginPage was opened");
        loginPage.enterValueToFieldEmail(email)
                .enterValueToFieldPassword(password)
                .pressLogInButton();
        Log.info("Test login Positive: eventsAuthPage was opened");
        eventsAuthPage.waitUntilPageLoad();
        // -----------------Asserts-------------------------
        Log.info("Test login Positive: --Assert 'Menu' and tooltip of iconMenu");
        Assert.assertEquals("Menu", eventsAuthPage.getTooltipIconMenu());
        Log.info("Test login Positive: --Assert header of EventsPage");
        Assert.assertEquals("Find event",eventsAuthPage.getHeader());
        // -----------------Asserts-------------------------

        eventsAuthPage.menuButtonClick();
        Log.info("Test login Positive: menuPage was opened");
        menuPage.waitUntilPageLoad()
                .pressLogOutButton();
        Log.info("Test login Positive: homePage was opened");
        homePage.waitUntilPageLoad();
        // -----------------Asserts-------------------------
        Assert.assertEquals(homePage.getHeader(),
                "Shabbat in the family circle");

    }

    @Test( groups = {"regression","negative"},dataProviderClass = DataProviders.class, dataProvider = "loginNegative")
    public void loginNegativeNoSuchUser(String email, String password){
        Log.info("--------- Test loginNegativeNoSuchUser was started -----------");
        Log.info("Parameter - email: " + email);
        Log.info("Parameter - password: " + password);
        Log.info("Test loginNegativeNoSuchUser: loginPage was opened");
        loginPage.enterValueToFieldEmail(email)
                .enterValueToFieldPassword(password)
                .pressLogInButton();
        // -----------------Asserts-------------------------
        Log.info("Test login Positive: ---Assert alertText verification--- ");
       Assert.assertEquals("Wrong authorization, login or password",
               loginPage.getAlertText(),
               "alert 'Wrong authorization, login or password' wasn't given");
        // -----------------Asserts-------------------------
       loginPage.pressCancelButton()
               .waitUntilWindowIsClosed();
    }
    @Test(groups = {"regression","negative"},dataProviderClass = DataProviders.class,
            dataProvider = "loginNegativeIncorrectPassword")
    public void loginNegativePasswordIncorrect(String email, String password){
        Log.info("--------- Test loginNegativePasswordIncorrect was started -----------");
        Log.info("Test loginNegativePasswordIncorrect: loginPage was opened");
        loginPage.enterValueToFieldPassword(password)
                .enterValueToFieldEmail(email);
        // -----------------Asserts-------------------------
        Assert.assertEquals("Enter 6 characters",
                loginPage.getAlertPassword(),
                "AlertPassword text wasn't correct");
        // -----------------Asserts-------------------------
        loginPage.pressCancelButton()
                .waitUntilWindowIsClosed();
    }

    @Test(groups = {"regression","negative"},dataProviderClass = DataProviders.class,
            dataProvider = "loginNegativeIncorrectEmail")
    public void loginNegativeEmailIncorrect(String email, String password){
        Log.info("--------- Test loginNegativeEmailIncorrect was started -----------");
        Log.info("Test loginNegativeEmailIncorrect: loginPage was opened");
        loginPage.enterValueToFieldEmail(email)
                .enterValueToFieldPassword(password);
        // -----------------Asserts-------------------------
        Assert.assertEquals("Not a valid email",
                loginPage.getAlertEmail(),
                "AlertEmail text wasn't correct");
        // -----------------Asserts-------------------------
        loginPage.pressCancelButton()
                .waitUntilWindowIsClosed();
    }

    @Test(groups = {"regression","negative"})
    public void loginNegativeEmptyEmailPassword(){
        Log.info("--------- Test loginNegativeEmptyEmailPassword was started -----------");
        Log.info("Test loginNegativeEmptyEmailPassword: loginPage was opened");
        loginPage.enterValueToFieldEmail("")
                .enterValueToFieldPassword("")
                .enterValueToFieldEmail("");
        Assert.assertEquals(2,loginPage.getQuantityAlertsForEmptyFields());
    }

    @Test(groups = {"regression","negative"})
    public void loginNegativeOnlyEmailIsEmpty(){
        Log.info("--------- Test loginNegativeOnlyEmailIsEmpty was started -----------");
        Log.info("Test loginNegativeOnlyEmailIsEmpty: loginPage was opened");
        loginPage.enterValueToFieldEmail("")
                .enterValueToFieldPassword("567890fgd")
                .enterValueToFieldEmail("");
        Assert.assertEquals(1,loginPage.getQuantityAlertsForEmptyFields());
    }

    @Test(groups = {"regression","negative"})
    public void loginNegativeOnlyPasswordIsEmpty(){
        Log.info("--------- Test loginNegativeOnlyPasswordIsEmpty was started -----------");
        Log.info("Test loginNegativeOnlyPasswordIsEmpty: loginPage was opened");
        loginPage.enterValueToFieldPassword("")
                .enterValueToFieldEmail("test@mail.com")
                .enterValueToFieldPassword("");
        Assert.assertEquals(1,loginPage.getQuantityAlertsForEmptyFields());
    }




}
