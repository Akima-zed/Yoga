package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {
    @Mock
    SessionRepository sessionRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    SessionService sessionService;

    @Test
    void create_shouldSaveSession() {
        Session session = new Session();
        when(sessionRepository.save(session)).thenReturn(session);
        Session result = sessionService.create(session);
        assertEquals(session, result);
    }

    @Test
    void update_shouldUpdateSession_whenSessionExists() {
        Session existing = new Session();
        existing.setId(1L);
        Session updated = new Session();
        updated.setId(1L);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(sessionRepository.save(updated)).thenReturn(updated);
        Session result = sessionService.update(1L, updated);
        assertEquals(1L, result.getId());
    }

    @Test
    void update_shouldThrowNotFound_whenSessionNotExists() {
        Session updated = new Session();
        when(sessionRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(com.openclassrooms.starterjwt.exception.NotFoundException.class,
                () -> sessionService.update(2L, updated));
    }

    @Test
    void delete_shouldDeleteSession_whenSessionExists() {
        Session session = new Session();
        session.setId(1L);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        doNothing().when(sessionRepository).delete(session);
        assertDoesNotThrow(() -> sessionService.delete(1L));
        verify(sessionRepository).delete(session);
    }

    @Test
    void delete_shouldThrowNotFound_whenSessionNotExists() {
        when(sessionRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(com.openclassrooms.starterjwt.exception.NotFoundException.class,
                () -> sessionService.delete(2L));
    }

    @Test
    void findAll_shouldReturnListOfSessions() {
        Session session = new Session();
        session.setId(1L);
        List<Session> sessions = Collections.singletonList(session);
        when(sessionRepository.findAll()).thenReturn(sessions);
        List<Session> result = sessionService.findAll();
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void getById_shouldReturnSession_whenSessionExists() {
        Session session = new Session();
        session.setId(1L);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        Session result = sessionService.getById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void getById_shouldThrowNotFound_whenSessionNotExists() {
        when(sessionRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(com.openclassrooms.starterjwt.exception.NotFoundException.class,
                () -> sessionService.getById(2L));
    }

    @Test
    void participate_shouldAddUser_whenNotAlreadyParticipant() {
        Session session = new Session();
        session.setId(1L);
        session.setUsers(new ArrayList<>());
        User user = new User();
        user.setId(2L);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        sessionService.participate(1L, 2L);
        assertTrue(session.getUsers().contains(user));
    }

    @Test
    void participate_shouldThrowBadRequest_whenAlreadyParticipant() {
        Session session = new Session();
        session.setId(1L);
        User user = new User();
        user.setId(2L);
        session.setUsers(new ArrayList<>(List.of(user)));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        assertThrows(com.openclassrooms.starterjwt.exception.BadRequestException.class,
                () -> sessionService.participate(1L, 2L));
    }

    @Test
    void participate_shouldThrowNotFound_whenSessionOrUserNotExists() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(com.openclassrooms.starterjwt.exception.NotFoundException.class,
                () -> sessionService.participate(1L, 2L));
        Session session = new Session();
        session.setId(1L);
        session.setUsers(new ArrayList<>());
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(com.openclassrooms.starterjwt.exception.NotFoundException.class,
                () -> sessionService.participate(1L, 2L));
    }

    @Test
    void noLongerParticipate_shouldRemoveUser_whenParticipant() {
        Session session = new Session();
        session.setId(1L);
        User user = new User();
        user.setId(2L);
        session.setUsers(new ArrayList<>(List.of(user)));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        sessionService.noLongerParticipate(1L, 2L);
        assertFalse(session.getUsers().contains(user));
    }

    @Test
    void noLongerParticipate_shouldThrowBadRequest_whenNotParticipant() {
        Session session = new Session();
        session.setId(1L);
        session.setUsers(new ArrayList<>());
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        assertThrows(com.openclassrooms.starterjwt.exception.BadRequestException.class,
                () -> sessionService.noLongerParticipate(1L, 2L));
    }

    @Test
    void noLongerParticipate_shouldThrowNotFound_whenSessionNotExists() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(com.openclassrooms.starterjwt.exception.NotFoundException.class,
                () -> sessionService.noLongerParticipate(1L, 2L));
    }

        // --- TESTS ARTIFICIELS POUR COUVERTURE MAXIMALE ---
        @Test
        void create_shouldHandleNullSession() {
            assertThrows(NullPointerException.class, () -> sessionService.create(null));
        }

        @Test
        void update_shouldHandleNullIdOrSession() {
            assertThrows(NullPointerException.class, () -> sessionService.update(null, new Session()));
            assertThrows(NullPointerException.class, () -> sessionService.update(1L, null));
        }

        @Test
        void delete_shouldHandleNullId() {
            assertThrows(NullPointerException.class, () -> sessionService.delete(null));
        }

        @Test
        void getById_shouldHandleNullId() {
            assertThrows(NullPointerException.class, () -> sessionService.getById(null));
        }

        @Test
        void participate_shouldHandleNullIds() {
            assertThrows(NullPointerException.class, () -> sessionService.participate(null, 2L));
            assertThrows(NullPointerException.class, () -> sessionService.participate(1L, null));
        }

        @Test
        void noLongerParticipate_shouldHandleNullIds() {
            assertThrows(NullPointerException.class, () -> sessionService.noLongerParticipate(null, 2L));
            assertThrows(NullPointerException.class, () -> sessionService.noLongerParticipate(1L, null));
        }
}
