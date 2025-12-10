package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SignupRequestTest {
    @Test
    void gettersAndSetters_shouldWork() {
        SignupRequest req = new SignupRequest();
        req.setEmail("test@test.com");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setPassword("pass");
        assertEquals("test@test.com", req.getEmail());
        assertEquals("John", req.getFirstName());
        assertEquals("Doe", req.getLastName());
        assertEquals("pass", req.getPassword());
    }
}
