package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestValidationTest {
        @Test
        void testGettersSettersEqualsHashCodeToString() {
            SignupRequest req1 = new SignupRequest();
            req1.setEmail("john.doe@example.com");
            req1.setFirstName("John");
            req1.setLastName("Doe");
            req1.setPassword("password123");

            SignupRequest req2 = new SignupRequest();
            req2.setEmail("john.doe@example.com");
            req2.setFirstName("John");
            req2.setLastName("Doe");
            req2.setPassword("password123");

            // Test getters
            assertEquals("john.doe@example.com", req1.getEmail());
            assertEquals("John", req1.getFirstName());
            assertEquals("Doe", req1.getLastName());
            assertEquals("password123", req1.getPassword());

            // Test equals and hashCode
            assertEquals(req1, req2);
            assertEquals(req1.hashCode(), req2.hashCode());

            // Test toString
            assertTrue(req1.toString().contains("john.doe@example.com"));
            assertTrue(req1.toString().contains("John"));
            assertTrue(req1.toString().contains("Doe"));
            assertTrue(req1.toString().contains("password123"));
        }
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
        void validSignupRequestShouldHaveNoViolations() {
            SignupRequest req = new SignupRequest();
            req.setEmail("john.doe@example.com");
            req.setFirstName("John");
            req.setLastName("Doe");
            req.setPassword("password123");
            Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
            assertTrue(violations.isEmpty());
        }

        @Test
        void firstNameMinAndMaxLength() {
            SignupRequest req = new SignupRequest();
            req.setEmail("john.doe@example.com");
            req.setLastName("Doe");
            req.setPassword("password123");
            req.setFirstName("Joh"); // min = 3
            Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
            assertTrue(violations.isEmpty());
            req.setFirstName("J".repeat(20)); // max = 20
            violations = validator.validate(req);
            assertTrue(violations.isEmpty());
            req.setFirstName("J".repeat(21)); // max + 1
            violations = validator.validate(req);
            assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
        }

        @Test
        void lastNameMinAndMaxLength() {
            SignupRequest req = new SignupRequest();
            req.setEmail("john.doe@example.com");
            req.setFirstName("John");
            req.setPassword("password123");
            req.setLastName("Doe"); // min = 3
            Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
            assertTrue(violations.isEmpty());
            req.setLastName("D".repeat(20)); // max = 20
            violations = validator.validate(req);
            assertTrue(violations.isEmpty());
            req.setLastName("D".repeat(21)); // max + 1
            violations = validator.validate(req);
            assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
        }

        @Test
        void passwordMinAndMaxLength() {
            SignupRequest req = new SignupRequest();
            req.setEmail("john.doe@example.com");
            req.setFirstName("John");
            req.setLastName("Doe");
            req.setPassword("123456"); // min = 6
            Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
            assertTrue(violations.isEmpty());
            req.setPassword("p".repeat(40)); // max = 40
            violations = validator.validate(req);
            assertTrue(violations.isEmpty());
            req.setPassword("p".repeat(41)); // max + 1
            violations = validator.validate(req);
            assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
        }

        @Test
        void emailMaxLength() {
            SignupRequest req = new SignupRequest();
            req.setFirstName("John");
            req.setLastName("Doe");
            req.setPassword("password123");
            req.setEmail("a".repeat(51) + "@test.com"); // max = 50
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
