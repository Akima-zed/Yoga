package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {
    @Test
    void gettersAndSetters_shouldWork() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("pass");
        assertEquals("test@test.com", req.getEmail());
        assertEquals("pass", req.getPassword());
    }
}
