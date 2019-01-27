package tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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

    }

    @Test(groups = {"sanity","regression"},dataProviderClass = DataProviders.class, dataProvider = "loginPositive")
    public void loginPositive(String email, String password)  {
        Log.info("--------Test loginPositive was started---------");
        Log.info("Parameter: email = " + email);
        Log.info("Parameter: password = " + password);
        Log.info("Test login Positive: homePage was opened");
        loginPage.waitUntilPageLoad()
                .enterValueToFieldEmail(email)
                .enterValueToFieldPassword(password)
                .pressLogInButton();
        eventsAuthPage.waitUntilPageLoad();

        Assert.assertEquals("Menu", eventsAuthPage.getTooltipIconMenu());
        Assert.assertEquals("Find event",eventsAuthPage.getHeader());
        //driver.quit();
        eventsAuthPage.menuButtonClick();
        menuPage.waitUntilPageLoad()
                .pressLogOutButton();
        homePage.waitUntilPageLoad();
        Assert.assertEquals(homePage.getHeader(),
                "Shabbat in the family circle");

    }

    @Test( groups = {"regression","negative"},dataProviderClass = DataProviders.class, dataProvider = "loginNegative")
    public void loginNegativeNoSuchUser(String email, String password){
        Log.info("--------- Test loginNegative was started -----------");
        Log.info("Parameter - email: " + email);
        Log.info("Parameter - password: " + password);
        Log.info("loginNegative: loginPage was opened");
        loginPage.waitUntilPageLoad()
                .enterValueToFieldEmail(email)
                .enterValueToFieldPassword(password)
                .pressLogInButton();
       Assert.assertEquals("Wrong authorization, login or password",
               loginPage.getAlertText(),
               "alert 'Wrong authorization, login or password' wasn't given");
       Log.info("loginNegative: loginPage was opened");
       loginPage.pressCancelButton()
               .waitUntilWindowIsClosed();
    }
    @Test(groups = {"regression","negative"},dataProviderClass = DataProviders.class,
            dataProvider = "loginNegativeIncorrectPassword")
    public void loginNegativePasswordIncorrect(String email, String password){
        loginPage.waitUntilPageLoad()
                .enterValueToFieldPassword(password)
                .enterValueToFieldEmail(email);
        Assert.assertEquals("Enter 6 characters",
                loginPage.getAlertPassword(),
                "AlertPassword text wasn't correct");
        loginPage.pressCancelButton()
                .waitUntilWindowIsClosed();
    }

    @Test(groups = {"regression","negative"},dataProviderClass = DataProviders.class,
            dataProvider = "loginNegativeIncorrectEmail")
    public void loginNegativeEmailIncorrect(String email, String password){
        loginPage.waitUntilPageLoad()
                .enterValueToFieldEmail(email)
                .enterValueToFieldPassword(password);
        Assert.assertEquals("Not a valid email",
                loginPage.getAlertEmail(),
                "AlertEmail text wasn't correct");
        loginPage.pressCancelButton()
                .waitUntilWindowIsClosed();
    }

    @Test(groups = {"regression","negative"})
    public void loginNegativeEmptyEmailPassword(){
        loginPage.waitUntilPageLoad()
                .enterValueToFieldEmail("")
                .enterValueToFieldPassword("")
                .enterValueToFieldEmail("");
        Assert.assertEquals(2,loginPage.getQuantityAlertsForEmptyFields());
    }

    @Test(groups = {"regression","negative"})
    public void loginNegativeOnlyEmailIsEmpty(){
        loginPage.waitUntilPageLoad()
                .enterValueToFieldEmail("")
                .enterValueToFieldPassword("567890fgd")
                .enterValueToFieldEmail("");
        Assert.assertEquals(1,loginPage.getQuantityAlertsForEmptyFields());
    }

    @Test(groups = {"regression","negative"})
    public void loginNegativeOnlyPasswordIsEmpty(){
        loginPage.waitUntilPageLoad()
                .enterValueToFieldPassword("")
                .enterValueToFieldEmail("test@mail.com")
                .enterValueToFieldPassword("");
        Assert.assertEquals(1,loginPage.getQuantityAlertsForEmptyFields());
    }




}
