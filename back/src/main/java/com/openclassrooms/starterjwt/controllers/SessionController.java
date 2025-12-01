package com.openclassrooms.starterjwt.controllers;


import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/session")
@Log4j2
public class SessionController {
    private final SessionMapper sessionMapper;
    private final SessionService sessionService;


    public SessionController(SessionService sessionService,
                             SessionMapper sessionMapper) {
        this.sessionMapper = sessionMapper;
        this.sessionService = sessionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> findById(@PathVariable String id) {

            Session session = sessionService.getById(Long.valueOf(id));

            if (session == null) throw new NotFoundException("Session not found");
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
    public ResponseEntity<SessionDto> update(@PathVariable String id, @Valid @RequestBody SessionDto sessionDto) {
        Session session = sessionService.update(Long.valueOf(id), sessionMapper.toEntity(sessionDto));
        return ResponseEntity.ok(sessionMapper.toDto(session));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Session session = sessionService.getById(Long.valueOf(id));
        if (session == null) throw new NotFoundException("Session not found");
        sessionService.delete(Long.valueOf(id));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/participate/{userId}")
    public ResponseEntity<Void> participate(@PathVariable String id, @PathVariable String userId) {
        sessionService.participate(Long.valueOf(id), Long.valueOf(userId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/participate/{userId}")
    public ResponseEntity<Void> noLongerParticipate(@PathVariable String id, @PathVariable String userId) {
        sessionService.noLongerParticipate(Long.valueOf(id), Long.valueOf(userId));
        return ResponseEntity.ok().build();
    }
}