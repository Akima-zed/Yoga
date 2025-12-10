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
        // Not equals to null
        assertNotEquals(dto1, null);
        // Not equals to other type
        assertNotEquals(dto1, "string");
        // Not equals to different id
        TeacherDto dto3 = new TeacherDto(2L, "Doe", "John", null, null);
        assertNotEquals(dto1, dto3);
        // Mutation after construction
        dto1.setLastName("Other");
        assertNotEquals(dto1, dto2);
        // Extreme values
        TeacherDto dtoExtreme = new TeacherDto(Long.MAX_VALUE, "", "", null, null);
        assertEquals(Long.MAX_VALUE, dtoExtreme.getId());
    }

    @Test
    void testValidationAndEdgeCases() {
        TeacherDto dto = new TeacherDto();
        // Test blank lastName/firstName
        dto.setLastName("");
        dto.setFirstName("");
        assertEquals("", dto.getLastName());
        assertEquals("", dto.getFirstName());
        // Test max size lastName/firstName
        String longName = "a".repeat(20);
        dto.setLastName(longName);
        dto.setFirstName(longName);
        assertEquals(longName, dto.getLastName());
        assertEquals(longName, dto.getFirstName());
        // Edge dates
        dto.setCreatedAt(LocalDateTime.MIN);
        dto.setUpdatedAt(LocalDateTime.MAX);
        assertEquals(LocalDateTime.MIN, dto.getCreatedAt());
        assertEquals(LocalDateTime.MAX, dto.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCodeAdvanced() {
        LocalDateTime now = LocalDateTime.now();
        TeacherDto dto1 = new TeacherDto(1L, "Doe", "John", now, now);
        TeacherDto dto2 = new TeacherDto(1L, "Doe", "John", now, now);
        TeacherDto dto3 = new TeacherDto(2L, "Smith", "Alice", now, now);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testToStringSpecialValues() {
        TeacherDto dto = new TeacherDto();
        dto.setLastName(null);
        dto.setFirstName(null);
        assertNotNull(dto.toString());
        dto.setLastName("\n\t\u2603");
        assertTrue(dto.toString().contains("\u2603"));
    }
        @Test
        void testMutationToNullAfterInit() {
            LocalDateTime now = LocalDateTime.now();
            TeacherDto dto = new TeacherDto(1L, "Doe", "John", now, now);
            // Mutate all to null
            dto.setId(null);
            dto.setLastName(null);
            dto.setFirstName(null);
            dto.setCreatedAt(null);
            dto.setUpdatedAt(null);
            assertNull(dto.getId());
            assertNull(dto.getLastName());
            assertNull(dto.getFirstName());
            assertNull(dto.getCreatedAt());
            assertNull(dto.getUpdatedAt());
            // equals/hashCode with all nulls
            TeacherDto allNull = new TeacherDto(null, null, null, null, null);
            assertEquals(dto, allNull);
            assertEquals(dto.hashCode(), allNull.hashCode());
            // toString should not throw
            assertNotNull(dto.toString());
        }

        @Test
        void testEqualsHashCodeCombinations() {
            LocalDateTime now = LocalDateTime.now();
            TeacherDto base = new TeacherDto(1L, "Doe", "John", now, now);
            // All nulls
            TeacherDto allNull = new TeacherDto(null, null, null, null, null);
            assertNotEquals(base, allNull);
            assertNotEquals(base.hashCode(), allNull.hashCode());
            // Only id null
            TeacherDto idNull = new TeacherDto(null, "Doe", "John", now, now);
            assertNotEquals(base, idNull);
            // Only lastName null
            TeacherDto lastNameNull = new TeacherDto(1L, null, "John", now, now);
            assertNotEquals(base, lastNameNull);
            // Only firstName null
            TeacherDto firstNameNull = new TeacherDto(1L, "Doe", null, now, now);
            assertNotEquals(base, firstNameNull);
            // Only createdAt null
            TeacherDto createdNull = new TeacherDto(1L, "Doe", "John", null, now);
            assertNotEquals(base, createdNull);
            // Only updatedAt null
            TeacherDto updatedNull = new TeacherDto(1L, "Doe", "John", now, null);
            assertNotEquals(base, updatedNull);
        }
}
