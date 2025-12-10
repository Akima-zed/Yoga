package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    void findById_shouldReturnUser_whenUserExists() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_shouldThrowNotFound_whenUserNotExists() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findById(2L));
    }

    @Test
    void findByEmail_shouldReturnUser_whenUserExists() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        User result = userService.findByEmail("test@test.com");
        assertEquals("test@test.com", result.getEmail());
    }

    @Test
    void findByEmail_shouldThrowNotFound_whenUserNotExists() {
        when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findByEmail("notfound@test.com"));
    }

    @Test
    void create_shouldSaveUser_whenEmailNotUsed() {
        when(userRepository.existsByEmail("new@test.com")).thenReturn(false);
        User userToSave = User.builder()
                .email("new@test.com")
                .lastName("Doe")
                .firstName("John")
                .password("pass")
                .admin(false)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(userToSave);
        User result = userService.create("new@test.com", "Doe", "John", "pass", false);
        assertEquals("new@test.com", result.getEmail());
        assertEquals("Doe", result.getLastName());
        assertEquals("John", result.getFirstName());
        assertFalse(result.isAdmin());
    }

    @Test
    void create_shouldThrowBadRequest_whenEmailUsed() {
        when(userRepository.existsByEmail("used@test.com")).thenReturn(true);
        assertThrows(com.openclassrooms.starterjwt.exception.BadRequestException.class,
                () -> userService.create("used@test.com", "Doe", "John", "pass", false));
    }

    @Test
    void delete_shouldDeleteUser_whenUserExists() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);
        assertDoesNotThrow(() -> userService.delete(1L));
        verify(userRepository).delete(user);
    }

    @Test
    void delete_shouldThrowNotFound_whenUserNotExists() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.delete(2L));
    }

    @Test
    void existsByEmail_shouldReturnTrue_whenEmailExists() {
        when(userRepository.existsByEmail("exists@test.com")).thenReturn(true);
        assertTrue(userService.existsByEmail("exists@test.com"));
    }

    @Test
    void existsByEmail_shouldReturnFalse_whenEmailNotExists() {
        when(userRepository.existsByEmail("notexists@test.com")).thenReturn(false);
        assertFalse(userService.existsByEmail("notexists@test.com"));
    }
}
