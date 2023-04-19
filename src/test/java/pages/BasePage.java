package pages;

import Reporting.ExtentTestManager;
import Utility.ConnectToPostgresDB;
import Utility.LoadConfig;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestResult;
import org.testng.annotations.*;



import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class BasePage {

    public WebDriver driver = null;
    public WebDriverWait wait = null;
    public ITestResult result = null;
    public ExtentReports extent = new ExtentReports();
    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMddHHmmssZ");
    public LocalDateTime now = LocalDateTime.now();
    public LoadConfig loadConfig = new LoadConfig();
    public ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir")+"\\Report\\spark.html");
    ConnectToPostgresDB connectToPostgresDB = new ConnectToPostgresDB();


@Test
    public void launchBrowser() throws MalformedURLException {
        String useCloudEnv = "false";
        String envName = null;
        String os = null;
        String os_version = null;
        String browserName = null;
        String browserVersion = null;
        String url = null;
        String browserstack_username = null;
        String browserstack_accesskey = null;
        String saucelabs_username = null;
        String saucelabs_accesskey = null;

            //parsing the  value of string
            useCloudEnv = loadConfig.loadProperties().getProperty("useCloudEnv");
            envName = loadConfig.loadProperties().getProperty("envName");
            os = loadConfig.loadProperties().getProperty("os");
            os_version = loadConfig.loadProperties().getProperty("os_version");
            browserName = loadConfig.loadProperties().getProperty("browserName");
            browserVersion = loadConfig.loadProperties().getProperty("browserVersion");
            url = loadConfig.loadProperties().getProperty("url");
            browserstack_username = loadConfig.loadProperties().getProperty("browserstack_username");
            browserstack_accesskey = loadConfig.loadProperties().getProperty("browserstack_accesskey");
            saucelabs_username = connectToPostgresDB.readFromDB("credentials","username").toString();
            saucelabs_accesskey = connectToPostgresDB.readFromDB("credentials","accesskey").toString();

            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("MyReport");
            extent.attachReporter(spark);//can add other reports like avant ..
            if (useCloudEnv.equalsIgnoreCase("true")) {
                if (envName == "browserstack") {
                    this.driver = getCloudDriver(envName, browserstack_username, browserstack_accesskey, os, os_version,
                            browserName, browserVersion);
                } else if (envName.equalsIgnoreCase("saucelabs")) {
                    System.out.println("Using Sauce Labs");
                    this.driver = getCloudDriver(envName, saucelabs_username, saucelabs_accesskey, os, os_version,
                            browserName, browserVersion);
                }
            }
            else if (useCloudEnv.equalsIgnoreCase("false")) {
                System.out.println("Local Driver ");
                driver = getLocalDriver();
            }

        this.driver.get(url);
        this.driver.manage().window().maximize();
        System.out.println("access to : " + url);
    }

    public WebDriver getLocalDriver() {
        WebDriverManager.edgedriver();

        driver = new EdgeDriver();
        driver.manage().window().maximize();

        return driver;
    }

    public WebDriver getCloudDriver(String envName, String envUsername, String envAccessKey, String os, String os_version, String browserName,
                                    String browserVersion) throws MalformedURLException {
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 11");
        browserOptions.setBrowserVersion("latest");
        DesiredCapabilities sauceOptions = new DesiredCapabilities();
        sauceOptions.setCapability("build", "selenium-build-UE2WO");
        sauceOptions.setCapability("name", "Gana");
        browserOptions.setCapability("sauce:options", sauceOptions);
        URL url = new URL("https://oauth-autolazare-558fb:f8d0a4b4-e059-4b39-a9eb-366965b887b1@ondemand.us-west-1.saucelabs.com:443/wd/hub");
        RemoteWebDriver driver = new RemoteWebDriver(url, browserOptions);


        return driver;
    }
    //use different methods to launch browser when broser is edge or chrome ect

    public ExtentTest test;

    public void getPageTitle() {
        test = extent.createTest("Verify page Title").assignAuthor("Gana")
                .assignCategory("Smoke Test")
                .assignDevice("EdgeBrowser");
        String title = driver.getTitle();


        if (title == "Delta") {
            test.pass("Test Pass");
            captureScreenShots(driver,result.getTestName());
        }
        else test.fail("Test Title Fail");
//          extent.flush();
////        driver.quit();
    }

    @AfterTest
    public void tearDown() {

        extent.flush();
        driver.quit();

    }


    public void afterEachTestMethod(ITestResult result) {
        try {
            for (String group : result.getMethod().getGroups()) {
                ExtentTestManager.getTest().assignCategory(group);
            }
            if (result.getStatus() == 1) {
                ExtentTestManager.getTest().log(Status.PASS, "Test Pass");

            } else if (result.getStatus() == 2) {
                ExtentTestManager.getTest().log(Status.FAIL, "Test Fail");
            } else if (result.getStatus() == 3) {
                ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
            }
        } catch (Exception e) {
        }
        extent.flush();
    }

    public void captureScreenShots(WebDriver driver, String screenShotName) {
        DateFormat df = new SimpleDateFormat("yyMMddHHmmssZ");
        Date date = new Date();
        df.format(date);

        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,
                    new File(System.getProperty("user.dir") + "/Screenshots/" + screenShotName
                            + "_" + df.format(date) + ".png"));
            System.out.println("ScreenShot Captured");
        } catch (Exception e) {
            System.out.println("Exception While taking Screenshot" + e.getMessage());
        }
    }

    public void js_click(WebElement element) {
        try {
            //add the wait time in properties file
            wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            if (element.isDisplayed()) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeAsyncScript("arguments[0].click();", element);
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(element));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


}
