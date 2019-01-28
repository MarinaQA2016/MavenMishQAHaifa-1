package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public abstract class PageBase {

  protected WebDriver driver;

  public PageBase(WebDriver driver) {
    this.driver = driver;
  }

  public String getTitle() {
    return driver.getTitle();
  }

  public void goBackBrowserButton() {
    driver.navigate().back();
  }

  public void goForwardBrowserButton() {
    driver.navigate().forward();
  }

  public void reloadPage() {
    driver.navigate().refresh();
  }


  public void waitUntilElementIsLoaded(WebDriver driver,
                                       By locator, int time)
  {
    try {
      new WebDriverWait(driver, time).until(ExpectedConditions
              .presenceOfElementLocated(locator));
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }


  public void waitUntilElementIsLoaded(WebDriver driver,
                                       WebElement element, int time)
  {
    try {
      new WebDriverWait(driver, time).until(ExpectedConditions
              .visibilityOf(element));
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
  public void waitUntilAllElementsAreLoaded(WebDriver driver,
                                       List<WebElement> list, int time)
  {
    try {
      new WebDriverWait(driver, time).until(ExpectedConditions
              .invisibilityOfAllElements());
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public static ExpectedCondition<Boolean> absenceOfElementLocated(
           final By locator) {
    return new ExpectedCondition<Boolean>() {

      @Override
      public Boolean apply(WebDriver driver) {
        try {
          driver.findElement(locator).isDisplayed();
          return false;
        } catch (NoSuchElementException e) {
          return true;
        } catch (StaleElementReferenceException e) {
          return true;
        }
      }
      @Override
      public String toString() {
        return "element to not being present: " + locator;
      }
    };
  }
  public static ExpectedCondition<Boolean> absenceOfElement(
          final WebElement element) {
    return new ExpectedCondition<Boolean>() {

      @Override
      public Boolean apply(WebDriver driver) {
        try {
          element.getTagName();
          return false;
        } catch (NoSuchElementException e) {
          return true;
        } catch (StaleElementReferenceException e) {
          return true;
        }
      }
    };
  }


  public void waitUntilElementIsDisappeared(WebDriver driver,
                                       By locator, int time)
  {
    try {
      new WebDriverWait(driver, time).until(absenceOfElementLocated(locator));
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public void waitUntilElementIsAbsent(WebDriver driver,
                                            WebElement element, int time)
  {
    try {
      new WebDriverWait(driver, time).until(absenceOfElement(element));
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public void setValueToField(WebElement element, String value) {
    element.click();
    element.clear();
    element.sendKeys(value);
  }

  public static String latinDigitString(int length){
    String str = "";
    char ch;
    int number;
    Random gen = new Random();
    int i = 0;
    do {
      number = '0' + gen.nextInt('z' - '0' +1);
      if ((number <= '9') || (number >= 'A' && number <= 'Z') || (number >= 'a'))
      {
        str = str + (char)number;
      }
    }while(str.length()<length);
    return str;
  }
  public void moveMouseOverElement(WebElement element) {
    String javaScript = "var evObj = document.createEvent('MouseEvents');" +
            "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
            "arguments[0].dispatchEvent(evObj);";


    ((JavascriptExecutor) driver).executeScript(javaScript, element);
  }

  public boolean verifyElementIsPresent(WebElement element) {
    try {
      element.getTagName();
      return true;
    } catch (NoSuchElementException e) {
      //  Log.info("---------------------------------");
      //  Log.info("element " + element + " can not be found by  element.getTagName()");
      //   Log.info("---------------------------------");
      return false;
    }
  }

  public boolean verifyTextBoolean(WebElement element, String text) {
    //  Log.info("verifying that text from element " + element + " - ('" + element.getText() + "') - is equal to text '" + text + "'");
    return text.equals(element.getText());
  }


}
