package org.patakig.studentplacement.domain;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private String studentName;
    private List<SchoolScore> schoolScores;

    public Application(String studentName) {
        this.studentName = studentName;
        schoolScores = new ArrayList<>();
    }

    public List<SchoolScore> getSchoolScores() {
        return schoolScores;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equal(studentName, that.studentName) &&
                Objects.equal(schoolScores, that.schoolScores);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentName, schoolScores);
    }

    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }
}
