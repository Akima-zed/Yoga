package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class SessionTest {
        @Test
        void testBuilderWithNullsAndToString() {
            Session session = Session.builder().build();
            assertNull(session.getId());
            assertNull(session.getName());
            assertTrue(session.toString().contains("Session"));
        }
    @Test
    void builder_and_getters_shouldWork() {
        Session session = Session.builder()
                .id(1L)
                .name("Yoga du matin")
                .build();
        assertEquals(1L, session.getId());
        assertEquals("Yoga du matin", session.getName());
    }

    @Test
    void equals_and_hashCode_shouldWork() {
        Session s1 = Session.builder().id(1L).name("Yoga").build();
        Session s2 = Session.builder().id(1L).name("Yoga").build();
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        // Not equals to null
        assertNotEquals(s1, null);
        // Not equals to other type
        assertNotEquals(s1, "string");
        // Not equals to different id
        Session s3 = Session.builder().id(2L).name("Yoga").build();
        assertNotEquals(s1, s3);
        // Mutation after construction
        s1.setName("Other");
        assertNotEquals(s1, s2);
        // Extreme values
        Session sExtreme = Session.builder().id(Long.MAX_VALUE).name("").build();
        assertEquals(Long.MAX_VALUE, sExtreme.getId());
    }

    @Test
    void users_shouldBeMutable() {
        Session session = Session.builder().id(2L).build();
        session.setUsers(List.of());
        assertNotNull(session.getUsers());
    }

    @Test
    void testSessionSettersAndEdgeCases() {
        Session session = new Session();
        session.setId(null);
        session.setName("");
        session.setDate(null);
        session.setDescription(null);
        session.setTeacher(null);
        session.setUsers(null);
        session.setCreatedAt(null);
        session.setUpdatedAt(null);
        assertNull(session.getId());
        assertEquals("", session.getName());
        assertNull(session.getDate());
        assertNull(session.getDescription());
        assertNull(session.getTeacher());
        assertNull(session.getUsers());
        assertNull(session.getCreatedAt());
        assertNull(session.getUpdatedAt());
    }

    @Test
    void testSessionUsersMutation() {
        Session session = Session.builder().id(3L).build();
        List<User> users = new java.util.ArrayList<>();
        User user1 = User.builder().id(1L).email("a@b.com").build();
        users.add(user1);
        session.setUsers(users);
        assertEquals(1, session.getUsers().size());
        User user2 = User.builder().id(2L).email("b@c.com").build();
        session.getUsers().add(user2);
        assertEquals(2, session.getUsers().size());
        session.getUsers().remove(user1);
        assertEquals(1, session.getUsers().size());
    }

    @Test
    void testValidationAndEdgeCases() {
        Session session = new Session();
        // Test blank name
        session.setName("");
        assertEquals("", session.getName());
        // Test max size name
        String longName = "a".repeat(50);
        session.setName(longName);
        assertEquals(longName, session.getName());
        // Test description max size
        String longDesc = "b".repeat(2500);
        session.setDescription(longDesc);
        assertEquals(longDesc, session.getDescription());
        // Test null users
        session.setUsers(null);
        assertNull(session.getUsers());
        // Test empty users
        session.setUsers(List.of());
        assertTrue(session.getUsers().isEmpty());
        // Edge dates
        session.setDate(new java.util.Date(Long.MAX_VALUE));
        assertEquals(Long.MAX_VALUE, session.getDate().getTime());
        session.setCreatedAt(java.time.LocalDateTime.MIN);
        session.setUpdatedAt(java.time.LocalDateTime.MAX);
        assertEquals(java.time.LocalDateTime.MIN, session.getCreatedAt());
        assertEquals(java.time.LocalDateTime.MAX, session.getUpdatedAt());
    }

    @Test
    void testToStringSpecialValues() {
        Session session = new Session();
        session.setName(null);
        session.setDescription(null);
        assertNotNull(session.toString());
        session.setName("\n\t\u2603");
        assertTrue(session.toString().contains("\u2603"));
    }

        @Test
        void testIsLongSession_nullDescription() {
            Session session = new Session();
            session.setDescription(null);
            assertFalse(session.isLongSession());
        }

        @Test
        void testIsLongSession_shortDescription() {
            Session session = new Session();
            session.setDescription("short");
            assertFalse(session.isLongSession());
        }

        @Test
        void testIsLongSession_exactly1000() {
            Session session = new Session();
            session.setDescription("a".repeat(1000));
            assertFalse(session.isLongSession());
        }

        @Test
        void testIsLongSession_longDescription() {
            Session session = new Session();
            session.setDescription("a".repeat(1001));
            assertTrue(session.isLongSession());
        }
}
