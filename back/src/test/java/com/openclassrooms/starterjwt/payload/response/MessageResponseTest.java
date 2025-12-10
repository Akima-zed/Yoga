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

            @Test
            void testEqualsAdvanced() {
                MessageResponse resp1 = new MessageResponse("ok");
                MessageResponse resp2 = new MessageResponse("ok");
                assertEquals(resp1, resp2);
                assertEquals(resp1.hashCode(), resp2.hashCode());
                // Not equals to null
                assertNotEquals(resp1, null);
                // Not equals to other type
                assertNotEquals(resp1, "string");
                // Not equals to different message
                MessageResponse resp3 = new MessageResponse("other");
                assertNotEquals(resp1, resp3);
                // Mutation after construction
                resp1.setMessage("mutated");
                assertNotEquals(resp1, resp2);
                // Extreme values
                MessageResponse respExtreme = new MessageResponse("");
                assertEquals("", respExtreme.getMessage());
                    // Null message in one
                    MessageResponse respNull = new MessageResponse(null);
                    assertNotEquals(resp1, respNull);
                    assertNotEquals(respNull, resp1);
                    // Both null
                    MessageResponse respNull2 = new MessageResponse(null);
                    assertEquals(respNull, respNull2);
                    assertEquals(respNull.hashCode(), respNull2.hashCode());
                    // Reflexivity
                    assertEquals(resp1, resp1);
                    // Symmetry
                    assertEquals(resp1.equals(resp2), resp2.equals(resp1));
                    // Transitivity
                    MessageResponse t1 = new MessageResponse("ok");
                    MessageResponse t2 = new MessageResponse("ok");
                    MessageResponse t3 = new MessageResponse("ok");
                    assertTrue(t1.equals(t2) && t2.equals(t3) && t1.equals(t3));
                    // Consistency
                    assertEquals(resp1.equals(resp2), resp1.equals(resp2));
                    // toString covers null
                    assertTrue(respNull.toString().contains("message"));
        }
}
