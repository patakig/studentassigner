package org.patakig.studentplacement.algorithm;

import org.patakig.studentplacement.domain.School;
import org.junit.Test;

import java.util.Arrays;

public class SchoolsValidatorTest {
    @Test
    public void acceptValidSchool() {
        SchoolValidator.validateSchools(Arrays.asList(new School("BME", 1), new School("KOZGAZ", 1)));
    }

    @Test(expected = NullPointerException.class)
    public void RefuseNullSchool() {
        SchoolValidator.validateSchools(Arrays.asList(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void RefuseSchoolWithInvalidCapacity() {
        SchoolValidator.validateSchools(Arrays.asList(new School("BME", -1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void RefuseDuplicatedSchoolNames() {
        SchoolValidator.validateSchools(Arrays.asList(new School("BME", 1), new School("KOZGAZ", 2), new School("BME", 3)));
    }
}