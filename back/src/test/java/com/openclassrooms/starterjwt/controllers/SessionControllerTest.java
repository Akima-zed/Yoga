package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionControllerTest {
    @Mock
    private SessionService sessionService;
    @Mock
    private SessionMapper sessionMapper;
    @InjectMocks
    private SessionController sessionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_shouldReturnSessionDto() {
        Session session = new Session();
        SessionDto dto = new SessionDto();
        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(dto);
        ResponseEntity<SessionDto> response = sessionController.findById(1L);
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void findAll_shouldReturnListOfSessionDto() {
        List<Session> sessions = Collections.singletonList(new Session());
        List<SessionDto> dtos = Collections.singletonList(new SessionDto());
        when(sessionService.findAll()).thenReturn(sessions);
        when(sessionMapper.toDto(sessions)).thenReturn(dtos);
        ResponseEntity<List<SessionDto>> response = sessionController.findAll();
        assertEquals(dtos, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void create_shouldReturnCreatedSessionDto() {
        SessionDto dto = new SessionDto();
        Session session = new Session();
        when(sessionMapper.toEntity(dto)).thenReturn(session);
        when(sessionService.create(session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(dto);
        ResponseEntity<SessionDto> response = sessionController.create(dto);
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void update_shouldReturnUpdatedSessionDto() {
        SessionDto dto = new SessionDto();
        Session session = new Session();
        when(sessionMapper.toEntity(dto)).thenReturn(session);
        when(sessionService.update(1L, session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(dto);
        ResponseEntity<SessionDto> response = sessionController.update(1L, dto);
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void delete_shouldReturnOk() {
        doNothing().when(sessionService).delete(1L);
        ResponseEntity<Void> response = sessionController.delete(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(sessionService, times(1)).delete(1L);
    }

    @Test
    void participate_shouldReturnOk() {
        doNothing().when(sessionService).participate(1L, 2L);
        ResponseEntity<Void> response = sessionController.participate(1L, 2L);
        assertEquals(200, response.getStatusCodeValue());
        verify(sessionService, times(1)).participate(1L, 2L);
    }

    @Test
    void noLongerParticipate_shouldReturnOk() {
        doNothing().when(sessionService).noLongerParticipate(1L, 2L);
        ResponseEntity<Void> response = sessionController.noLongerParticipate(1L, 2L);
        assertEquals(200, response.getStatusCodeValue());
        verify(sessionService, times(1)).noLongerParticipate(1L, 2L);
    }
}
