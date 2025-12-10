package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

class SessionDtoTest {
    @Test
    void testMutationAllProperties() {
        SessionDto dto = new SessionDto();
        dto.setId(100L);
        dto.setName("MutationTest");
        dto.setDate(new Date(123456789L));
        dto.setTeacher_id(-1L);
        dto.setDescription("MutatedDesc");
        dto.setUsers(List.of(42L, 43L));
        dto.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0));
        dto.setUpdatedAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        assertEquals(100L, dto.getId());
        assertEquals("MutationTest", dto.getName());
        assertEquals(new Date(123456789L), dto.getDate());
        assertEquals(-1L, dto.getTeacher_id());
        assertEquals("MutatedDesc", dto.getDescription());
        assertEquals(List.of(42L, 43L), dto.getUsers());
        assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0), dto.getCreatedAt());
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), dto.getUpdatedAt());
    }

    @Test
    void testEqualsHashCodeCombinations() {
        SessionDto base = new SessionDto(1L, "A", new Date(1), 2L, "desc", List.of(1L), LocalDateTime.now(), LocalDateTime.now());
        // All nulls
        SessionDto allNull = new SessionDto(null, null, null, null, null, null, null, null);
        assertNotEquals(base, allNull);
        assertNotEquals(base.hashCode(), allNull.hashCode());
        // Only id null
        SessionDto idNull = new SessionDto(null, "A", new Date(1), 2L, "desc", List.of(1L), base.getCreatedAt(), base.getUpdatedAt());
        assertNotEquals(base, idNull);
        // Only name null
        SessionDto nameNull = new SessionDto(1L, null, new Date(1), 2L, "desc", List.of(1L), base.getCreatedAt(), base.getUpdatedAt());
        assertNotEquals(base, nameNull);
        // Only date null
        SessionDto dateNull = new SessionDto(1L, "A", null, 2L, "desc", List.of(1L), base.getCreatedAt(), base.getUpdatedAt());
        assertNotEquals(base, dateNull);
        // Only teacher_id null
        SessionDto teacherNull = new SessionDto(1L, "A", new Date(1), null, "desc", List.of(1L), base.getCreatedAt(), base.getUpdatedAt());
        assertNotEquals(base, teacherNull);
        // Only description null
        SessionDto descNull = new SessionDto(1L, "A", new Date(1), 2L, null, List.of(1L), base.getCreatedAt(), base.getUpdatedAt());
        assertNotEquals(base, descNull);
        // Only users null
        SessionDto usersNull = new SessionDto(1L, "A", new Date(1), 2L, "desc", null, base.getCreatedAt(), base.getUpdatedAt());
        assertNotEquals(base, usersNull);
        // Only createdAt null
        SessionDto createdNull = new SessionDto(1L, "A", new Date(1), 2L, "desc", List.of(1L), null, base.getUpdatedAt());
        assertNotEquals(base, createdNull);
        // Only updatedAt null
        SessionDto updatedNull = new SessionDto(1L, "A", new Date(1), 2L, "desc", List.of(1L), base.getCreatedAt(), null);
        assertNotEquals(base, updatedNull);
    }
        @Test
        void testNullAndEmptyFields() {
            SessionDto dto = new SessionDto();
            assertNull(dto.getId());
            assertNull(dto.getName());
            assertNull(dto.getDate());
            assertNull(dto.getTeacher_id());
            assertNull(dto.getDescription());
            assertNull(dto.getUsers());
            assertNull(dto.getCreatedAt());
            assertNull(dto.getUpdatedAt());
            dto.setUsers(List.of());
            assertTrue(dto.getUsers().isEmpty());
        }

        @Test
        void testToString() {
            SessionDto dto = new SessionDto();
            dto.setName("TestSession");
            assertTrue(dto.toString().contains("TestSession"));
        }
    @Test
    void allArgsConstructor_and_getters_shouldWork() {
        LocalDateTime now = LocalDateTime.now();
        Date date = new Date();
        List<Long> users = List.of(1L, 2L);
        SessionDto dto = new SessionDto(1L, "Yoga", date, 3L, "desc", users, now, now);
        assertEquals(1L, dto.getId());
        assertEquals("Yoga", dto.getName());
        assertEquals(date, dto.getDate());
        assertEquals(3L, dto.getTeacher_id());
        assertEquals("desc", dto.getDescription());
        assertEquals(users, dto.getUsers());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void noArgsConstructor_and_setters_shouldWork() {
        SessionDto dto = new SessionDto();
        dto.setId(2L);
        dto.setName("Pilates");
        Date date = new Date();
        dto.setDate(date);
        dto.setTeacher_id(4L);
        dto.setDescription("desc2");
        List<Long> users = List.of(5L, 6L);
        dto.setUsers(users);
        LocalDateTime now = LocalDateTime.now();
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);
        assertEquals(2L, dto.getId());
        assertEquals("Pilates", dto.getName());
        assertEquals(date, dto.getDate());
        assertEquals(4L, dto.getTeacher_id());
        assertEquals("desc2", dto.getDescription());
        assertEquals(users, dto.getUsers());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void testSessionDtoProperties() {
        LocalDateTime now = LocalDateTime.now();
        Date date = new Date();
        List<Long> users = Arrays.asList(1L, 2L);
        SessionDto dto = new SessionDto(1L, "Yoga", date, 10L, "desc", users, now, now);
        assertEquals(1L, dto.getId());
        assertEquals("Yoga", dto.getName());
        assertEquals(date, dto.getDate());
        assertEquals(10L, dto.getTeacher_id());
        assertEquals("desc", dto.getDescription());
        assertEquals(users, dto.getUsers());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void testValidationAndEdgeCases() {
        SessionDto dto = new SessionDto();
        // Test blank name
        dto.setName("");
        assertEquals("", dto.getName());
        // Test max size name
        String longName = "a".repeat(50);
        dto.setName(longName);
        assertEquals(longName, dto.getName());
        // Test description max size
        String longDesc = "b".repeat(2500);
        dto.setDescription(longDesc);
        assertEquals(longDesc, dto.getDescription());
        // Test null users
        dto.setUsers(null);
        assertNull(dto.getUsers());
        // Test empty users
        dto.setUsers(List.of());
        assertTrue(dto.getUsers().isEmpty());
        // Test edge dates
        dto.setDate(new Date(Long.MAX_VALUE));
        assertEquals(Long.MAX_VALUE, dto.getDate().getTime());
        dto.setCreatedAt(LocalDateTime.MIN);
        dto.setUpdatedAt(LocalDateTime.MAX);
        assertEquals(LocalDateTime.MIN, dto.getCreatedAt());
        assertEquals(LocalDateTime.MAX, dto.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        SessionDto dto1 = new SessionDto(1L, "Yoga", null, 10L, "desc", null, null, null);
        SessionDto dto2 = new SessionDto(1L, "Yoga", null, 10L, "desc", null, null, null);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        // Not equals to null
        assertNotEquals(dto1, null);
        // Not equals to other type
        assertNotEquals(dto1, "string");
        // Not equals to different id
        SessionDto dto3 = new SessionDto(2L, "Yoga", null, 10L, "desc", null, null, null);
        assertNotEquals(dto1, dto3);
        // Mutation after construction
        dto1.setName("Other");
        assertNotEquals(dto1, dto2);
        // Extreme values
        SessionDto dtoExtreme = new SessionDto(Long.MAX_VALUE, "", null, Long.MIN_VALUE, "", List.of(), null, null);
        assertEquals(Long.MAX_VALUE, dtoExtreme.getId());
        assertEquals(Long.MIN_VALUE, dtoExtreme.getTeacher_id());
        // List null/empty
        SessionDto dtoListNull = new SessionDto(1L, "Yoga", null, 10L, "desc", null, null, null);
        assertNull(dtoListNull.getUsers());
        SessionDto dtoListEmpty = new SessionDto(1L, "Yoga", null, 10L, "desc", List.of(), null, null);
        assertTrue(dtoListEmpty.getUsers().isEmpty());
    }

    @Test
    void testEqualsAndHashCodeAdvanced() {
        SessionDto dto1 = new SessionDto(1L, "Yoga", new Date(1), 2L, "desc", List.of(1L), LocalDateTime.now(), LocalDateTime.now());
        SessionDto dto2 = new SessionDto(1L, "Yoga", new Date(1), 2L, "desc", List.of(1L), dto1.getCreatedAt(), dto1.getUpdatedAt());
        SessionDto dto3 = new SessionDto(2L, "Pilates", new Date(2), 3L, "desc2", List.of(2L), LocalDateTime.now(), LocalDateTime.now());
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testToStringSpecialValues() {
        SessionDto dto = new SessionDto();
        dto.setName(null);
        dto.setDescription(null);
        assertNotNull(dto.toString());
        dto.setName("\n\t\u2603");
        assertTrue(dto.toString().contains("\u2603"));
    }
        @Test
        void testMutationToNullAfterInit() {
            LocalDateTime now = LocalDateTime.now();
            Date date = new Date();
            List<Long> users = List.of(1L, 2L);
            SessionDto dto = new SessionDto(1L, "Yoga", date, 3L, "desc", users, now, now);
            // Mutate all to null
            dto.setId(null);
            dto.setName(null);
            dto.setDate(null);
            dto.setTeacher_id(null);
            dto.setDescription(null);
            dto.setUsers(null);
            dto.setCreatedAt(null);
            dto.setUpdatedAt(null);
            assertNull(dto.getId());
            assertNull(dto.getName());
            assertNull(dto.getDate());
            assertNull(dto.getTeacher_id());
            assertNull(dto.getDescription());
            assertNull(dto.getUsers());
            assertNull(dto.getCreatedAt());
            assertNull(dto.getUpdatedAt());
            // equals/hashCode with all nulls
            SessionDto allNull = new SessionDto(null, null, null, null, null, null, null, null);
            assertEquals(dto, allNull);
            assertEquals(dto.hashCode(), allNull.hashCode());
            // toString should not throw
            assertNotNull(dto.toString());
        }
}
