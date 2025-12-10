package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class UserDtoTest {
            @Test
            void testEqualsAndHashCodeNullCombinations() {
                LocalDateTime now = LocalDateTime.now();
                // Tous null
                UserDto dtoNull1 = new UserDto(null, null, null, null, false, null, null, null);
                UserDto dtoNull2 = new UserDto(null, null, null, null, false, null, null, null);
                assertEquals(dtoNull1, dtoNull2);
                assertEquals(dtoNull1.hashCode(), dtoNull2.hashCode());

                // Un champ différent à chaque fois
                UserDto dtoId = new UserDto(1L, null, null, null, false, null, null, null);
                assertNotEquals(dtoNull1, dtoId);
                UserDto dtoEmail = new UserDto(null, "a@b.com", null, null, false, null, null, null);
                assertNotEquals(dtoNull1, dtoEmail);
                UserDto dtoLastName = new UserDto(null, null, "Doe", null, false, null, null, null);
                assertNotEquals(dtoNull1, dtoLastName);
                UserDto dtoFirstName = new UserDto(null, null, null, "John", false, null, null, null);
                assertNotEquals(dtoNull1, dtoFirstName);
                UserDto dtoAdmin = new UserDto(null, null, null, null, true, null, null, null);
                assertNotEquals(dtoNull1, dtoAdmin);
                UserDto dtoPassword = new UserDto(null, null, null, null, false, "pass", null, null);
                assertNotEquals(dtoNull1, dtoPassword);
                UserDto dtoCreatedAt = new UserDto(null, null, null, null, false, null, now, null);
                assertNotEquals(dtoNull1, dtoCreatedAt);
                UserDto dtoUpdatedAt = new UserDto(null, null, null, null, false, null, null, now);
                assertNotEquals(dtoNull1, dtoUpdatedAt);

                // Deux objets avec certains champs null, certains non
                UserDto dtoMix1 = new UserDto(1L, null, "Doe", null, true, null, now, null);
                UserDto dtoMix2 = new UserDto(1L, null, "Doe", null, true, null, now, null);
                assertEquals(dtoMix1, dtoMix2);
                assertEquals(dtoMix1.hashCode(), dtoMix2.hashCode());
                // Mutation d'un champ
                dtoMix1.setLastName("Smith");
                assertNotEquals(dtoMix1, dtoMix2);
            }
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
    void testValidationAndEdgeCases() {
        UserDto dto = new UserDto();
        // Test max size email
        String longEmail = "a".repeat(40) + "@test.com";
        dto.setEmail(longEmail);
        assertEquals(longEmail, dto.getEmail());
        // Test max size lastName/firstName
        String longName = "b".repeat(20);
        dto.setLastName(longName);
        dto.setFirstName(longName);
        assertEquals(longName, dto.getLastName());
        assertEquals(longName, dto.getFirstName());
        // Test password max size
        String longPass = "c".repeat(120);
        dto.setPassword(longPass);
        assertEquals(longPass, dto.getPassword());
        // Test admin true/false
        dto.setAdmin(true);
        assertTrue(dto.isAdmin());
        dto.setAdmin(false);
        assertFalse(dto.isAdmin());
        // Edge dates
        dto.setCreatedAt(LocalDateTime.MIN);
        dto.setUpdatedAt(LocalDateTime.MAX);
        assertEquals(LocalDateTime.MIN, dto.getCreatedAt());
        assertEquals(LocalDateTime.MAX, dto.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        UserDto dto1 = new UserDto(1L, "a@b.com", "Doe", "John", false, "pass", null, null);
        UserDto dto2 = new UserDto(1L, "a@b.com", "Doe", "John", false, "pass", null, null);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        // Not equals to null
        assertNotEquals(dto1, null);
        // Not equals to other type
        assertNotEquals(dto1, "string");
        // Not equals to different id
        UserDto dto3 = new UserDto(2L, "a@b.com", "Doe", "John", false, "pass", null, null);
        assertNotEquals(dto1, dto3);
        // Mutation after construction
        dto1.setEmail("other@test.com");
        assertNotEquals(dto1, dto2);
        // Extreme values
        UserDto dtoExtreme = new UserDto(Long.MAX_VALUE, "", "", "", true, "", null, null);
        assertEquals(Long.MAX_VALUE, dtoExtreme.getId());
        assertTrue(dtoExtreme.isAdmin());
    }

    @Test
    void testEqualsAndHashCodeAdvanced() {
        LocalDateTime now = LocalDateTime.now();
        UserDto dto1 = new UserDto(1L, "a@b.com", "Doe", "John", true, "pass", now, now);
        UserDto dto2 = new UserDto(1L, "a@b.com", "Doe", "John", true, "pass", now, now);
        UserDto dto3 = new UserDto(2L, "c@d.com", "Smith", "Alice", false, "secret", now, now);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testToStringSpecialValues() {
        UserDto dto = new UserDto();
        dto.setEmail(null);
        dto.setLastName(null);
        dto.setFirstName(null);
        assertNotNull(dto.toString());
        dto.setEmail("\n\t\u2603@test.com");
        assertTrue(dto.toString().contains("\u2603"));
    }
}
