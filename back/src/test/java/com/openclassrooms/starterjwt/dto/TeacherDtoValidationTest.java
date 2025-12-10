package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TeacherDtoValidationTest {
    private final Validator validator;

    public TeacherDtoValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void lastNameShouldNotBeBlank() {
        TeacherDto dto = new TeacherDto(1L, "", "John", LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<TeacherDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
    }

    @Test
    void firstNameShouldNotBeBlank() {
        TeacherDto dto = new TeacherDto(1L, "Doe", "", LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<TeacherDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    void lastNameShouldNotExceedMaxSize() {
        String longLastName = "a".repeat(21);
        TeacherDto dto = new TeacherDto(1L, longLastName, "John", LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<TeacherDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
    }

    @Test
    void firstNameShouldNotExceedMaxSize() {
        String longFirstName = "a".repeat(21);
        TeacherDto dto = new TeacherDto(1L, "Doe", longFirstName, LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<TeacherDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }
}
