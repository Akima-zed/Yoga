package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class TeacherDtoTest {
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
}
