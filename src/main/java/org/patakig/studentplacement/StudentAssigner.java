package org.patakig.studentplacement;

import org.patakig.studentplacement.algorithm.AlgorithmImpl;
import org.patakig.studentplacement.domain.Application;
import org.patakig.studentplacement.domain.School;
import org.patakig.studentplacement.parser.ApplicationCsvParser;
import org.patakig.studentplacement.parser.SchoolCsvParser;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentAssigner {
    public static void main(String[] args) {
        if (args.length == 2) {

            List<School> schools = new SchoolCsvParser().parseCsvFile(args[0]);
            List<Application> applications = new ApplicationCsvParser().parseCsvFile(args[1]);

            AlgorithmImpl alg = new AlgorithmImpl();
            Map<String, Set<String>> assignments = alg.calculate(schools, applications);

            displayAssignments(assignments);
        } else {
            System.err.println("Usage java -jar myname.jar schools.csv applications.csv");
            System.exit(1);
        }
    }

    private static void displayAssignments(Map<String, Set<String>> assignments) {
        for (String s : assignments.keySet()) {
            System.out.println(String.format("\nassignments for school: '%s'", s));
            for (String n : assignments.get(s)) {
                System.out.println(String.format("name: '%s'", n));
            }
        }
    }
}
