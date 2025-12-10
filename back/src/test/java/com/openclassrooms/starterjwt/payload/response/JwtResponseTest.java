package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {
    @Test
    void testJwtResponseProperties() {
        JwtResponse resp = new JwtResponse("token", 1L, "user", "John", "Doe", true);
        assertEquals("token", resp.getToken());
        assertEquals("Bearer", resp.getType());
        assertEquals(1L, resp.getId());
        assertEquals("user", resp.getUsername());
        assertEquals("John", resp.getFirstName());
        assertEquals("Doe", resp.getLastName());
        assertTrue(resp.getAdmin());
    }

    @Test
    void testJwtResponseSettersAndToString() {
        JwtResponse resp = new JwtResponse("token", 1L, "user", "John", "Doe", true);
        resp.setToken("newtoken");
        resp.setType("Custom");
        resp.setId(2L);
        resp.setUsername("newuser");
        resp.setFirstName("Jane");
        resp.setLastName("Smith");
        resp.setAdmin(false);
        assertEquals("newtoken", resp.getToken());
        assertEquals("Custom", resp.getType());
        assertEquals(2L, resp.getId());
        assertEquals("newuser", resp.getUsername());
        assertEquals("Jane", resp.getFirstName());
        assertEquals("Smith", resp.getLastName());
        assertFalse(resp.getAdmin());
    }
}
