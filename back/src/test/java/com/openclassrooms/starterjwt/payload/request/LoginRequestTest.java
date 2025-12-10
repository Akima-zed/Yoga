package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import jakarta.validation.ConstraintViolation;

class LoginRequestTest {
    @Test
    void testLoginRequestProperties() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("pass123");
        assertEquals("test@test.com", req.getEmail());
        assertEquals("pass123", req.getPassword());
    }

    @Test
    void testLoginRequestValidationConstraints() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        LoginRequest req = new LoginRequest();
        req.setEmail(""); // invalid
        req.setPassword(""); // invalid
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testLoginRequestValid() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        LoginRequest req = new LoginRequest();
        req.setEmail("valid@email.com");
        req.setPassword("password123");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty());
    }
}
