package org.patakig.studentplacement.parser;

import org.patakig.studentplacement.domain.SchoolScore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SchoolScoreParserTest {
    @Test
    public void testValid() {
        SchoolScore actual = SchoolScoreParser.fromString(" Elte : 100 ");
        SchoolScore expected = new SchoolScore("Elte", 100);
        assertEquals(expected, actual);
    }

    @Test(expected = ParseException.class)
    public void testInvalidNumber() {
        SchoolScoreParser.fromString(" Elte : 10a0 ");
    }

    @Test(expected = ParseException.class)
    public void tooManyColons() {
        SchoolScoreParser.fromString(" Elte : 10a0 :");
    }

    @Test(expected = ParseException.class)
    public void missingScore() {
        SchoolScoreParser.fromString("BME:");
    }
}