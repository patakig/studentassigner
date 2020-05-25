package org.patakig.studentplacement.algorithm;

import org.patakig.studentplacement.domain.Application;
import org.patakig.studentplacement.domain.SchoolScore;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ApplicationsValidator {
    public static Set<Application> validateApplications(Collection<Application> applications) {
        Objects.requireNonNull(applications, "applications must not be null!");
        validateNamesInApplications(applications);

        Set<Application> ret = new HashSet<>();

        validatedDuplicatedStudentNamesInApplications(applications);
        for (Application a : applications) {
            requireNonNullApplication(a);

            validateSchoolScores(a);

            ret.add(a);
        }
        return ret;
    }

    private static void validateSchoolScores(Application a) {
        if (a.getSchoolScores() == null) {
            throw new IllegalArgumentException(String.format("SchoolScores is null application %s!", a));
        }

        Set<String> schools = new HashSet<>();
        for (SchoolScore ss : a.getSchoolScores()) {
            if (ss.getSchoolName() == null) {
                throw new IllegalArgumentException(String.format("SchoolName is null application %s!", a));
            }
            if (schools.contains(ss.getSchoolName())) {
                throw new IllegalArgumentException(String.format("SchoolName is duplicated application %s!", a));
            }
            schools.add(ss.getSchoolName());
        }
    }

    private static void validateNamesInApplications(Collection<Application> applications) {

        Set<String> students = new HashSet<>();
        for (Application a : applications) {
            requireNonNullApplication(a);

            if (a.getStudentName() == null) {
                throw new IllegalArgumentException(String.format("Student name '%s' is null in application!", a));
            }

            if (students.contains(a.getStudentName())) {
                throw new IllegalArgumentException(String.format("Student name '%s' has duplicated applications!", a));
            }
        }
    }

    private static void validatedDuplicatedStudentNamesInApplications(Collection<Application> applications) {
        Set<String> students = new HashSet<>();
        for (Application a : applications) {
            requireNonNullApplication(a);
            if (students.contains(a.getStudentName())) {
                throw new IllegalArgumentException(String.format("Student with name '%s' is duplicated in application '%s'!", a.getStudentName(), a));
            }
            students.add(a.getStudentName());
        }
    }

    private static  void requireNonNullApplication(Application a) {
        if (a == null) {
            throw new IllegalArgumentException("Application must not be null in application list!");
        }
    }
}