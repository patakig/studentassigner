package org.patakig.studentplacement.domain;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class SchoolScore {
    private String schoolName;
    private int score;

    public SchoolScore(String schoolName, int score) {
        this.schoolName = schoolName;
        this.score = score;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolScore that = (SchoolScore) o;
        return score == that.score &&
                Objects.equal(schoolName, that.schoolName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(schoolName, score);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }
}
