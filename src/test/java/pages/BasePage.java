package pages;

import Reporting.ExtentTestManager;
import Reporting.ViolationsReporter;
import Utility.ConnectToPostgresDB;
import Utility.LoadConfig;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


import com.deque.html.axecore.extensions.WebDriverExtensions;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;

import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class BasePage {

    public WebDriver driver = null;
    public AxeBuilder axeBuilder = new AxeBuilder();
    public WebDriverWait wait = null;
    public ITestResult result = null;
    public ExtentReports extent = new ExtentReports();
    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMddHHmmssZ");
    public LocalDateTime now = LocalDateTime.now();
    public LoadConfig loadConfig = new LoadConfig();
   // public ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir")+"\\Report\\spark.html");
    ConnectToPostgresDB connectToPostgresDB = new ConnectToPostgresDB();




    public void launchBrowser() throws MalformedURLException {
        try {
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
            saucelabs_username = connectToPostgresDB.readFromDB("credentials", "username").toString();
            saucelabs_accesskey = connectToPostgresDB.readFromDB("credentials", "accesskey").toString();

//            spark.config().setTheme(Theme.DARK);
//            spark.config().setDocumentTitle("MyReport");
//            extent.attachReporter(spark);//can add other reports like avant ..
            if (useCloudEnv.equalsIgnoreCase("true")) {
                if (envName == "browserstack") {
                    this.driver = getCloudDriver(envName, browserstack_username, browserstack_accesskey, os, os_version,
                            browserName, browserVersion);
                } else if (envName.equalsIgnoreCase("saucelabs")) {
                    System.out.println("Using Sauce Labs");
                    this.driver = getCloudDriver(envName, saucelabs_username, saucelabs_accesskey, os, os_version,
                            browserName, browserVersion);
                }
            } else if (useCloudEnv.equalsIgnoreCase("false")) {
                System.out.println("Local Driver ");
                //driver = getLocalDriver();
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }

            driver.get(url);

            driver.manage().window().maximize();
            //axeBuilder below takes tramendous amount of time to analyse the webPage
            //Im not currently Sure of this is the right method to implement this
//        Results axeResults = axeBuilder.analyze(driver);
//        System.out.println(axeResults);
            // Assert.assertTrue(axeResults.violationFree());
            // axeBuilder.include(url);
            // List<AccessibilityViolation> violations = axeBuilder.ana;
            List<String> tags = Arrays.asList("wcag21aa");
            AxeBuilder builder = new AxeBuilder();
            builder.withTags(tags);
            System.out.println("We Are in WEbSite :   " + driver.getTitle());
            /*
            FOR SOME REASON THE BELOW METHOD IS NOT WORKING AND ACTUALLY TAKES a TIMEOUT AND NEVER
            ANALYZE THE PAGE I HAD TO REPLACE THAT WITH WebDriverExtentions.analyze()
            Results results = builder.analyze(driver);
            */

            Results results = WebDriverExtensions.analyze(driver);
            //**get all violations
            List<Rule> violations = results.getViolations();
            //List<Rule> passResults = results.getPasses();
            for (Rule i : violations) {
                //for (Rule i : passResults) {
               // System.out.println(i);
            }
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(0, results.getViolations().size());
           // Assert.assertEquals(results.getViolations().size(),);
            ViolationsReporter.buildCustomReport(results);
            //Assert.assertTrue(results.violationFree(), ViolationsReporter.buildCustomReport(results));
            softAssert.assertEquals(0, results.getViolations().size());

            driver.quit();
        }
        catch (Exception e){}
    }

    public WebDriver getLocalDriver() {
        WebDriverManager.edgedriver().setup();

        this.driver = new EdgeDriver();
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
            //test.pass("Test Pass");
            captureScreenShots(driver,result.getTestName());
        }
      //  else //test.fail("Test Title Fail");
//          extent.flush();
////        driver.quit();
    }



    public void tearDown() {

//        extent.flush();
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


    public static void readAccessibility(){
 try {
     Context context = new Context();

     System.out.println(System.getProperty("user.dir")+"\\src\\test\\java\\Reporting\\AccessibilityReport.txt");
     FileReader fileReader = new FileReader(System.getProperty("user.dir")+"\\src\\test\\java\\Reporting\\AccessibilityReport.txt");
     BufferedReader bufferedReader = new BufferedReader(fileReader);

     String txtContents = bufferedReader.readLine();

     context.setVariable("txtContents", txtContents);

     TemplateEngine templateEngine = new TemplateEngine();
     String html = templateEngine.process(System.getProperty("user.dir")+"\\src\\test\\java\\Reporting\\AllyReport.html"
             , context);

     FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+"\\src\\test\\java\\Reporting\\AllyReport.html");
     //Files.write(Paths.get(System.getProperty("user.dir")+"\\src\\test\\java\\Reporting\\AllyReport.html"), html.getBytes());
     fileWriter.write("<html>\n<head>\n<title>Accessibility Testing Report</title>\n</head>\n<body>\n");

     // read the input file line by line and write each line as an HTML paragraph to the output file
     String line;
     while ((line = bufferedReader.readLine()) != null) {
         fileWriter.write("<p>" + line + "</p>\n");
     }

     // write the HTML footer to the output file
     fileWriter.write("</body>\n</html>");

     // close the input and output streams
     bufferedReader.close();
     fileReader.close();
     fileWriter.close();

     System.out.println("Conversion complete!");

 }
 catch (Exception e){
 e.getMessage();
 e.getStackTrace();
 }
    }

    public static void main(String[] args) {
        readAccessibility();
    }


}
