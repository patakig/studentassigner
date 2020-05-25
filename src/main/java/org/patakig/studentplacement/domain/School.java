package org.patakig.studentplacement.domain;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class School  {
    private String name;
    private int capacity;

    public School(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return capacity == school.capacity &&
                Objects.equal(name, school.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, capacity);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }
}
