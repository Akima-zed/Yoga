package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestValidationTest {
        @Test
        void testGettersSettersEqualsHashCodeToString() {
            LoginRequest req1 = new LoginRequest();
            req1.setEmail("john.doe@example.com");
            req1.setPassword("password123");

            LoginRequest req2 = new LoginRequest();
            req2.setEmail("john.doe@example.com");
            req2.setPassword("password123");

            // Test getters
            assertEquals("john.doe@example.com", req1.getEmail());
            assertEquals("password123", req1.getPassword());

            // Test equals and hashCode
            assertEquals(req1, req2);
            assertEquals(req1.hashCode(), req2.hashCode());

            // Test toString
            assertTrue(req1.toString().contains("john.doe@example.com"));
            assertTrue(req1.toString().contains("password123"));
        }
    private final Validator validator;

    public LoginRequestValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void emailShouldNotBeBlank() {
        LoginRequest req = new LoginRequest();
        req.setEmail("");
        req.setPassword("password");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void passwordShouldNotBeBlank() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

        @Test
        void validLoginRequestShouldHaveNoViolations() {
            LoginRequest req = new LoginRequest();
            req.setEmail("john.doe@example.com");
            req.setPassword("password123");
            Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
            assertTrue(violations.isEmpty());
        }
}
