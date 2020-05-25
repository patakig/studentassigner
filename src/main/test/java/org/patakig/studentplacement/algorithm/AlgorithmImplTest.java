package org.patakig.studentplacement.algorithm;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.patakig.studentplacement.domain.School;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AlgorithmImplTest {
    private AlgorithmImpl testSubject;

    @Before
    public void setUp() {
        testSubject = new AlgorithmImpl();
    }

    @Test
    public void emptySchoolsEmptyApplicationsShouldNotFail() {
        Map<School, Set<String>> result = testSubject.calculate(Collections.EMPTY_LIST, Collections.EMPTY_SET);
        assertTrue(result.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void nullSchoolsShouldThrowException() {
        testSubject.calculate(null, Collections.EMPTY_SET);
    }

    @Test(expected = NullPointerException.class)
    public void nullApplicationsShouldThrowException() {
        testSubject.calculate(Collections.EMPTY_LIST, null);
    }

    @Test
    public void shouldChooseBetterApplication() {
        final School BME = new School("BME", 1);
        final School KOZGAZ = new School("KOZGAZ", 1);
        Map<String, Set<String>> result = testSubject.calculate(ImmutableSet.of(BME, KOZGAZ),
                ImmutableSet.of(
                        ApplicationBuilder.withStudentName("good").withScore(BME.getName(), 1).withScore(KOZGAZ.getName(), 1).build(),
                        ApplicationBuilder.withStudentName("better").withScore(BME.getName(), 2).withScore(KOZGAZ.getName(), 1).build()));
        Map<String, Set<String>> expected = new HashMap<>();
        expected.put(BME.getName(), ImmutableSet.of("better"));
        expected.put(KOZGAZ.getName(), ImmutableSet.of("good"));

        assertThat(result, is(expected));
    }

    @Test
    public void shouldRefuseWhenCapacityExceeded() {
        final School U1 = new School("U1", 0);
        Map<String, Set<String>> result = testSubject.calculate(ImmutableSet.of(U1),
                ImmutableSet.of(
                        ApplicationBuilder.withStudentName("ST1").withScore(U1.getName(), 0).build()));
        Map<String, Set<String>> expected = new HashMap<>();
        expected.put(U1.getName(), Collections.EMPTY_SET);

        assertThat(result, is(expected));
    }

    @Test
    public void shouldAssignSameScoresToDifferentSchools() {
        final School BME = new School("BME", 1);
        final School KOZGAZ = new School("KOZGAZ", 1);
        Map<String, Set<String>> result = testSubject.calculate(ImmutableSet.of(BME, KOZGAZ),
                ImmutableSet.of(
                        ApplicationBuilder.withStudentName("student1").withScore(BME.getName(), 1).withScore(KOZGAZ.getName(), 1).build(),
                        ApplicationBuilder.withStudentName("student2").withScore(BME.getName(), 1).withScore(KOZGAZ.getName(), 1).build()));

        Set<String> allStudentNames = new HashSet<>(Arrays.asList("student1", "student2"));

        // Order is not known, but all students should have one school
        assertEquals(2, result.size());

        for (String schoolName : new String[]{"BME", "KOZGAZ"}) {
            assertTrue(result.containsKey(schoolName));
            assertEquals(1, result.get(schoolName).size());
            assertTrue(allStudentNames.remove(result.get(schoolName).iterator().next()));
        }
    }

    @Test
    public void shouldAssignStudentsInto3Levels() {
        final School U1 = new School("U1", 1);
        final School U2 = new School("U2", 1);
        final School U3 = new School("U3", 1);
        Map<String, Set<String>> result = testSubject.calculate(ImmutableSet.of(U1, U2, U3),
                ImmutableSet.of(
                        ApplicationBuilder.withStudentName("ST1").withScore(U1.getName(), 10).withScore(U2.getName(), 20).withScore(U3.getName(), 30).build(),
                        ApplicationBuilder.withStudentName("ST2").withScore(U1.getName(), 2).withScore(U2.getName(), 40).withScore(U3.getName(), 20).build(),
                        ApplicationBuilder.withStudentName("ST3").withScore(U1.getName(), 30).withScore(U2.getName(), 50).withScore(U3.getName(), 10).build()));
        Map<String, Set<String>> expected = new HashMap<>();
        expected.put(U1.getName(), ImmutableSet.of("ST3"));
        expected.put(U2.getName(), ImmutableSet.of("ST2"));
        expected.put(U3.getName(), ImmutableSet.of("ST1"));

        assertThat(result, is(expected));
    }

    @Test
    public void shouldStudentReplacedFromSecondLevel() {
        final School U1 = new School("U1", 1);
        final School U2 = new School("U2", 0);
        Map<String, Set<String>> result = testSubject.calculate(ImmutableSet.of(U1, U2),
                ImmutableSet.of(
                        ApplicationBuilder.withStudentName("ST1").withScore(U1.getName(), 10).withScore(U2.getName(), 30).build(),
                        ApplicationBuilder.withStudentName("ST2").withScore(U2.getName(), 30).withScore(U1.getName(), 15).build()));
        Map<String, Set<String>> expected = new HashMap<>();
        expected.put(U1.getName(), ImmutableSet.of("ST2"));
        expected.put(U2.getName(), Collections.EMPTY_SET);

        assertThat(result, is(expected));
    }

    @Test
    public void shouldAllAssignedToFirstLevel() {
        final School U1 = new School("U1", 1);
        final School U2 = new School("U2", 1);
        Map<String, Set<String>> result = testSubject.calculate(ImmutableSet.of(U1, U2),
                ImmutableSet.of(
                        ApplicationBuilder.withStudentName("ST1").withScore(U1.getName(), 1).withScore(U2.getName(), 2).build(),
                        ApplicationBuilder.withStudentName("ST2").withScore(U2.getName(), 1).withScore(U1.getName(), 2).build()));
        Map<String, Set<String>> expected = new HashMap<>();
        expected.put(U2.getName(), ImmutableSet.of("ST2"));
        expected.put(U1.getName(), ImmutableSet.of("ST1"));

        assertThat(result, is(expected));
    }

    @Test
    public void shouldWorkForDifferentApplicationListSize() {
        final School U1 = new School("U1", 1);
        final School U2 = new School("U2", 1);
        final School U3 = new School("U3", 4);
        Map<String, Set<String>> result = testSubject.calculate(ImmutableSet.of(U1, U2, U3),
                ImmutableSet.of(
                        ApplicationBuilder.withStudentName("ST1").withScore(U1.getName(), 1).build(),
                        ApplicationBuilder.withStudentName("ST2").withScore(U1.getName(), 0).withScore(U2.getName(), 5).build()));
        Map<String, Set<String>> expected = new HashMap<>();
        expected.put(U1.getName(), ImmutableSet.of("ST1"));
        expected.put(U2.getName(), ImmutableSet.of("ST2"));
        expected.put(U3.getName(), Collections.EMPTY_SET);

        assertThat(result, is(expected));
    }

    @Test
    public void shouldWorkForMoreApplicantInSameLevel() {
        final School ELTE = new School("ELTE", 1);
        final School BME = new School("BME", 3);
        Map<String, Set<String>> result = testSubject.calculate(ImmutableSet.of(ELTE, BME),
                ImmutableSet.of(
                        ApplicationBuilder.withStudentName("Bela").withScore(ELTE.getName(), 100).withScore(BME.getName(), 110).build(),
                        ApplicationBuilder.withStudentName("Juli").withScore(ELTE.getName(), 100).build(),
                        ApplicationBuilder.withStudentName("Jakab").withScore(BME.getName(), 60).build(),
                        ApplicationBuilder.withStudentName("Pista").withScore(BME.getName(), 80).build()));
        Map<String, Set<String>> expected = new HashMap<>();
        expected.put(ELTE.getName(), ImmutableSet.of("Bela"));
        expected.put(BME.getName(), ImmutableSet.of("Jakab", "Pista"));

        assertThat(result, is(expected));
    }
}