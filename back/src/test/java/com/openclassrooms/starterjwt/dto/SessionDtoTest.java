package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

class SessionDtoTest {
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
}
