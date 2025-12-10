package com.openclassrooms.starterjwt.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.*;

class ApiErrorTest {
    @Test
    void constructor_and_getters_shouldWork() {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Erreur de requête");
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.name(), error.getError());
        assertEquals("Erreur de requête", error.getMessage());
        assertNotNull(error.getTimestamp());
    }
}
