package org.patakig.studentplacement.parser;

import org.patakig.studentplacement.domain.School;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SchoolCsvParserTest {
    private SchoolCsvParser testSubject;

    @Before
    public void setUp() throws Exception {
        testSubject = new SchoolCsvParser();
    }

    @Test
    public void parseValidFile() {
        File file = new File(getClass().getClassLoader().getResource("schools-valid.csv").getFile());
        List<School> schools = testSubject.parseCsvFile(file.getPath());
        List<School> expected = Arrays.asList(new School("ANNYE", 20), new School("BGE", 10),new School("EKE", 80),new School("LFZE", 90));
        assertEquals(expected, schools);
    }

    @Test(expected = RuntimeException.class)
    public void parseMissingFile() {
        testSubject.parseCsvFile("missing.csv");
    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidFile() {
        File file = new File(getClass().getClassLoader().getResource("schools-invalid.csv").getFile());
        testSubject.parseCsvFile(file.getPath());
    }
}