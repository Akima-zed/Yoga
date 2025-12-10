package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {
        @Test
        void testBuilderWithNullsAndToString() {
            Teacher teacher = Teacher.builder().build();
            assertNull(teacher.getId());
            assertNull(teacher.getLastName());
            assertNull(teacher.getFirstName());
            assertTrue(teacher.toString().contains("Teacher"));
        }
    @Test
    void builder_and_getters_shouldWork() {
        Teacher teacher = Teacher.builder()
                .id(1L)
                .lastName("Doe")
                .firstName("John")
                .build();
        assertEquals(1L, teacher.getId());
        assertEquals("Doe", teacher.getLastName());
        assertEquals("John", teacher.getFirstName());
    }

    @Test
    void equals_and_hashCode_shouldWork() {
        Teacher t1 = Teacher.builder().id(1L).lastName("Doe").build();
        Teacher t2 = Teacher.builder().id(1L).lastName("Doe").build();
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
        // Not equals to null
        assertNotEquals(t1, null);
        // Not equals to other type
        assertNotEquals(t1, "string");
        // Not equals to different id
        Teacher t3 = Teacher.builder().id(2L).lastName("Doe").build();
        assertNotEquals(t1, t3);
        // Mutation after construction
        t1.setLastName("Other");
        assertNotEquals(t1, t2);
        // Extreme values
        Teacher tExtreme = Teacher.builder().id(Long.MAX_VALUE).lastName("").build();
        assertEquals(Long.MAX_VALUE, tExtreme.getId());
    }

    @Test
    void testTeacherSettersAndEdgeCases() {
        Teacher teacher = new Teacher();
        teacher.setId(null);
        teacher.setLastName("");
        teacher.setFirstName(null);
        teacher.setCreatedAt(null);
        teacher.setUpdatedAt(null);
        assertNull(teacher.getId());
        assertEquals("", teacher.getLastName());
        assertNull(teacher.getFirstName());
        assertNull(teacher.getCreatedAt());
        assertNull(teacher.getUpdatedAt());
    }

    @Test
    void testValidationAndEdgeCases() {
        Teacher teacher = new Teacher();
        // Test blank lastName/firstName
        teacher.setLastName("");
        teacher.setFirstName("");
        assertEquals("", teacher.getLastName());
        assertEquals("", teacher.getFirstName());
        // Test max size lastName/firstName
        String longName = "a".repeat(20);
        teacher.setLastName(longName);
        teacher.setFirstName(longName);
        assertEquals(longName, teacher.getLastName());
        assertEquals(longName, teacher.getFirstName());
        // Edge dates
        teacher.setCreatedAt(java.time.LocalDateTime.MIN);
        teacher.setUpdatedAt(java.time.LocalDateTime.MAX);
        assertEquals(java.time.LocalDateTime.MIN, teacher.getCreatedAt());
        assertEquals(java.time.LocalDateTime.MAX, teacher.getUpdatedAt());
    }

    @Test
    void testToStringSpecialValues() {
        Teacher teacher = new Teacher();
        teacher.setLastName(null);
        teacher.setFirstName(null);
        assertNotNull(teacher.toString());
        teacher.setLastName("\n\t\u2603");
        assertTrue(teacher.toString().contains("\u2603"));
    }

        @Test
        void testIsSenior_nullLastName() {
            Teacher teacher = new Teacher();
            teacher.setLastName(null);
            assertFalse(teacher.isSenior());
        }

        @Test
        void testIsSenior_notSenior() {
            Teacher teacher = new Teacher();
            teacher.setLastName("Martin");
            assertFalse(teacher.isSenior());
        }

        @Test
        void testIsSenior_drPrefix() {
            Teacher teacher = new Teacher();
            teacher.setLastName("DrMartin");
            assertTrue(teacher.isSenior());
        }

        @Test
        void testIsSenior_profPrefix() {
            Teacher teacher = new Teacher();
            teacher.setLastName("ProfDupont");
            assertTrue(teacher.isSenior());
        }

        @Test
        void testIsSenior_allBranches() {
            // lastName=null
            Teacher t1 = Teacher.builder().lastName(null).build();
            assertFalse(t1.isSenior());
            // lastName does not start with Dr or Prof
            Teacher t2 = Teacher.builder().lastName("Monsieur").build();
            assertFalse(t2.isSenior());
            // lastName starts with Dr
            Teacher t3 = Teacher.builder().lastName("Dr Martin").build();
            assertTrue(t3.isSenior());
            // lastName starts with Prof
            Teacher t4 = Teacher.builder().lastName("Prof Dupont").build();
            assertTrue(t4.isSenior());
    }
}
