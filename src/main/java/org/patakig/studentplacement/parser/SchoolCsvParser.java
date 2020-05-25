package org.patakig.studentplacement.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.patakig.studentplacement.domain.School;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SchoolCsvParser {

    public List<School> parseCsvFile(String fileName) {
        int lineNo = 0;
        try {
            List<School> ret = new ArrayList<>();

            try (CSVReader csvReader = new CSVReader(new FileReader(fileName));) {
                String[] values;
                while ((values = csvReader.readNext()) != null) {
                    lineNo++;
                    if (values.length > 0) {
                        if (!values[0].isEmpty()) {
                            if (values.length == 2) {
                                try {
                                    ret.add(new School(values[0], Integer.parseInt(values[1].trim())));
                                } catch (NumberFormatException ex) {
                                    throw new CsvException("Cannot parse capacity in line: " + lineNo);
                                }
                            } else {
                                throw new CsvException("Too many values in line: " + lineNo);
                            }
                        }
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
}
