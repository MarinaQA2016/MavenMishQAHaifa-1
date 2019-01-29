package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.log4j.Logger;
import util.LogLog4j;

import java.util.List;

/**
 * Created by Inka on 26-Dec-18.
 */
public class LoginPageHelper extends PageBase {
    @FindBy(xpath = "//span[contains(text(),'Cancel')]" )
    WebElement cancelButton;
    @FindBy(xpath = "//input[@formcontrolname='email']")
    WebElement emailField;
    @FindBy(xpath = "//input[@formcontrolname='password']")
    WebElement passwordField;
    @FindBy(xpath = "//span[contains(text(),'Log in')]")
    WebElement loginButton;
    @FindBy(xpath =
            "//div[@class='alert alert-danger ng-star-inserted']")
    WebElement alertText;
    @FindBy(xpath ="//*[contains(text(),'valid email')]")
    WebElement alertEmail;
    @FindBy(xpath ="//*[contains(text(),'Enter 6 characters')]")
    WebElement alertPassword;
    @FindBy(xpath = "//*[contains(text(),'This field is mandatory')]")
    List<WebElement> emptyAlertsList;


    private static Logger Log = Logger.getLogger(LogLog4j.class.getName());


    public LoginPageHelper(WebDriver driver) {
        super(driver);
    }

    public LoginPageHelper waitUntilPageLoad() {
        Log.info("LoginPageHelper: ---waitUntilPageLoad()---");
        Log.info("LoginPageHelper: wait until Cancel button was loaded");
        waitUntilElementIsLoaded(driver,
                cancelButton,
                40);
        Log.info("LoginPageHelper: wait until Email field  was loaded");
        waitUntilElementIsLoaded(driver,
                emailField,
                50);
        Log.info("LoginPageHelper: wait until Password field  was loaded");
        waitUntilElementIsLoaded(driver,
                passwordField,
                50);
        return this;
    }

    public LoginPageHelper enterValueToFieldEmail(String value) {
        Log.info("LoginPageHelper: ---enterValueToFieldEmail()---");
        Log.info("LoginPageHelper: wait until emailField is loaded ---");
        waitUntilElementIsLoaded(driver, emailField,40);
        Log.info("LoginPageHelper: was eneterd email: " + value);
        setValueToField(emailField,value);
        return this;
    }

    public LoginPageHelper enterValueToFieldPassword(String value) {
        Log.info("LoginPageHelper: ---enterValueToFieldPassword()---");
        Log.info("LoginPageHelper: wait until emailPasswor is loaded ---");
        waitUntilElementIsLoaded(driver, passwordField,40);
        Log.info("LoginPageHelper: was enetered password: " + value);
        setValueToField(passwordField,value);
        return this;
    }

    public LoginPageHelper pressLogInButton() {
        Log.info("LoginPageHelper: --- pressLogInButton() ---");
        Log.info("LoginPageHelper: wait until Login button is loaded");
        waitUntilElementIsLoaded(driver, loginButton, 40);
        Log.info("LoginPageHelper: click on Login button");
        loginButton.click();
        return this;
    }

    public String getAlertText() {
        Log.info("LoginPageHelper: --- getAlertText() ---");
        Log.info("LoginPageHelper: wait until Alert text is loaded");
        waitUntilElementIsLoaded(driver, alertText,40);
        Log.info("LoginPageHelper: return text of alertText ");
    return alertText.getText();

    }

    public LoginPageHelper pressCancelButton(){
        Log.info("LoginPageHelper: --- pressCancelButton() ---");
        Log.info("LoginPageHelper: click on Cancel button");
        cancelButton.click();
        return this;
    }

    public LoginPageHelper waitUntilWindowIsClosed(){
        Log.info("LoginPageHelper: --- waitUntilWindowIsClosed() ---");
        Log.info("LoginPageHelper: wait until Cancel button is absent");
        waitUntilElementIsAbsent(driver, cancelButton,40);
        return this;
    }

    public String getAlertEmail() {
        Log.info("LoginPageHelper: --- getAlertEmail() ---");
        Log.info("LoginPageHelper: wait until alertEmail button is absent");
        waitUntilElementIsLoaded(driver, alertEmail, 40);
        Log.info("LoginPageHelper: return text of alertEmail ");
        return alertEmail.getText();
    }

    public String getAlertPassword() {
        Log.info("LoginPageHelper: --- getAlertPassword() ---");
        Log.info("LoginPageHelper: wait until alertPassword button is absent");
        waitUntilElementIsLoaded(driver, alertPassword, 40);
        Log.info("LoginPageHelper: return text of alertPassword ");
        return alertPassword.getText();
    }

    public int getQuantityAlertsForEmptyFields(){
        Log.info("LoginPageHelper: --- getQuantityAlertsForEmptyFields() ---");
        Log.info("LoginPageHelper: wait until emptyAlertsList elements are loaded");
        waitUntilAllElementsAreLoaded(driver, emptyAlertsList,40);
        Log.info("LoginPageHelper: return size of emptyAlertsList");
        return emptyAlertsList.size();
    }
}
