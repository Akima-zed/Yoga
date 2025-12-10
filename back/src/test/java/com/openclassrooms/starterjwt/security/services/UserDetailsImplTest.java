package com.openclassrooms.starterjwt.security.services;

import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

class UserDetailsImplTest {
    @Test
    void builder_CreatesUserDetailsImpl() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("test@example.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .admin(false)
                .build();
        assertEquals(1L, userDetails.getId());
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals("John", userDetails.getFirstName());
        assertEquals("Doe", userDetails.getLastName());
        assertFalse(userDetails.getAdmin());
    }

    @Test
    void testEqualsAndHashCode() {
        UserDetailsImpl details1 = UserDetailsImpl.builder().id(1L).username("user1").build();
        UserDetailsImpl details2 = UserDetailsImpl.builder().id(1L).username("user2").build();
        assertEquals(details1, details2);
    }

    @Test
    void testDefaultAccountStatusMethods() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder().id(1L).username("user").build();
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testGetAuthoritiesReturnsEmpty() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder().id(1L).username("user").build();
        assertNotNull(userDetails.getAuthorities());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }
}
