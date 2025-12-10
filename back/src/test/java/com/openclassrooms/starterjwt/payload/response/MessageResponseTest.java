package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageResponseTest {
    @Test
    void constructorAndGetter_shouldWork() {
        MessageResponse res = new MessageResponse("ok");
        assertEquals("ok", res.getMessage());
    }
}
