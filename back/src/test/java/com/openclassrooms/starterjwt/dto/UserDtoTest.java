package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class UserDtoTest {
        @Test
        void testNullFieldsAndToString() {
            UserDto dto = new UserDto();
            assertNull(dto.getId());
            assertNull(dto.getEmail());
            assertNull(dto.getLastName());
            assertNull(dto.getFirstName());
            assertFalse(dto.isAdmin());
            assertNull(dto.getPassword());
            assertNull(dto.getCreatedAt());
            assertNull(dto.getUpdatedAt());
            dto.setEmail("TestEmail");
            assertTrue(dto.toString().contains("TestEmail"));
        }
    @Test
    void allArgsConstructor_and_getters_shouldWork() {
        LocalDateTime now = LocalDateTime.now();
        UserDto dto = new UserDto(1L, "test@test.com", "Doe", "John", true, "pass", now, now);
        assertEquals(1L, dto.getId());
        assertEquals("test@test.com", dto.getEmail());
        assertEquals("Doe", dto.getLastName());
        assertEquals("John", dto.getFirstName());
        assertTrue(dto.isAdmin());
        assertEquals("pass", dto.getPassword());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void noArgsConstructor_and_setters_shouldWork() {
        UserDto dto = new UserDto();
        dto.setId(2L);
        dto.setEmail("a@b.com");
        dto.setLastName("Smith");
        dto.setFirstName("Alice");
        dto.setAdmin(false);
        dto.setPassword("secret");
        LocalDateTime now = LocalDateTime.now();
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);
        assertEquals(2L, dto.getId());
        assertEquals("a@b.com", dto.getEmail());
        assertEquals("Smith", dto.getLastName());
        assertEquals("Alice", dto.getFirstName());
        assertFalse(dto.isAdmin());
        assertEquals("secret", dto.getPassword());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void testUserDtoProperties() {
        LocalDateTime now = LocalDateTime.now();
        UserDto dto = new UserDto(1L, "test@test.com", "Doe", "John", true, "pass", now, now);
        assertEquals(1L, dto.getId());
        assertEquals("test@test.com", dto.getEmail());
        assertEquals("Doe", dto.getLastName());
        assertEquals("John", dto.getFirstName());
        assertTrue(dto.isAdmin());
        assertEquals("pass", dto.getPassword());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        UserDto dto1 = new UserDto(1L, "a@b.com", "Doe", "John", false, "pass", null, null);
        UserDto dto2 = new UserDto(1L, "a@b.com", "Doe", "John", false, "pass", null, null);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
