package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
        @Test
        void testBuilderWithNullsAndToString() {
            User user = User.builder().build();
            assertNull(user.getId());
            assertNull(user.getEmail());
            assertNull(user.getLastName());
            assertNull(user.getFirstName());
            assertFalse(user.isAdmin());
            assertTrue(user.toString().contains("User"));
        }
    @Test
    void builder_and_getters_shouldWork() {
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .lastName("Doe")
                .firstName("John")
                .admin(true)
                .build();
        assertEquals(1L, user.getId());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertTrue(user.isAdmin());
    }

    @Test
    void equals_and_hashCode_shouldWork() {
        User u1 = User.builder().id(1L).email("a@b.com").build();
        User u2 = User.builder().id(1L).email("a@b.com").build();
        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }
}
