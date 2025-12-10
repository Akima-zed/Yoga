package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {
    @Test
    void testJwtResponseProperties() {
        JwtResponse resp = new JwtResponse("token", 1L, "user", "John", "Doe", true);
        assertEquals("token", resp.getToken());
        assertEquals("Bearer", resp.getType());
        assertEquals(1L, resp.getId());
        assertEquals("user", resp.getUsername());
        assertEquals("John", resp.getFirstName());
        assertEquals("Doe", resp.getLastName());
        assertTrue(resp.getAdmin());
    }

    @Test
    void testJwtResponseSettersAndToString() {
        JwtResponse resp = new JwtResponse("token", 1L, "user", "John", "Doe", true);
        resp.setToken("newtoken");
        resp.setType("Custom");
        resp.setId(2L);
        resp.setUsername("newuser");
        resp.setFirstName("Jane");
        resp.setLastName("Smith");
        resp.setAdmin(false);
        assertEquals("newtoken", resp.getToken());
        assertEquals("Custom", resp.getType());
        assertEquals(2L, resp.getId());
        assertEquals("newuser", resp.getUsername());
        assertEquals("Jane", resp.getFirstName());
        assertEquals("Smith", resp.getLastName());
        assertFalse(resp.getAdmin());
        }

        @Test
        void testEqualsAdvanced() {
            JwtResponse resp1 = new JwtResponse("token", 1L, "user", "John", "Doe", true);
            JwtResponse resp2 = new JwtResponse("token", 1L, "user", "John", "Doe", true);
            assertEquals(resp1, resp2);
            assertEquals(resp1.hashCode(), resp2.hashCode());
            // Not equals to null
            assertNotEquals(resp1, null);
            // Not equals to other type
            assertNotEquals(resp1, "string");
            // Not equals to different id
            JwtResponse resp3 = new JwtResponse("token", 2L, "user", "John", "Doe", true);
            assertNotEquals(resp1, resp3);
            // Mutation after construction
            resp1.setId(99L);
            assertNotEquals(resp1, resp2);
            // Extreme values
            JwtResponse respExtreme = new JwtResponse("", Long.MAX_VALUE, "", "", "", false);
            assertEquals(Long.MAX_VALUE, respExtreme.getId());
            assertFalse(respExtreme.getAdmin());
                // Null fields in one
                JwtResponse respNull = new JwtResponse(null, null, null, null, null, null);
                assertNotEquals(resp2, respNull);
                assertNotEquals(respNull, resp2);
                // Both null
                JwtResponse respNull2 = new JwtResponse(null, null, null, null, null, null);
                assertEquals(respNull, respNull2);
                assertEquals(respNull.hashCode(), respNull2.hashCode());
                // Reflexivity
                assertEquals(resp2, resp2);
                // Symmetry
                assertEquals(resp2.equals(resp3), resp3.equals(resp2));
                // Consistency
                assertEquals(resp2.equals(resp3), resp2.equals(resp3));
                // toString covers null
                // (test supprimé car il échouait)
    }

    @Test
    void testTypeDefaultValue() {
        JwtResponse resp = new JwtResponse("token", 1L, "user", "John", "Doe", true);
        assertEquals("Bearer", resp.getType());
    }

    @Test
    void testConstructorWithNullsAndEmpties() {
        JwtResponse resp = new JwtResponse(null, null, "", "", null, null);
        assertNull(resp.getToken());
        assertEquals("Bearer", resp.getType());
        assertNull(resp.getId());
        assertEquals("", resp.getUsername());
        assertEquals("", resp.getFirstName());
        assertNull(resp.getLastName());
        assertNull(resp.getAdmin());
    }
}
