package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageResponseTest {
    @Test
    void testMessageResponseProperties() {
        MessageResponse resp = new MessageResponse("ok");
        assertEquals("ok", resp.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        MessageResponse resp1 = new MessageResponse("ok");
        MessageResponse resp2 = new MessageResponse("ok");
        assertEquals(resp1, resp2);
        assertEquals(resp1.hashCode(), resp2.hashCode());
    }

        @Test
        void testToStringAndSetters() {
            MessageResponse resp = new MessageResponse("hello");
            resp.setMessage("world");
            assertEquals("world", resp.getMessage());
            assertTrue(resp.toString().contains("world"));
        }
}
