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


/**
 * Service gérant les opérations liées aux sessions.
 * Toutes les opérations CRUD et la gestion de participation des utilisateurs
 * sont centralisées ici.
 */
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Crée une nouvelle session.
     *
     * @param session session à créer
     * @return session sauvegardée
     */
    public Session create(Session session) {
        return sessionRepository.save(session);
    }

    /**
     * Met à jour une session existante.
     *
     * @param id      identifiant de la session à mettre à jour
     * @param session données mises à jour
     * @return session mise à jour
     */
    public Session update(Long id, Session session) {
        Session existing = getById(id);
        session.setId(existing.getId());
        return sessionRepository.save(session);
    }

    /**
     * Supprime une session.
     *
     * @param id identifiant de la session à supprimer
     * @throws NotFoundException si la session n'existe pas
     */
    public void delete(Long id) {
        Session session = getById(id); // NotFoundException si absent
        sessionRepository.delete(session);
    }

    /**
     * Récupère toutes les sessions.
     *
     * @return liste de toutes les sessions
     */
    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    /**
     * Récupère une session par son ID.
     *
     * @param id identifiant de la session
     * @return session correspondante
     * @throws NotFoundException si la session n'existe pas
     */
    public Session getById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session introuvable avec id: " + id));
    }

    /**
     * Ajoute un utilisateur à une session.
     *
     * @param sessionId identifiant de la session
     * @param userId    identifiant de l'utilisateur
     * @throws NotFoundException   si la session ou l'utilisateur n'existe pas
     * @throws BadRequestException si l'utilisateur est déjà inscrit
     */
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

    /**
     * Retire un utilisateur d'une session.
     *
     * @param sessionId identifiant de la session
     * @param userId    identifiant de l'utilisateur
     * @throws BadRequestException si l'utilisateur n'est pas inscrit
     */
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
