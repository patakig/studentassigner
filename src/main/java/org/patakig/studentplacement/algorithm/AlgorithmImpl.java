package org.patakig.studentplacement.algorithm;

import org.patakig.studentplacement.domain.Application;
import org.patakig.studentplacement.domain.School;
import org.patakig.studentplacement.domain.SchoolRef;
import org.patakig.studentplacement.domain.SchoolScore;

import java.util.*;

public class AlgorithmImpl implements Algorithm {
    private Set<String> assignedStudentNames = new HashSet<>();
    private HashMap<String, SortedSet<SchoolRef>> schoolAssignments;
    private Map<String, School> schoolsByName;
    private Set<Application> applications;

    @Override
    public Map<String, Set<String>> calculate(Collection<School> schools, Collection<Application> applications) {
        assignedStudentNames.clear();

        validate(schools, applications);
        initializeEmptyResult();

        return executeCalculation();
    }

    private Map<String, Set<String>> executeCalculation() {

        int level = -1;
        boolean shallContinue = true;
        while (shallContinue && !schoolsByName.isEmpty()) {
            level++;
            for (String schoolName : schoolsByName.keySet()) {
                if (!checkIfAnyStudentsHasApplicationSizeGreaterThanLevel(level)) {
                    shallContinue = false;
                    break;
                }

                findAndAddApplicationInLevel(schoolName, level);
            }
        }
        return convertResult(schoolAssignments);
    }

    private void validate(Collection<School> schools, Collection<Application> applications) {
        schoolsByName = SchoolValidator.validateSchools(schools);
        this.applications = ApplicationsValidator.validateApplications(applications);
    }

    private Map<String, Set<String>> convertResult(HashMap<String, SortedSet<SchoolRef>> schoolAssignments) {
        Map<String, Set<String>> ret = new HashMap<>();

        for (String school : schoolAssignments.keySet()) {
            Set<String> students = new HashSet<String>();

            for (SchoolRef ai : schoolAssignments.get(school)) {
                students.add(ai.getApplication().getStudentName());
            }

            ret.put(school, students);
        }
        return ret;
    }

    private void assignStudentIfNeeded(String schoolName, SchoolRef schoolRef) {
        SortedSet<SchoolRef> assignedApplications = schoolAssignments.get(schoolName);

        boolean shouldAdd = true;
        if (schoolsByName.get(schoolName).getCapacity() > 0) {
            if (schoolsByName.get(schoolName).getCapacity() <= assignedApplications.size()) {
                if (schoolRef.getScore() > assignedApplications.first().getScore()) {
                    SchoolRef removedApplication = assignedApplications.first();
                    assignedApplications.remove(removedApplication);
                    assignedStudentNames.remove(removedApplication.getApplication().getStudentName());
                } else {
                    shouldAdd = false;
                }
            }
            if (shouldAdd) {
                assignedApplications.add(schoolRef);
                assignedStudentNames.add(schoolRef.getApplication().getStudentName());
            }
        }
    }

    private void initializeEmptyResult() {
        schoolAssignments = new HashMap<>();
        for (String school : schoolsByName.keySet()) {
            schoolAssignments.put(school, new TreeSet<>());
        }
    }

    private boolean checkIfAnyStudentsHasApplicationSizeGreaterThanLevel(int minimumSize) {
        for (Application a : applications) {
            if (!assignedStudentNames.contains(a.getStudentName())) {
                if (a.getSchoolScores().size() > minimumSize) {
                    return true;
                }
            }
        }

        return false;
    }

    private void findAndAddApplicationInLevel(String schoolName, int searchLevel) {
        for (Application a : applications) {
            if (!assignedStudentNames.contains(a.getStudentName())) {
                List<SchoolScore> ss = a.getSchoolScores();
                if (ss.size() > searchLevel && ss.get(searchLevel).getSchoolName().equals(schoolName)) {
                    assignStudentIfNeeded(schoolName, new SchoolRef(a, searchLevel));
                }
            }
        }
    }
}