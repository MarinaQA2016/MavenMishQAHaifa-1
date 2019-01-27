package tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EventsUnAuthPageHelper;
import pages.HomePageHelper;
import org.apache.log4j.Logger;
import util.LogLog4j;


/**
 * Created by Inka on 16-Dec-18.
 */
public class HomePagesTests extends TestBase {
    HomePageHelper homePage;
    EventsUnAuthPageHelper eventsUnAuthPage;
    private static Logger Log = Logger.getLogger(LogLog4j.class.getName());

    @BeforeMethod
    public void initPage(){
        homePage = PageFactory
                .initElements(driver, HomePageHelper.class);
        eventsUnAuthPage = PageFactory
                .initElements(driver, EventsUnAuthPageHelper.class);
        Log.info("--------BeforeMethod was stareted-----");
        Log.info("Test openHomePage: wait until homePage is loaded");
        homePage.waitUntilPageLoad();


    }
    @Test(groups = {"sanity", "regression"})
    public void openHomePage()  {
        Log.info("--------Test openHomePage was stareted-----");
        Log.info("Test openHomePage: get name of GoToEventButton");
        String goToButtonName =
                homePage.getGoToEventButtonName();
        Log.info("Test openHomePage - Assert: verify that name " +
                "'Go to Event list' is equal to real name '" +
                goToButtonName + "'");

        Assert.assertEquals("Go to Event list",goToButtonName,
                "'Go to Event list' is not equal to real name of the button");
    }

    @Test(groups = {"regression"})
    public void goToEventsTest()  {
        homePage.pressGoToEventButton();
        eventsUnAuthPage.waitUntilPageLoad();
        Assert.assertEquals("Find event",eventsUnAuthPage.getHeader());
    }

}
