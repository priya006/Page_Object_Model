package Testsuite;


import browserStackSetup.Setup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import org.testng.annotations.Test;


import DataProvider.*;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BaseTests extends Setup {

    @Test(priority = 1)
    public void navigate_to_home_page() throws IOException, InterruptedException {
        Reporter.log("This test verifies the current selenium compatibility with TestNG by launching the chrome driver");
        WebElement element = driver.findElement(browserStackHomePage.getStarted);
        browserStackHomePage.veryHeader();
        takeScreenshot("/Users/pboopathi/Desktop/entirepage.png");
       String pathToSaveImage = "/Users/pboopathi/Desktop/webelementscreen.png";
        takeWebElementScreenShot(element,pathToSaveImage,driver);
        browserStackHomePage.clickOnGetStarted();


    }

    @Test(priority = 3)
    public void comparing_screenshots() throws IOException {
        browserStackSignUpPage.veryHeader();
        //Take userName Webelement screenshot
        WebElement userNameElement = driver.findElement(browserStackSignUpPage.userName);
        takeWebElementScreenShot(userNameElement,"/Users/pboopathi/Desktop/userNameExpectedimage.png",driver);

        //Take a particular WebElement Screenshot
        WebElement Webelement = driver.findElement(browserStackSignUpPage.userName);


        Screenshot webElementScreenshot = new AShot().takeScreenshot(driver, Webelement);

        // read the image to compare
        BufferedImage expectedImage = ImageIO.read(new File("/Users/pboopathi/Desktop/userNameExpectedimage.png"));
        BufferedImage actualImage = webElementScreenshot.getImage();

        ImageDiffer imageDiffer = new ImageDiffer();
        ImageDiff diff = imageDiffer.makeDiff(expectedImage,actualImage);

        if(diff.hasDiff()==true) {
            System.out.println("Images are different");
        }
        else
            System.out.println("Images are same");
    }



    @Test(priority = 2, dataProvider = "data-provider", dataProviderClass = DataProviderMethod.class)
  //  @Parameters({"name"})

    public void enter_userDetails(String email){

        //browserStackSignUpPage.veryHeader();
        browserStackSignUpPage.enterFullName("priya123");
        browserStackSignUpPage.enterBusinessEmail(email);
        browserStackSignUpPage.enterPasswrod("TestUserPassword");

    }





    @Test(priority = 4)
    public void highlight_Webelements() throws IOException {
        WebElement userNameWebElement = driver.findElement(browserStackSignUpPage.userName);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].style.border='2px solid red'",userNameWebElement);

        //Take screenshot
        takeScreenshot("/Users/pboopathi/Desktop/HighlightWebelement.png");

    }


    @Test(groups = {"demo"})
    public void get_title() throws IOException, InterruptedException {
        Reporter.log("This test verifies the current selenium compatibility with TestNG by launching the chrome driver");
        browserStackHomePage.clickOnGetStarted();
        SoftAssert softAssert = new SoftAssert();
        String OriginalTitle = driver.getTitle();
        softAssert.assertEquals(OriginalTitle,"Most Reliable App & Cross Browser Testing Platform | BrowserStack");
        softAssert.assertAll();
    }

//    @Test(priority = 5, dataProvider = "excel-data",  dataProviderClass = DataProviderForExcel.class)
//    //  @Parameters({"name"})
//
//    public void DataDrivenTesting(String name, String emailaddress) throws IOException {
//
//        //browserStackSignUpPage.veryHeader();
//        browserStackSignUpPage.enterFullName(name);
//        takeScreenshot("/Users/pboopathi/Desktop/newtest.png");
//        browserStackSignUpPage.enterBusinessEmail(emailaddress);
//        browserStackSignUpPage.enterPasswrod("TestUserPassword");
//
//    }




    public void takeWebElementScreenShot(WebElement element, String pathname, WebDriver driver) throws IOException {
        Screenshot screenshot  = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver,element);
        ImageIO.write(screenshot.getImage(), "PNG", new File(pathname));
    }




}
