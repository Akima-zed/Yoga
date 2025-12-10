package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class TeacherDtoTest {
        @Test
        void testNullFieldsAndToString() {
            TeacherDto dto = new TeacherDto();
            assertNull(dto.getId());
            assertNull(dto.getLastName());
            assertNull(dto.getFirstName());
            assertNull(dto.getCreatedAt());
            assertNull(dto.getUpdatedAt());
            dto.setLastName("TestLastName");
            assertTrue(dto.toString().contains("TestLastName"));
        }
    @Test
    void allArgsConstructor_and_getters_shouldWork() {
        LocalDateTime now = LocalDateTime.now();
        TeacherDto dto = new TeacherDto(1L, "Doe", "John", now, now);
        assertEquals(1L, dto.getId());
        assertEquals("Doe", dto.getLastName());
        assertEquals("John", dto.getFirstName());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void noArgsConstructor_and_setters_shouldWork() {
        TeacherDto dto = new TeacherDto();
        dto.setId(2L);
        dto.setLastName("Smith");
        dto.setFirstName("Alice");
        LocalDateTime now = LocalDateTime.now();
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);
        assertEquals(2L, dto.getId());
        assertEquals("Smith", dto.getLastName());
        assertEquals("Alice", dto.getFirstName());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void testTeacherDtoProperties() {
        LocalDateTime now = LocalDateTime.now();
        TeacherDto dto = new TeacherDto(1L, "Doe", "John", now, now);
        assertEquals(1L, dto.getId());
        assertEquals("Doe", dto.getLastName());
        assertEquals("John", dto.getFirstName());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        TeacherDto dto1 = new TeacherDto(1L, "Doe", "John", null, null);
        TeacherDto dto2 = new TeacherDto(1L, "Doe", "John", null, null);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
