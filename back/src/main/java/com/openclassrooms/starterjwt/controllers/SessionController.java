package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller pour gérer les sessions (CRUD + participation).
 */
@RestController
@RequestMapping("/api/session")
public class SessionController {

    private final SessionService sessionService;
    private final SessionMapper sessionMapper;

    public SessionController(SessionService sessionService,
                             SessionMapper sessionMapper) {
        this.sessionService = sessionService;
        this.sessionMapper = sessionMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> findById(@PathVariable Long id) {
        Session session = sessionService.getById(id);
        return ResponseEntity.ok(sessionMapper.toDto(session));
    }

    @GetMapping
    public ResponseEntity<List<SessionDto>> findAll() {
        return ResponseEntity.ok(sessionMapper.toDto(sessionService.findAll()));
    }

    @PostMapping
    public ResponseEntity<SessionDto> create(@Valid @RequestBody SessionDto sessionDto) {
        Session session = sessionService.create(sessionMapper.toEntity(sessionDto));
        return ResponseEntity.ok(sessionMapper.toDto(session));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionDto> update(@PathVariable Long id, @Valid @RequestBody SessionDto sessionDto) {
        Session session = sessionService.update(id, sessionMapper.toEntity(sessionDto));
        return ResponseEntity.ok(sessionMapper.toDto(session));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sessionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/participate/{userId}")
    public ResponseEntity<Void> participate(@PathVariable Long id, @PathVariable Long userId) {
        sessionService.participate(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/participate/{userId}")
    public ResponseEntity<Void> noLongerParticipate(@PathVariable Long id, @PathVariable Long userId) {
        sessionService.noLongerParticipate(id, userId);
        return ResponseEntity.ok().build();
    }
}
