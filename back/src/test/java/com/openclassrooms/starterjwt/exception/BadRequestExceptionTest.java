package com.openclassrooms.starterjwt.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BadRequestExceptionTest {
    @Test
    void constructor_shouldSetMessage() {
        BadRequestException ex = new BadRequestException("bad request");
        assertEquals("bad request", ex.getMessage());
    }
}
