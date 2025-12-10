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
}
