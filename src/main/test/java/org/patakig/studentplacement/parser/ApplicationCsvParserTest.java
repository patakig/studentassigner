package org.patakig.studentplacement.parser;

import org.patakig.studentplacement.domain.Application;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ApplicationCsvParserTest {
    private ApplicationCsvParser testSubject;

    @Before
    public void setUp() throws Exception {
        testSubject = new ApplicationCsvParser();
    }

    @Test
    public void parseValidFile() {
        File file = new File(getClass().getClassLoader().getResource("applications-valid.csv").getFile());
        List<Application> schools = testSubject.parseCsvFile(file.getPath());
        List<Application> expected = Arrays.asList(
                createApplication("Bela", "Elte:100", "BME:110"),
                createApplication("Juli", "Elte:100"));

        assertEquals(expected, schools);
    }

    @Test(expected = RuntimeException.class)
    public void parseMissingFile() {
        testSubject.parseCsvFile("missing.csv");
    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidFile() {
        File file = new File(getClass().getClassLoader().getResource("applications-invalid.csv").getFile());
        testSubject.parseCsvFile(file.getPath());
    }

    private Application createApplication(String studentName, String... schoolScores) {
        Application ret = new Application(studentName);
        for (String ss: schoolScores) {
            ret.getSchoolScores().add(SchoolScoreParser.fromString(ss));
        }

        return ret;
    }
}