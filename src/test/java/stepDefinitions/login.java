package stepDefinitions;

import io.cucumber.java.en.*;
import org.testng.annotations.Optional;
import pages.BasePage;
import pages.Login_Page;

import java.net.MalformedURLException;

public class login extends Login_Page {

    @Given("user navigate to the site")
    public void user_navigate_to_the_site() throws MalformedURLException {
        launchBrowser();
        clickOnLogInButton();
        getPageTitle();
        tearDown();

    }

}
