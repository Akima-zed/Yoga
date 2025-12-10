package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import jakarta.validation.ConstraintViolation;

class SignupRequestTest {
    @Test
    void testSignupRequestProperties() {
        SignupRequest req = new SignupRequest();
        req.setEmail("test@test.com");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setPassword("pass123");
        assertEquals("test@test.com", req.getEmail());
        assertEquals("John", req.getFirstName());
        assertEquals("Doe", req.getLastName());
        assertEquals("pass123", req.getPassword());
    }

    @Test
    void testSignupRequestValidationConstraints() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        SignupRequest req = new SignupRequest();
        req.setEmail(""); // invalid
        req.setFirstName("A"); // too short
        req.setLastName(""); // invalid
        req.setPassword("123"); // too short
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testSignupRequestValid() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        SignupRequest req = new SignupRequest();
        req.setEmail("valid@email.com");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setPassword("password123");
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty());
    }
}
