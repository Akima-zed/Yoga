package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtResponseValidationTest {
    @Test
    void allArgsConstructor_and_getters_shouldWork() {
        JwtResponse resp = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        assertEquals("token", resp.getToken());
        assertEquals("Bearer", resp.getType());
        assertEquals(1L, resp.getId());
        assertEquals("username", resp.getUsername());
        assertEquals("John", resp.getFirstName());
        assertEquals("Doe", resp.getLastName());
        assertTrue(resp.getAdmin());
    }
}
