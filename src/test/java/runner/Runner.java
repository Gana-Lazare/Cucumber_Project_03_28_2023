package runner;


import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;




@CucumberOptions(
        features = {"src/test/java/features"}, //the path of the feature files
        glue = "stepDefinitions", //the path of the step definition files
        plugin = {"pretty",
                "json:target/cucumber-reports/cucumber.json",
                "html:target/cucumber-reports/index.html",
                "pretty:target/cucumber-reports/cucumber-pretty.txt",
                "usage:target/cucumber-reports/cucumber-usage.json",
                "junit:target/cucumber-reports/cucumber-results.xml",}, //to generate different types of reporting
        monochrome = true, //display the console output in a proper readable format
        dryRun = false, //to check the mapping is proper between feature file and step def file
        publish = true)
public class Runner extends AbstractTestNGCucumberTests {
}
