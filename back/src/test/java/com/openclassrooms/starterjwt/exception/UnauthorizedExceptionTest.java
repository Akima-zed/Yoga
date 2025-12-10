package com.openclassrooms.starterjwt.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UnauthorizedExceptionTest {
    @Test
    void constructor_shouldSetMessage() {
        UnauthorizedException ex = new UnauthorizedException("unauthorized");
        assertEquals("unauthorized", ex.getMessage());
    }
}
