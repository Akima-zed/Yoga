package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoValidationTest {
    private final Validator validator;

    public UserDtoValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void emailShouldBeValid() {
        UserDto dto = new UserDto(1L, "invalid-email", "Doe", "John", true, "pass", LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void emailShouldNotExceedMaxSize() {
        String longEmail = "a".repeat(51) + "@test.com";
        UserDto dto = new UserDto(1L, longEmail, "Doe", "John", true, "pass", LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void lastNameShouldNotExceedMaxSize() {
        String longLastName = "a".repeat(21);
        UserDto dto = new UserDto(1L, "test@test.com", longLastName, "John", true, "pass", LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
    }

    @Test
    void firstNameShouldNotExceedMaxSize() {
        String longFirstName = "a".repeat(21);
        UserDto dto = new UserDto(1L, "test@test.com", "Doe", longFirstName, true, "pass", LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    void passwordShouldNotExceedMaxSize() {
        String longPassword = "a".repeat(121);
        UserDto dto = new UserDto(1L, "test@test.com", "Doe", "John", true, longPassword, LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
}
