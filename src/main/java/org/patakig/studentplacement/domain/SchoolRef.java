package org.patakig.studentplacement.domain;

public class SchoolRef implements Comparable<SchoolRef> {
    private Application application;
    private int schoolScoreIndex;

    public SchoolRef(Application application, int schoolScoreIndex) {
        this.application = application;
        this.schoolScoreIndex = schoolScoreIndex;
    }

    public int getScore() {
        return getSchoolScore().getScore();
    }

    public SchoolScore getSchoolScore() {
        return application.getSchoolScores().get(schoolScoreIndex);
    }

    public Application getApplication() {
        return application;
    }

    @Override
    public int compareTo(SchoolRef o) {
        if (o != null) {
            return Integer.compare(getScore(), o.getScore());
        }

        return 0;
    }
}
