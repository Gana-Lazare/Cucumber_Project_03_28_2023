package Utility;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadCsvData {
    String csvFile = "path/to/csv/file.csv";
    String line = "";
    String csvDelimiter = ",";

    public void retrieveDataCSv() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                String[] data = line.split(csvDelimiter);

                // do something with the data
                System.out.println("Column 1: " + data[0] + ", Column 2: " + data[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
