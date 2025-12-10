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
        // Not equals to null
        assertNotEquals(u1, null);
        // Not equals to other type
        assertNotEquals(u1, "string");
        // Not equals to different id
        User u3 = User.builder().id(2L).email("a@b.com").build();
        assertNotEquals(u1, u3);
        // Mutation after construction
        u1.setEmail("other@test.com");
        assertNotEquals(u1, u2);
        // Extreme values
        User uExtreme = User.builder().id(Long.MAX_VALUE).email("").build();
        assertEquals(Long.MAX_VALUE, uExtreme.getId());
            // All fields null
            User uNull1 = new User();
            User uNull2 = new User();
            assertEquals(uNull1, uNull2);
            assertEquals(uNull1.hashCode(), uNull2.hashCode());
            // Different fields
            User uDiff = User.builder().id(1L).email("x@y.com").lastName("A").firstName("B").password("p").admin(true).build();
            User uDiff2 = User.builder().id(1L).email("x@y.com").lastName("A").firstName("B").password("p").admin(false).build();
            assertNotEquals(uDiff, uDiff2);
            // Compare with completely different object
            assertNotEquals(uNull1, new Object());
            // Compare with itself
            assertEquals(uNull1, uNull1);
    }
    @Test
    void builder_and_constructor_edgeCases() {
        // Builder with all nulls
        User userNull = User.builder().id(null).email(null).lastName(null).firstName(null).password(null).admin(false).build();
        assertNull(userNull.getId());
        assertNull(userNull.getEmail());
        assertNull(userNull.getLastName());
        assertNull(userNull.getFirstName());
        assertNull(userNull.getPassword());
        assertFalse(userNull.isAdmin());

        // All fields set
        User userFull = User.builder()
                .id(123L)
                .email("full@entreprise.com")
                .lastName("Last")
                .firstName("First")
                .password("pass")
                .admin(true)
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .build();
        assertEquals(123L, userFull.getId());
        assertEquals("full@entreprise.com", userFull.getEmail());
        assertEquals("Last", userFull.getLastName());
        assertEquals("First", userFull.getFirstName());
        assertEquals("pass", userFull.getPassword());
        assertTrue(userFull.isAdmin());

        // Constructor with all fields
        User userCtor = new User(1L, "a@b.com", "L", "F", "pw", true, java.time.LocalDateTime.MIN, java.time.LocalDateTime.MAX);
        assertEquals(1L, userCtor.getId());
        assertEquals("a@b.com", userCtor.getEmail());
        assertEquals("L", userCtor.getLastName());
        assertEquals("F", userCtor.getFirstName());
        assertEquals("pw", userCtor.getPassword());
        assertTrue(userCtor.isAdmin());
        assertEquals(java.time.LocalDateTime.MIN, userCtor.getCreatedAt());
        assertEquals(java.time.LocalDateTime.MAX, userCtor.getUpdatedAt());
    }

    @Test
    void testUserSettersAndEdgeCases() {
        User user = new User();
        user.setId(null);
        user.setEmail("");
        user.setLastName(null);
        user.setFirstName("");
        user.setPassword(null);
        user.setAdmin(true);
        user.setCreatedAt(null);
        user.setUpdatedAt(null);
        assertNull(user.getId());
        assertEquals("", user.getEmail());
        assertNull(user.getLastName());
        assertEquals("", user.getFirstName());
        assertNull(user.getPassword());
        assertTrue(user.isAdmin());
        assertNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());
    }

    @Test
    void testValidationAndEdgeCases() {
        User user = new User();
        // Test max size email
        String longEmail = "a".repeat(40) + "@test.com";
        user.setEmail(longEmail);
        assertEquals(longEmail, user.getEmail());
        // Test max size lastName/firstName
        String longName = "b".repeat(20);
        user.setLastName(longName);
        user.setFirstName(longName);
        assertEquals(longName, user.getLastName());
        assertEquals(longName, user.getFirstName());
        // Test password
        user.setPassword("");
        assertEquals("", user.getPassword());
        // Test admin true/false
        user.setAdmin(true);
        assertTrue(user.isAdmin());
        user.setAdmin(false);
        assertFalse(user.isAdmin());
        // Edge dates
        user.setCreatedAt(java.time.LocalDateTime.MIN);
        user.setUpdatedAt(java.time.LocalDateTime.MAX);
        assertEquals(java.time.LocalDateTime.MIN, user.getCreatedAt());
        assertEquals(java.time.LocalDateTime.MAX, user.getUpdatedAt());
    }

    @Test
    void testToStringSpecialValues() {
        User user = new User();
        user.setEmail(null);
        user.setLastName(null);
        user.setFirstName(null);
        assertNotNull(user.toString());
        user.setEmail("\n\t\u2603@test.com");
        assertTrue(user.toString().contains("\u2603"));
    }

        @Test
        void testIsSuperAdmin_falseIfNotAdmin() {
            User user = new User();
            user.setAdmin(false);
            user.setEmail("admin@entreprise.com");
            assertFalse(user.isSuperAdmin());
        }

        @Test
        void testIsSuperAdmin_falseIfEmailNull() {
            User user = new User();
            user.setAdmin(true);
            user.setEmail(null);
            assertFalse(user.isSuperAdmin());
        }

        @Test
        void testIsSuperAdmin_falseIfEmailNotEntreprise() {
            User user = new User();
            user.setAdmin(true);
            user.setEmail("admin@autre.com");
            assertFalse(user.isSuperAdmin());
        }

        @Test
        void testIsSuperAdmin_true() {
            User user = new User();
            user.setAdmin(true);
            user.setEmail("admin@entreprise.com");
            assertTrue(user.isSuperAdmin());
        }

        @Test
        void testIsSuperAdmin_allBranches() {
            // admin=false
            User user1 = User.builder().admin(false).email("x@entreprise.com").build();
            assertFalse(user1.isSuperAdmin());
            // admin=true, email=null
            User user2 = User.builder().admin(true).email(null).build();
            assertFalse(user2.isSuperAdmin());
            // admin=true, email not entreprise
            User user3 = User.builder().admin(true).email("x@autre.com").build();
            assertFalse(user3.isSuperAdmin());
            // admin=true, email ends with @entreprise.com
            User user4 = User.builder().admin(true).email("x@entreprise.com").build();
            assertTrue(user4.isSuperAdmin());
        }
}
