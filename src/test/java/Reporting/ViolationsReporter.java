package Reporting;

import com.deque.html.axecore.results.CheckedNode;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ViolationsReporter {

    /**
     * Builds a custom report for violations and its nodes.
     * @param axeResult
     * @return
     */

    public static void createfileReport(String axeResult){
        try {
            File myObj = new File(System.getProperty("user.dir")+"\\src\\test\\java\\Reporting\\AccessibilityReport.txt");
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+"\\src\\test\\java\\Reporting\\AccessibilityReport.txt");

            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            fileWriter.write(axeResult);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static String buildCustomReport(final Results axeResult) {
        String path = System.getProperty("user.dir")+"";
        int ruleCounter = 1;
        int nodeCounter = 1;
        StringBuilder reportBuilder = new StringBuilder();
        for (Rule rule : axeResult.getViolations()) {
            reportBuilder.append("_________________________________________________________________________________\n")
                    .append(ruleCounter).append(") ").append(rule.getDescription()).append(".").append("\n")
                    .append(rule.getHelpUrl()).append("\n");
            for (CheckedNode checkedNode : rule.getNodes()) {
                reportBuilder.append(ruleCounter).append(".").append(nodeCounter).append(") ")
                        .append(checkedNode.getTarget()).append("\n")
                        .append(checkedNode.getFailureSummary()).append("\n\n");
                nodeCounter++;
            }
            ruleCounter++;
            nodeCounter = 1;
        }
createfileReport("Your Web Page Has the Following Number of Violations : "+axeResult.getViolations().size() + "\n"
        +reportBuilder.toString());
        return reportBuilder.toString();
    }


}
