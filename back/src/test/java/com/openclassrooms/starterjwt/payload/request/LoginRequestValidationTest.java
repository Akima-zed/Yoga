package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestValidationTest {
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
}
