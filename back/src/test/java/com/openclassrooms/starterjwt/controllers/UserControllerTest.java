package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.exception.UnauthorizedException;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserController userController;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findById_shouldReturnUserDto() {
        User user = new User();
        UserDto dto = new UserDto();
        when(userService.findById(1L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(dto);
        ResponseEntity<UserDto> response = userController.findById(1L);
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void delete_shouldReturnOk_whenAuthorized() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userService.findById(1L)).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@test.com");
        doNothing().when(userService).delete(1L);
        ResponseEntity<Void> response = userController.delete(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).delete(1L);
    }

    @Test
    void delete_shouldThrowUnauthorized_whenNotAuthorized() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userService.findById(1L)).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("other@test.com");
        assertThrows(UnauthorizedException.class, () -> userController.delete(1L));
    }
}
