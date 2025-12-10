package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MessageResponseValidationTest {
    private final Validator validator;

    public MessageResponseValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void messageShouldNotBeNull() {
        MessageResponse resp = new MessageResponse(null);
        Set<ConstraintViolation<MessageResponse>> violations = validator.validate(resp);
        // Pas d'annotation, donc pas de violation attendue
        assertTrue(violations.isEmpty());
    }
}
