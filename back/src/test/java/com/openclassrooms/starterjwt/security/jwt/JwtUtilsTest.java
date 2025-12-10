package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilsTest {
    private JwtUtils jwtUtils;
    private final String jwtSecret = "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef";
    private final int jwtExpirationMs = 60000;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        // Utilisation de la réflexion pour injecter les valeurs des propriétés
        try {
            var secretField = JwtUtils.class.getDeclaredField("jwtSecret");
            secretField.setAccessible(true);
            secretField.set(jwtUtils, jwtSecret);
            var expField = JwtUtils.class.getDeclaredField("jwtExpirationMs");
            expField.setAccessible(true);
            expField.set(jwtUtils, jwtExpirationMs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGenerateJwtTokenAndGetUserName() {
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");

        String token = jwtUtils.generateJwtToken(authentication);
        assertNotNull(token);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        assertEquals("testuser", username);
    }

    @Test
    void testValidateJwtToken_Valid() {
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("validuser");
        String token = jwtUtils.generateJwtToken(authentication);
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_Invalid() {
        String invalidToken = "invalid.token.value";
        assertFalse(jwtUtils.validateJwtToken(invalidToken));
    }

    @Test
    void testValidateJwtToken_Expired() throws InterruptedException {
        JwtUtils shortExpJwtUtils = new JwtUtils();
        try {
            var secretField = JwtUtils.class.getDeclaredField("jwtSecret");
            secretField.setAccessible(true);
            secretField.set(shortExpJwtUtils, jwtSecret);
            var expField = JwtUtils.class.getDeclaredField("jwtExpirationMs");
            expField.setAccessible(true);
            expField.set(shortExpJwtUtils, 1); // 1 ms
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("expireduser");
        String token = shortExpJwtUtils.generateJwtToken(authentication);
        Thread.sleep(5); // attendre l'expiration
        assertFalse(shortExpJwtUtils.validateJwtToken(token));
    }
}
