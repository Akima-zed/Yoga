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

    @Test
    void testValidateJwtToken_ExpiredJwtException() throws InterruptedException {
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

    @Test
    void testValidateJwtToken_EmptyToken() {
        assertFalse(jwtUtils.validateJwtToken(""));
    }

    @Test
    void testValidateJwtToken_NullToken() {
        assertFalse(jwtUtils.validateJwtToken(null));
    }

    @Test
    void testValidateJwtToken_UnsupportedToken() {
        // Un token qui n'est pas JWT
        assertFalse(jwtUtils.validateJwtToken("not.a.jwt.token"));
    }

    @Test
    void testValidateJwtToken_SignatureException() {
        // Token avec une signature invalide (clé différente, 512 bits)
        String validKey = "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef"; // 64 chars
        String otherKey = "abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789"; // 64 chars
        JwtUtils validJwtUtils = new JwtUtils();
        JwtUtils otherJwtUtils = new JwtUtils();
        try {
            var secretField = JwtUtils.class.getDeclaredField("jwtSecret");
            secretField.setAccessible(true);
            secretField.set(validJwtUtils, validKey);
            secretField.set(otherJwtUtils, otherKey);
            var expField = JwtUtils.class.getDeclaredField("jwtExpirationMs");
            expField.setAccessible(true);
            expField.set(validJwtUtils, jwtExpirationMs);
            expField.set(otherJwtUtils, jwtExpirationMs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("siguser");
        String token = validJwtUtils.generateJwtToken(authentication);
        // Le token généré avec la clé d'origine ne doit pas passer avec une autre clé
        assertFalse(otherJwtUtils.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_MalformedJwtException() {
        // Token mal formé (trop de points)
        String malformedToken = "abc.def.ghi.jkl";
        assertFalse(jwtUtils.validateJwtToken(malformedToken));
    }

    @Test
    void testValidateJwtToken_IllegalArgumentException() {
        // Token null ou vide déjà testé, mais on peut tester un token non string
        assertFalse(jwtUtils.validateJwtToken(null));
        assertFalse(jwtUtils.validateJwtToken(""));
    }

    @Test
    void testValidateJwtToken_UnsupportedJwtException() {
        // Token non supporté (exemple : JWE au lieu de JWS)
        // Ici, on simule un token JWE (4 points)
        String unsupportedToken = "a.b.c.d.e";
        assertFalse(jwtUtils.validateJwtToken(unsupportedToken));
    }
}
