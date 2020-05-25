package org.patakig.studentplacement.parser;

import org.patakig.studentplacement.domain.SchoolScore;

public class SchoolScoreParser {
    public static SchoolScore fromString(String schoolScoreString) {
        SchoolScore ret;
        String[] schoolScoreSplitted = schoolScoreString.split(":");
        if (schoolScoreSplitted.length != 2) {
            throw new ParseException(String.format("Cannot interpret school and score: %s", schoolScoreString));
        }
        try {
            ret = new SchoolScore(schoolScoreSplitted[0].trim(), Integer.parseInt(schoolScoreSplitted[1].trim()));
        } catch (NumberFormatException ex) {
            throw new ParseException(String.format("Cannot score: %s", schoolScoreString));
        }

        return ret;
    }
}
