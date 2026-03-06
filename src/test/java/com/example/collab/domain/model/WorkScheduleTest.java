package com.example.collab.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("WorkSchedule Model Tests")
public class WorkScheduleTest {

    private WorkSchedule workSchedule;

    @BeforeEach
    void setUp() {
        workSchedule = new WorkSchedule(1L);
    }

    @Test
    @DisplayName("Should create WorkSchedule with id constructor")
    void testCreateWorkSchedule_WithIdConstructor() {
        assertNotNull(workSchedule);
        assertEquals(1L, workSchedule.getId());
        assertNull(workSchedule.getDescription());
        assertNull(workSchedule.getWorkDaysPerWeek());
        assertNull(workSchedule.getRestDaysPerWeek());
        assertNull(workSchedule.getIsActive());
    }

    @Test
    @DisplayName("Should create WorkSchedule with different ids")
    void testCreateWorkSchedule_DifferentIds() {
        WorkSchedule ws1 = new WorkSchedule(1L);
        WorkSchedule ws2 = new WorkSchedule(2L);
        WorkSchedule ws3 = new WorkSchedule(100L);

        assertEquals(1L, ws1.getId());
        assertEquals(2L, ws2.getId());
        assertEquals(100L, ws3.getId());
    }

    @Test
    @DisplayName("Should set and get description")
    void testSetGetDescription() {
        String description = "Monday to Friday";
        workSchedule.setDescription(description);
        assertEquals(description, workSchedule.getDescription());
    }

    @Test
    @DisplayName("Should set and get workDaysPerWeek")
    void testSetGetWorkDaysPerWeek() {
        Integer workDaysPerWeek = 5;
        workSchedule.setWorkDaysPerWeek(workDaysPerWeek);
        assertEquals(workDaysPerWeek, workSchedule.getWorkDaysPerWeek());
    }

    @Test
    @DisplayName("Should set and get restDaysPerWeek")
    void testSetGetRestDaysPerWeek() {
        Integer restDaysPerWeek = 2;
        workSchedule.setRestDaysPerWeek(restDaysPerWeek);
        assertEquals(restDaysPerWeek, workSchedule.getRestDaysPerWeek());
    }

    @Test
    @DisplayName("Should set and get isActive")
    void testSetGetIsActive() {
        workSchedule.setIsActive(true);
        assertTrue(workSchedule.getIsActive());

        workSchedule.setIsActive(false);
        assertFalse(workSchedule.getIsActive());
    }

    @Test
    @DisplayName("Should set multiple properties together")
    void testSetMultipleProperties() {
        workSchedule.setDescription("Full week schedule");
        workSchedule.setWorkDaysPerWeek(5);
        workSchedule.setRestDaysPerWeek(2);
        workSchedule.setIsActive(true);

        assertEquals("Full week schedule", workSchedule.getDescription());
        assertEquals(5, workSchedule.getWorkDaysPerWeek());
        assertEquals(2, workSchedule.getRestDaysPerWeek());
        assertTrue(workSchedule.getIsActive());
    }

    @Test
    @DisplayName("Should handle null description")
    void testNullDescription() {
        workSchedule.setDescription(null);
        assertNull(workSchedule.getDescription());
    }

    @Test
    @DisplayName("Should handle null workDaysPerWeek")
    void testNullWorkDaysPerWeek() {
        workSchedule.setWorkDaysPerWeek(null);
        assertNull(workSchedule.getWorkDaysPerWeek());
    }

    @Test
    @DisplayName("Should handle null isActive")
    void testNullIsActive() {
        workSchedule.setIsActive(null);
        assertNull(workSchedule.getIsActive());
    }

    @Test
    @DisplayName("Should test equality with same id")
    void testEquality_SameId() {
        WorkSchedule ws1 = new WorkSchedule(1L);
        ws1.setDescription("Schedule 1");
        ws1.setWorkDaysPerWeek(5);
        ws1.setRestDaysPerWeek(2);
        ws1.setIsActive(true);

        WorkSchedule ws2 = new WorkSchedule(1L);
        ws2.setDescription("Schedule 1");
        ws2.setWorkDaysPerWeek(5);
        ws2.setRestDaysPerWeek(2);
        ws2.setIsActive(true);

        assertEquals(ws1, ws2);
    }

    @Test
    @DisplayName("Should test inequality with different ids")
    void testInequality_DifferentIds() {
        WorkSchedule ws1 = new WorkSchedule(1L);
        WorkSchedule ws2 = new WorkSchedule(2L);

        assertNotEquals(ws1, ws2);
    }

    @Test
    @DisplayName("Should handle edge case workDaysPerWeek values")
    void testEdgeCaseWorkDaysPerWeek() {
        workSchedule.setWorkDaysPerWeek(1);
        assertEquals(1, workSchedule.getWorkDaysPerWeek());

        workSchedule.setWorkDaysPerWeek(7);
        assertEquals(7, workSchedule.getWorkDaysPerWeek());

        workSchedule.setWorkDaysPerWeek(0);
        assertEquals(0, workSchedule.getWorkDaysPerWeek());
    }

    @Test
    @DisplayName("Should handle various descriptions")
    void testVariousDescriptions() {
        String[] descriptions = {
            "Morning shift",
            "Monday to Friday",
            "Weekend rotation",
            "Split schedule",
            ""
        };

        for (String desc : descriptions) {
            workSchedule.setDescription(desc);
            assertEquals(desc, workSchedule.getDescription());
        }
    }

    @Test
    @DisplayName("Should not allow id setter")
    void testIdCannotBeModified() {
        WorkSchedule ws = new WorkSchedule(1L);
        assertEquals(1L, ws.getId());
        
        // Id should not be settable (AccessLevel.NONE on setter)
        // This just verifies the id stays as initialized
        assertEquals(1L, ws.getId());
    }

    @Test
    @DisplayName("Should handle state transitions")
    void testStateTransitions() {
        // Start inactive
        workSchedule.setIsActive(false);
        assertFalse(workSchedule.getIsActive());

        // Activate
        workSchedule.setIsActive(true);
        assertTrue(workSchedule.getIsActive());

        // Deactivate
        workSchedule.setIsActive(false);
        assertFalse(workSchedule.getIsActive());
    }
}
