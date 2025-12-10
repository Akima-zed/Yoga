package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {
    @Test
    void constructorAndGetters_shouldWork() {
        JwtResponse res = new JwtResponse("token", 1L, "user", "John", "Doe", true);
        assertEquals("token", res.getToken());
        assertEquals(1L, res.getId());
        assertEquals("user", res.getUsername());
        assertEquals("John", res.getFirstName());
        assertEquals("Doe", res.getLastName());
        assertTrue(res.getAdmin());
        assertEquals("Bearer", res.getType());
    }
}
