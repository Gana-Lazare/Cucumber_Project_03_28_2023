package runner;


import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;




@CucumberOptions(
        features = {"src/test/java/features"}, //the path of the feature files
        glue = "stepDefinitions",
        tags = "@regression",
//        tags = "@tag1 or @tag2"  // Executes scenarios with either @tag1 or @tag2
//        tags = "@tag1 and not @tag2"  // Executes scenarios with @tag1 but not @tag2//the path of the step definition files
        plugin = {"pretty",
                "json:target/cucumber-reports/cucumber.json",
                "html:target/cucumber-reports/index.html",
                "pretty:target/cucumber-reports/cucumber-pretty.txt",
                "usage:target/cucumber-reports/cucumber-usage.json",
                "junit:target/cucumber-reports/cucumber-results.xml",}, //to generate different types of reporting
        monochrome = true, //display the console output in a proper readable format
        dryRun = false, //to check the mapping is proper between feature file and step def file
        publish = true)
// Run the command mvn test -Dcucumber.options="--tags @tag1" to execute from local specific tags
public class Runner extends AbstractTestNGCucumberTests {
}
