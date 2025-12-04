package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public @NonNull Session create(@NonNull Session session) {
        return Objects.requireNonNull(sessionRepository.save(session));
    }

    public @NonNull Session update(@NonNull Long id, @NonNull Session session) {
        Session existing = getById(id);
        session.setId(existing.getId());
        return Objects.requireNonNull(sessionRepository.save(session));
    }

    public void delete(@NonNull Long id) {
        Session session = getById(id);
        sessionRepository.delete(session);
    }

    public @NonNull Session getById(@NonNull Long id) {
        return Objects.requireNonNull(
                sessionRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Session introuvable avec id: " + id))
        );
    }

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public void participate(@NonNull Long sessionId, @NonNull Long userId) {
        Session session = getById(sessionId);
        User user = Objects.requireNonNull(
                userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id: " + userId))
        );

        boolean alreadyParticipate = session.getUsers().stream()
                .anyMatch(u -> u.getId().equals(userId));
        if (alreadyParticipate) {
            throw new BadRequestException("Utilisateur déjà inscrit à cette session");
        }

        session.getUsers().add(user);
        sessionRepository.save(session);
    }

    public void noLongerParticipate(@NonNull Long sessionId, @NonNull Long userId) {
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
