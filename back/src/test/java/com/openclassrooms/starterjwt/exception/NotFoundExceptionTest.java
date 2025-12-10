package com.openclassrooms.starterjwt.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {
    @Test
    void constructor_shouldSetMessage() {
        NotFoundException ex = new NotFoundException("not found");
        assertEquals("not found", ex.getMessage());
    }
}
