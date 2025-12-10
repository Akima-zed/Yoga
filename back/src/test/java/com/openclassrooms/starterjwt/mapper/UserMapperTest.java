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

        @Test
        void toEntity_shouldMapDtoListToUserList() {
            UserDto dto1 = new UserDto();
            dto1.setId(1L);
            dto1.setEmail("a@test.com");
            UserDto dto2 = new UserDto();
            dto2.setId(2L);
            dto2.setEmail("b@test.com");
            var dtoList = java.util.Arrays.asList(dto1, dto2);
            var userList = mapper.toEntity(dtoList);
            assertEquals(2, userList.size());
            assertEquals(dto1.getId(), userList.get(0).getId());
            assertEquals(dto2.getId(), userList.get(1).getId());
        }

        @Test
        void toDto_shouldMapUserListToDtoList() {
            User user1 = new User();
            user1.setId(1L);
            user1.setEmail("a@test.com");
            User user2 = new User();
            user2.setId(2L);
            user2.setEmail("b@test.com");
            var userList = java.util.Arrays.asList(user1, user2);
            var dtoList = mapper.toDto(userList);
            assertEquals(2, dtoList.size());
            assertEquals(user1.getId(), dtoList.get(0).getId());
            assertEquals(user2.getId(), dtoList.get(1).getId());
        }
}
