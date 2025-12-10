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
    }
}
