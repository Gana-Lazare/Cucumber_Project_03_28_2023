package Reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.Reporter;

import java.io.File;

public class ExtentManager {
    private static ExtentReports extent;
    private static ExtentSparkReporter extentSparkReporter;
    private static ITestContext context;

    public synchronized static ExtentReports getInstance(){
        if(extent == null){
            File outputDirectory = new File(context.getOutputDirectory());
            File resultDirectory = new File(outputDirectory.getParentFile(),"html");
           // extent = new ExtentReports(System.getProperty("user.dir")+"/Extent-Report/ExtentReport.html", true);
            extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/Report/ExtentReport.html");
            Reporter.log("Extent Report Directory"+ resultDirectory, true);
            extentSparkReporter.config().setReportName("Gana Lazare");
            extentSparkReporter.config().setTheme(Theme.DARK);
            extentSparkReporter.config().setDocumentTitle("MyReport");
            try {
                extentSparkReporter.loadXMLConfig(new File(System.getProperty("user.dir")+ "/report-config.xml"));

            }
            catch (Exception e){

            }
        }
        return extent;
    }

    public static void setOutputDirectory(ITestContext context){
        ExtentManager.context = context;

    }
}
