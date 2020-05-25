package org.patakig.studentplacement.algorithm;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

public class ApplicationsValidatorTest {
    @Test
    public void acceptValidApplications() {
        ApplicationsValidator.validateApplications(ImmutableSet.of(ApplicationBuilder.withStudentName("good").withScore("school1", 1).build(), ApplicationBuilder.withStudentName("better").withScore("school2", 2).withScore("school3", 2).build()));
    }

    @Test(expected = NullPointerException.class)
    public void refuseNullApplication() {
        ApplicationsValidator.validateApplications(ImmutableSet.of(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseApplicationWithDuplicatedStudents() {
        ApplicationsValidator.validateApplications(ImmutableSet.of(ApplicationBuilder.withStudentName("istvan kiss").withScore("school1", 1).build(), ApplicationBuilder.withStudentName("istvan kiss").withScore("school2", 2).build()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseApplicationWithDuplicatedSchools() {
        ApplicationsValidator.validateApplications(ImmutableSet.of(ApplicationBuilder.withStudentName("istvan kiss").withScore("school1", 1).withScore("school1", 2).build()));
    }
}