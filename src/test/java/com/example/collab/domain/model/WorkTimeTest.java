package com.example.collab.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("WorkTime Model Tests")
public class WorkTimeTest {

    private WorkTime workTime;

    @BeforeEach
    void setUp() {
        workTime = new WorkTime(1L);
    }

    @Test
    @DisplayName("Should create WorkTime with id constructor")
    void testCreateWorkTime_WithIdConstructor() {
        assertNotNull(workTime);
        assertEquals(1L, workTime.getId());
        assertNull(workTime.getDescription());
        assertNull(workTime.getIsActive());
        assertNull(workTime.getInitialTime());
        assertNull(workTime.getEndTime());
        assertNull(workTime.getIsOvernight());
        assertNull(workTime.getInitialBreakTime());
        assertNull(workTime.getEndBreakTime());
        assertNull(workTime.getRequiresPunch());
        assertNull(workTime.getAutoGeneratePunches());
    }

    @Test
    @DisplayName("Should set and get description")
    void testSetGetDescription() {
        String description = "Morning Shift";
        workTime.setDescription(description);
        assertEquals(description, workTime.getDescription());
    }

    @Test
    @DisplayName("Should set and get isActive")
    void testSetGetIsActive() {
        workTime.setIsActive(true);
        assertTrue(workTime.getIsActive());

        workTime.setIsActive(false);
        assertFalse(workTime.getIsActive());
    }

    @Test
    @DisplayName("Should set and get initialTime")
    void testSetGetInitialTime() {
        LocalTime initialTime = LocalTime.of(8, 0);
        workTime.setInitialTime(initialTime);
        assertEquals(initialTime, workTime.getInitialTime());
    }

    @Test
    @DisplayName("Should set and get endTime")
    void testSetGetEndTime() {
        LocalTime endTime = LocalTime.of(12, 0);
        workTime.setEndTime(endTime);
        assertEquals(endTime, workTime.getEndTime());
    }

    @Test
    @DisplayName("Should set and get isOvernight")
    void testSetGetIsOvernight() {
        workTime.setIsOvernight(true);
        assertTrue(workTime.getIsOvernight());

        workTime.setIsOvernight(false);
        assertFalse(workTime.getIsOvernight());
    }

    @Test
    @DisplayName("Should set and get initialBreakTime")
    void testSetGetInitialBreakTime() {
        LocalTime initialBreakTime = LocalTime.of(10, 0);
        workTime.setInitialBreakTime(initialBreakTime);
        assertEquals(initialBreakTime, workTime.getInitialBreakTime());
    }

    @Test
    @DisplayName("Should set and get endBreakTime")
    void testSetGetEndBreakTime() {
        LocalTime endBreakTime = LocalTime.of(10, 30);
        workTime.setEndBreakTime(endBreakTime);
        assertEquals(endBreakTime, workTime.getEndBreakTime());
    }

    @Test
    @DisplayName("Should set and get requiresPunch")
    void testSetGetRequiresPunch() {
        workTime.setRequiresPunch(true);
        assertTrue(workTime.getRequiresPunch());

        workTime.setRequiresPunch(false);
        assertFalse(workTime.getRequiresPunch());
    }

    @Test
    @DisplayName("Should set and get autoGeneratePunches")
    void testSetGetAutoGeneratePunches() {
        workTime.setAutoGeneratePunches(true);
        assertTrue(workTime.getAutoGeneratePunches());

        workTime.setAutoGeneratePunches(false);
        assertFalse(workTime.getAutoGeneratePunches());
    }

    @Test
    @DisplayName("Should set all time properties together")
    void testSetAllTimeProperties() {
        LocalTime initialTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        LocalTime initialBreakTime = LocalTime.of(12, 0);
        LocalTime endBreakTime = LocalTime.of(13, 0);

        workTime.setInitialTime(initialTime);
        workTime.setEndTime(endTime);
        workTime.setInitialBreakTime(initialBreakTime);
        workTime.setEndBreakTime(endBreakTime);

        assertEquals(initialTime, workTime.getInitialTime());
        assertEquals(endTime, workTime.getEndTime());
        assertEquals(initialBreakTime, workTime.getInitialBreakTime());
        assertEquals(endBreakTime, workTime.getEndBreakTime());
    }

    @Test
    @DisplayName("Should set all boolean properties together")
    void testSetAllBooleanProperties() {
        workTime.setIsActive(true);
        workTime.setIsOvernight(false);
        workTime.setRequiresPunch(true);
        workTime.setAutoGeneratePunches(true);

        assertTrue(workTime.getIsActive());
        assertFalse(workTime.getIsOvernight());
        assertTrue(workTime.getRequiresPunch());
        assertTrue(workTime.getAutoGeneratePunches());
    }

    @Test
    @DisplayName("Should set complete WorkTime object")
    void testSetCompleteWorkTime() {
        workTime.setDescription("Full day shift");
        workTime.setIsActive(true);
        workTime.setInitialTime(LocalTime.of(8, 0));
        workTime.setEndTime(LocalTime.of(17, 0));
        workTime.setIsOvernight(false);
        workTime.setInitialBreakTime(LocalTime.of(12, 0));
        workTime.setEndBreakTime(LocalTime.of(13, 0));
        workTime.setRequiresPunch(true);
        workTime.setAutoGeneratePunches(true);

        assertEquals("Full day shift", workTime.getDescription());
        assertTrue(workTime.getIsActive());
        assertEquals(LocalTime.of(8, 0), workTime.getInitialTime());
        assertEquals(LocalTime.of(17, 0), workTime.getEndTime());
        assertFalse(workTime.getIsOvernight());
        assertEquals(LocalTime.of(12, 0), workTime.getInitialBreakTime());
        assertEquals(LocalTime.of(13, 0), workTime.getEndBreakTime());
        assertTrue(workTime.getRequiresPunch());
        assertTrue(workTime.getAutoGeneratePunches());
    }

    @Test
    @DisplayName("Should handle null times")
    void testNullTimes() {
        workTime.setInitialTime(null);
        workTime.setEndTime(null);
        workTime.setInitialBreakTime(null);
        workTime.setEndBreakTime(null);

        assertNull(workTime.getInitialTime());
        assertNull(workTime.getEndTime());
        assertNull(workTime.getInitialBreakTime());
        assertNull(workTime.getEndBreakTime());
    }

    @Test
    @DisplayName("Should handle null description")
    void testNullDescription() {
        workTime.setDescription(null);
        assertNull(workTime.getDescription());
    }

    @Test
    @DisplayName("Should handle null booleans")
    void testNullBooleans() {
        workTime.setIsActive(null);
        workTime.setIsOvernight(null);
        workTime.setRequiresPunch(null);
        workTime.setAutoGeneratePunches(null);

        assertNull(workTime.getIsActive());
        assertNull(workTime.getIsOvernight());
        assertNull(workTime.getRequiresPunch());
        assertNull(workTime.getAutoGeneratePunches());
    }

    @Test
    @DisplayName("Should test equality with same values")
    void testEquality_SameValues() {
        WorkTime wt1 = new WorkTime(1L);
        wt1.setDescription("Morning shift");
        wt1.setIsActive(true);
        wt1.setInitialTime(LocalTime.of(8, 0));
        wt1.setEndTime(LocalTime.of(12, 0));
        wt1.setIsOvernight(false);
        wt1.setInitialBreakTime(LocalTime.of(10, 0));
        wt1.setEndBreakTime(LocalTime.of(10, 30));
        wt1.setRequiresPunch(true);
        wt1.setAutoGeneratePunches(true);

        WorkTime wt2 = new WorkTime(1L);
        wt2.setDescription("Morning shift");
        wt2.setIsActive(true);
        wt2.setInitialTime(LocalTime.of(8, 0));
        wt2.setEndTime(LocalTime.of(12, 0));
        wt2.setIsOvernight(false);
        wt2.setInitialBreakTime(LocalTime.of(10, 0));
        wt2.setEndBreakTime(LocalTime.of(10, 30));
        wt2.setRequiresPunch(true);
        wt2.setAutoGeneratePunches(true);

        assertEquals(wt1, wt2);
    }

    @Test
    @DisplayName("Should test inequality with different ids")
    void testInequality_DifferentIds() {
        WorkTime wt1 = new WorkTime(1L);
        WorkTime wt2 = new WorkTime(2L);

        assertNotEquals(wt1, wt2);
    }

    @Test
    @DisplayName("Should handle overnight shift configuration")
    void testOvernightShiftConfiguration() {
        workTime.setDescription("Night shift");
        workTime.setInitialTime(LocalTime.of(22, 0));
        workTime.setEndTime(LocalTime.of(6, 0));
        workTime.setIsOvernight(true);
        workTime.setRequiresPunch(true);

        assertEquals("Night shift", workTime.getDescription());
        assertEquals(LocalTime.of(22, 0), workTime.getInitialTime());
        assertEquals(LocalTime.of(6, 0), workTime.getEndTime());
        assertTrue(workTime.getIsOvernight());
        assertTrue(workTime.getRequiresPunch());
    }

    @Test
    @DisplayName("Should handle multiple time configurations")
    void testMultipleTimeConfigurations() {
        // Evening shift
        LocalTime evening8am = LocalTime.of(8, 0);
        LocalTime evening6pm = LocalTime.of(18, 0);
        
        workTime.setInitialTime(evening8am);
        workTime.setEndTime(evening6pm);
        assertEquals(evening8am, workTime.getInitialTime());
        assertEquals(evening6pm, workTime.getEndTime());

        // Change to night shift
        LocalTime night10pm = LocalTime.of(22, 0);
        LocalTime night6am = LocalTime.of(6, 0);
        
        workTime.setInitialTime(night10pm);
        workTime.setEndTime(night6am);
        assertEquals(night10pm, workTime.getInitialTime());
        assertEquals(night6am, workTime.getEndTime());
    }

    @Test
    @DisplayName("Should create multiple WorkTime instances with different ids")
    void testMultipleWorkTimeInstances() {
        WorkTime wt1 = new WorkTime(1L);
        WorkTime wt2 = new WorkTime(2L);
        WorkTime wt3 = new WorkTime(3L);

        assertEquals(1L, wt1.getId());
        assertEquals(2L, wt2.getId());
        assertEquals(3L, wt3.getId());

        assertNotEquals(wt1, wt2);
        assertNotEquals(wt2, wt3);
        assertNotEquals(wt1, wt3);
    }

    @Test
    @DisplayName("Should handle state transitions for isActive")
    void testStateTransitionsForIsActive() {
        workTime.setIsActive(false);
        assertFalse(workTime.getIsActive());

        workTime.setIsActive(true);
        assertTrue(workTime.getIsActive());

        workTime.setIsActive(false);
        assertFalse(workTime.getIsActive());
    }

    @Test
    @DisplayName("Should handle edge case times (midnight and noon)")
    void testEdgeCaseTimes() {
        LocalTime midnight = LocalTime.of(0, 0);
        LocalTime noon = LocalTime.of(12, 0);

        workTime.setInitialTime(midnight);
        workTime.setEndTime(noon);

        assertEquals(midnight, workTime.getInitialTime());
        assertEquals(noon, workTime.getEndTime());
    }
}
