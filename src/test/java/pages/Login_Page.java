package pages;

import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.sql.DriverManager;


public class Login_Page extends BasePage{
    //to Use only One Driver instance across all the testing
    // I use init Elements
    Login_Page login_page ;
    BasePage basePage = new BasePage();
   // WebDriver driver = basePage.driver;

    @BeforeMethod
    public void getInit(){
        login_page = PageFactory.initElements(driver,Login_Page.class);
        //this.driver = basePage.getDriver();
    }

    //login locator using findby
    @FindBy(how = How.ID,using = "login-modal-button")
    public WebElement login_Button_WebElement;

    //usernaem or skymile number
    @FindBy(how = How.XPATH,using = "//*[@id=\"userId\"]/div/div/input")
    WebElement username_WebElement;




    //Action
    public void clickOnLogInButton(){

        driver.findElement(By.id("login-modal-button")).click();
        //login_Button_WebElement.click();
        //js_click(login_Button_WebElement);
        //js_click(username_WebElement);

        try {

        }catch (Exception e){}
    }

//   public void loginPageAccessibilityTest(){
//
//        basePage.accessibilityReport(driver);
//   }
}
