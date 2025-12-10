package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionDtoValidationTest {
    private final Validator validator;

    public SessionDtoValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void nameShouldNotBeBlank() {
        SessionDto dto = new SessionDto(1L, "", new Date(), 2L, "desc", List.of(1L), LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<SessionDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void nameShouldNotExceedMaxSize() {
        String longName = "a".repeat(51);
        SessionDto dto = new SessionDto(1L, longName, new Date(), 2L, "desc", List.of(1L), LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<SessionDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void descriptionShouldNotExceedMaxSize() {
        String longDesc = "a".repeat(2501);
        SessionDto dto = new SessionDto(1L, "Yoga", new Date(), 2L, longDesc, List.of(1L), LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<SessionDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }
}
