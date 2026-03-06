package com.example.collab.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ScheduleRotation Model Tests")
public class ScheduleRotationTest {

    private ScheduleRotation scheduleRotation;
    private WorkSchedule workSchedule;
    private WorkTime workTime;

    @BeforeEach
    void setUp() {
        workSchedule = new WorkSchedule(1L);
        workTime = new WorkTime(1L);
        scheduleRotation = new ScheduleRotation();
    }

    @Test
    @DisplayName("Should create ScheduleRotation with default constructor")
    void testCreateScheduleRotation_DefaultConstructor() {
        assertNotNull(scheduleRotation);
        assertNull(scheduleRotation.getId());
        assertNull(scheduleRotation.getWorkSchedule());
        assertNull(scheduleRotation.getWorkTime());
        assertNull(scheduleRotation.getDayIndexs());
        assertNull(scheduleRotation.getWorkdays());
    }

    @Test
    @DisplayName("Should maintain null id when not generated")
    void testIdInitiallyNull() {
        assertNull(scheduleRotation.getId());
    }

    @Test
    @DisplayName("Should set and get workSchedule")
    void testSetGetWorkSchedule() {
        scheduleRotation.setWorkSchedule(workSchedule);
        assertEquals(workSchedule, scheduleRotation.getWorkSchedule());
    }

    @Test
    @DisplayName("Should set and get workTime")
    void testSetGetWorkTime() {
        scheduleRotation.setWorkTime(workTime);
        assertEquals(workTime, scheduleRotation.getWorkTime());
    }

    @Test
    @DisplayName("Should set and get dayIndexs")
    void testSetGetDayIndexs() {
        List<Integer> dayIndexs = List.of(1, 2, 3);
        scheduleRotation.setDayIndexs(dayIndexs);
        assertEquals(dayIndexs, scheduleRotation.getDayIndexs());
        assertEquals(3, scheduleRotation.getDayIndexs().size());
    }

    @Test
    @DisplayName("Should set and get workdays")
    void testSetGetWorkdays() {
        List<Boolean> workdays = List.of(true, true, false);
        scheduleRotation.setWorkdays(workdays);
        assertEquals(workdays, scheduleRotation.getWorkdays());
        assertEquals(3, scheduleRotation.getWorkdays().size());
    }

    @Test
    @DisplayName("Should handle null dayIndexs")
    void testNullDayIndexs() {
        scheduleRotation.setDayIndexs(null);
        assertNull(scheduleRotation.getDayIndexs());
    }

    @Test
    @DisplayName("Should handle null workdays")
    void testNullWorkdays() {
        scheduleRotation.setWorkdays(null);
        assertNull(scheduleRotation.getWorkdays());
    }

    @Test
    @DisplayName("Should test equality with same workSchedule and workTime")
    void testEquality_SameValues() {
        ScheduleRotation sr1 = new ScheduleRotation();
        sr1.setWorkSchedule(workSchedule);
        sr1.setWorkTime(workTime);
        sr1.setDayIndexs(List.of(1, 2, 3));
        sr1.setWorkdays(List.of(true, true, false));

        ScheduleRotation sr2 = new ScheduleRotation();
        sr2.setWorkSchedule(workSchedule);
        sr2.setWorkTime(workTime);
        sr2.setDayIndexs(List.of(1, 2, 3));
        sr2.setWorkdays(List.of(true, true, false));

        assertEquals(sr1, sr2);
    }

    @Test
    @DisplayName("Should test inequality with different workSchedule")
    void testInequality_DifferentWorkSchedule() {
        WorkSchedule ws1 = new WorkSchedule(1L);
        WorkSchedule ws2 = new WorkSchedule(2L);
        
        ScheduleRotation sr1 = new ScheduleRotation();
        sr1.setWorkSchedule(ws1);
        
        ScheduleRotation sr2 = new ScheduleRotation();
        sr2.setWorkSchedule(ws2);

        assertNotEquals(sr1, sr2);
    }

    @Test
    @DisplayName("Should set multiple properties together")
    void testSetMultipleProperties() {
        List<Integer> dayIndexs = List.of(1, 2, 3, 4, 5);
        List<Boolean> workdays = List.of(true, true, true, true, false);
        
        scheduleRotation.setWorkSchedule(workSchedule);
        scheduleRotation.setWorkTime(workTime);
        scheduleRotation.setDayIndexs(dayIndexs);
        scheduleRotation.setWorkdays(workdays);

        assertNotNull(scheduleRotation.getWorkSchedule());
        assertNotNull(scheduleRotation.getWorkTime());
        assertEquals(5, scheduleRotation.getDayIndexs().size());
        assertEquals(5, scheduleRotation.getWorkdays().size());
    }

    @Test
    @DisplayName("Should handle empty list for dayIndexs")
    void testEmptyDayIndexsList() {
        scheduleRotation.setDayIndexs(List.of());
        assertNotNull(scheduleRotation.getDayIndexs());
        assertEquals(0, scheduleRotation.getDayIndexs().size());
    }

    @Test
    @DisplayName("Should handle workSchedule null after assignment")
    void testWorkScheduleNullAfterAssignment() {
        scheduleRotation.setWorkSchedule(workSchedule);
        assertNotNull(scheduleRotation.getWorkSchedule());
        
        scheduleRotation.setWorkSchedule(null);
        assertNull(scheduleRotation.getWorkSchedule());
    }

    @Test
    @DisplayName("Should handle various dayIndexs values")
    void testVariousDayIndexsValues() {
        scheduleRotation.setDayIndexs(List.of(1));
        assertEquals(1, scheduleRotation.getDayIndexs().size());

        scheduleRotation.setDayIndexs(List.of(1, 2, 3, 4, 5, 6, 7));
        assertEquals(7, scheduleRotation.getDayIndexs().size());

        scheduleRotation.setDayIndexs(List.of(7, 5, 3, 1));
        assertEquals(4, scheduleRotation.getDayIndexs().size());
    }
}
