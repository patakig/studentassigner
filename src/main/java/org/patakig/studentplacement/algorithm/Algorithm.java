package org.patakig.studentplacement.algorithm;

import org.patakig.studentplacement.domain.Application;
import org.patakig.studentplacement.domain.School;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Algorithm {
    Map<String, Set<String>> calculate(Collection<School> schools, Collection<Application> applications);
}
