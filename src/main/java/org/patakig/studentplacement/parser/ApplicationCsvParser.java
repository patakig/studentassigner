package org.patakig.studentplacement.parser;

import com.opencsv.CSVReader;
import org.patakig.studentplacement.domain.Application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ApplicationCsvParser {

    public List<Application> parseCsvFile(String fileName) {
        int lineNo = 0;
        try {
            List<Application> ret = new ArrayList<>();

            try (CSVReader csvReader = new CSVReader(new FileReader(fileName));) {
                String[] values;
                while ((values = csvReader.readNext()) != null) {
                    lineNo++;
                    if (values.length > 1) {
                        ret.add(parseApplication(values));
                    }
                }
            }
            return ret;
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException("Error parsing line: " + lineNo, ex);
        }
    }

    private Application parseApplication(String[] values) {
        Application ret = new Application(values[0].trim());

        for (int i = 1; i < values.length; i++) {
            ret.getSchoolScores().add(SchoolScoreParser.fromString(values[i]));
        }
        return ret;
    }
}