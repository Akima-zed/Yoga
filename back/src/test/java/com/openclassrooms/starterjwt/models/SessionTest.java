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
    }

    @Test
    void users_shouldBeMutable() {
        Session session = Session.builder().id(2L).build();
        session.setUsers(List.of());
        assertNotNull(session.getUsers());
    }
}
