package org.patakig.studentplacement.algorithm;

import org.patakig.studentplacement.domain.School;

import java.util.*;

public class SchoolValidator {
    public static Map<String, School> validateSchools(Collection<School> schools) {
        Objects.requireNonNull(schools, "schools must not be null!");

        Map<String, School> ret = new HashMap<>();
        Set<String> schoolNames = new HashSet<>();
        for (School s : schools) {
            if (s == null) {
                throw new IllegalArgumentException("school must not be null in school list!");
            }

            if (s.getCapacity() < 0) {
                throw new IllegalArgumentException(String.format("School '%s' has less capacity than 0!", s));
            }

            if (schoolNames.contains(s.getName())) {
                throw new IllegalArgumentException(String.format("School '%s'is duplicated in input, please rename!", s));
            }
            schoolNames.add(s.getName());
            ret.put(s.getName(), s);
        }

        return ret;
    }
}