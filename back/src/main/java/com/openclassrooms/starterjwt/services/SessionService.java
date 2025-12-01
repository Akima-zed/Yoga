package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public Session create(Session session) {
        return sessionRepository.save(session);
    }

    public Session update(Long id, Session session) {
        Session existing = getById(id);
        session.setId(existing.getId());
        return sessionRepository.save(session);
    }

    public void delete(Long id) {
        Session session = getById(id); // NotFoundException si absent
        sessionRepository.delete(session);
    }

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Session getById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session introuvable avec id: " + id));
    }

    public void participate(Long sessionId, Long userId) {
        Session session = getById(sessionId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id: " + userId));

        boolean alreadyParticipate = session.getUsers().stream()
                .anyMatch(u -> u.getId().equals(userId));
        if (alreadyParticipate) {
            throw new BadRequestException("Utilisateur déjà inscrit à cette session");
        }

        session.getUsers().add(user);
        sessionRepository.save(session);
    }

    public void noLongerParticipate(Long sessionId, Long userId) {
        Session session = getById(sessionId);

        boolean alreadyParticipate = session.getUsers().stream()
                .anyMatch(u -> u.getId().equals(userId));
        if (!alreadyParticipate) {
            throw new BadRequestException("Utilisateur non inscrit à cette session");
        }

        session.setUsers(session.getUsers().stream()
                .filter(u -> !u.getId().equals(userId))
                .collect(Collectors.toList()));

        sessionRepository.save(session);
    }
}
