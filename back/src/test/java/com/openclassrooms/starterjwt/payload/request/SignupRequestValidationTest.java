package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestValidationTest {
    private final Validator validator;

    public SignupRequestValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void emailShouldBeValid() {
        SignupRequest req = new SignupRequest();
        req.setEmail("invalid-email");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setPassword("password");
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void emailShouldNotBeBlank() {
        SignupRequest req = new SignupRequest();
        req.setEmail("");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setPassword("password");
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void firstNameShouldNotBeBlankOrTooShort() {
        SignupRequest req = new SignupRequest();
        req.setEmail("test@test.com");
        req.setFirstName("");
        req.setLastName("Doe");
        req.setPassword("password");
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
        req.setFirstName("Jo");
        violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    void lastNameShouldNotBeBlankOrTooShort() {
        SignupRequest req = new SignupRequest();
        req.setEmail("test@test.com");
        req.setFirstName("John");
        req.setLastName("");
        req.setPassword("password");
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
        req.setLastName("Do");
        violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
    }

    @Test
    void passwordShouldNotBeBlankOrTooShort() {
        SignupRequest req = new SignupRequest();
        req.setEmail("test@test.com");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setPassword("");
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
        req.setPassword("12345");
        violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
}
