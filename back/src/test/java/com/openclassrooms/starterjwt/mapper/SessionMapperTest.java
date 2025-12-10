package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class SessionMapperTest {
    SessionMapper mapper = Mappers.getMapper(SessionMapper.class);

    @Test
    void toDto_shouldMapSessionToDto() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga du matin");
        SessionDto dto = mapper.toDto(session);
        assertEquals(session.getId(), dto.getId());
        assertEquals(session.getName(), dto.getName());
    }

    @Test
    void toEntity_shouldMapDtoToSession() {
        SessionDto dto = new SessionDto();
        dto.setId(2L);
        dto.setName("Yoga du soir");
        Session session = mapper.toEntity(dto);
        assertEquals(dto.getId(), session.getId());
        assertEquals(dto.getName(), session.getName());
    }

        @Test
        void toDto_shouldHandleNullSession() {
            SessionDto dto = mapper.toDto((Session) null);
            assertNull(dto);
        }

        @Test
        void toEntity_shouldHandleNullDto() {
            Session session = mapper.toEntity((SessionDto) null);
            assertNull(session);
        }

        @Test
        void toDto_shouldHandleEmptyUsers() {
            Session session = new Session();
            session.setUsers(null);
            SessionDto dto = mapper.toDto(session);
            assertNotNull(dto);
            assertNotNull(dto.getUsers());
            assertTrue(dto.getUsers().isEmpty());
        }

        @Test
        void toEntity_shouldMapDtoListToSessionList() {
            SessionDto dto1 = new SessionDto();
            dto1.setId(1L);
            SessionDto dto2 = new SessionDto();
            dto2.setId(2L);
            var dtoList = java.util.Arrays.asList(dto1, dto2);
            var sessionList = mapper.toEntity(dtoList);
            assertEquals(2, sessionList.size());
            assertEquals(dto1.getId(), sessionList.get(0).getId());
            assertEquals(dto2.getId(), sessionList.get(1).getId());
        }

        @Test
        void toDto_shouldMapSessionListToDtoList() {
            Session session1 = new Session();
            session1.setId(1L);
            Session session2 = new Session();
            session2.setId(2L);
            var sessionList = java.util.Arrays.asList(session1, session2);
            var dtoList = mapper.toDto(sessionList);
            assertEquals(2, dtoList.size());
            assertEquals(session1.getId(), dtoList.get(0).getId());
            assertEquals(session2.getId(), dtoList.get(1).getId());
        }
}
