package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toDto_shouldMapUserToDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setAdmin(true);
        UserDto dto = mapper.toDto(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getLastName(), dto.getLastName());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertTrue(dto.isAdmin());
    }

    @Test
    void toEntity_shouldMapDtoToUser() {
        UserDto dto = new UserDto();
        dto.setId(2L);
        dto.setEmail("user2@test.com");
        dto.setLastName("Smith");
        dto.setFirstName("Alice");
        dto.setAdmin(false);
        User user = mapper.toEntity(dto);
        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getLastName(), user.getLastName());
        assertEquals(dto.getFirstName(), user.getFirstName());
        assertFalse(user.isAdmin());
    }
}
