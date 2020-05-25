package org.patakig.studentplacement.algorithm;

import org.patakig.studentplacement.domain.Application;
import org.patakig.studentplacement.domain.SchoolScore;

public class ApplicationBuilder {
    private Application application;

    public static ApplicationBuilder withStudentName(String studentName) {
        ApplicationBuilder ret = new ApplicationBuilder();
        ret.application = new Application(studentName);
        return ret;
    }

    public ApplicationBuilder withScore(String schoolName, int score) {
        application.getSchoolScores().add(new SchoolScore(schoolName, score));
        return this;
    }

    public Application build() {
        return application;
    }
}
