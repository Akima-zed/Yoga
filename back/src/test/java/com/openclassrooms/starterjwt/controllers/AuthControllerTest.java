package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserService userService;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateUser_shouldReturnJwtResponse() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com");
        loginRequest.setPassword("pass");
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwt-token");
        when(userDetails.getUsername()).thenReturn("test@test.com");
        when(userDetails.getId()).thenReturn(1L);
        when(userDetails.getFirstName()).thenReturn("John");
        when(userDetails.getLastName()).thenReturn("Doe");
        User user = new User();
        user.setAdmin(true);
        when(userService.findByEmail("test@test.com")).thenReturn(user);
        ResponseEntity<JwtResponse> response = authController.authenticateUser(loginRequest);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals("jwt-token", response.getBody().getToken());
            assertEquals(1L, response.getBody().getId());
            assertEquals("test@test.com", response.getBody().getUsername());
            assertEquals("John", response.getBody().getFirstName());
            assertEquals("Doe", response.getBody().getLastName());
            assertTrue(response.getBody().getAdmin());
    }

    @Test
    void registerUser_shouldReturnMessageResponse() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("new@test.com");
        signupRequest.setLastName("Doe");
        signupRequest.setFirstName("John");
        signupRequest.setPassword("pass");
        when(userService.existsByEmail("new@test.com")).thenReturn(false);
        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(userService.create(anyString(), anyString(), anyString(), anyString(), anyBoolean())).thenReturn(new User());
        ResponseEntity<MessageResponse> response = authController.registerUser(signupRequest);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", response.getBody().getMessage());
    }

    @Test
    void registerUser_shouldThrowBadRequest_whenEmailExists() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("exists@test.com");
        when(userService.existsByEmail("exists@test.com")).thenReturn(true);
        assertThrows(BadRequestException.class, () -> authController.registerUser(signupRequest));
    }
}
