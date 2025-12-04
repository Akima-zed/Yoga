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
import java.util.stream.Collectors;

@Service
public class SessionService {

    /**
     * Service Spring pour gérer les sessions (cours/événements) et
     * les inscriptions des utilisateurs aux sessions.
     *
     * <p>Ce service expose les opérations CRUD basiques sur les entités
     * {@link com.openclassrooms.starterjwt.models.Session} ainsi que les
     * opérations métier d'inscription et de désinscription des utilisateurs.
     * Il utilise {@link SessionRepository} pour la persistance et
     * {@link UserRepository} pour vérifier/lier les participants.</p>
     *
     * <p>Exceptions levées:
     * <ul>
     *   <li>{@link NotFoundException} lorsqu'une entité recherchée est introuvable</li>
     *   <li>{@link BadRequestException} pour des erreurs métier (ex: double inscription)</li>
     * </ul>
     * </p>
     */

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Constructeur du service.
     *
     * @param sessionRepository repository pour les sessions (non null)
     * @param userRepository repository pour les utilisateurs (non null)
     */

    /**
     * Crée et persiste une nouvelle session.
     *
     * @param session la session à créer (non null)
     * @return la session persistée (non null)
     */
    public @NonNull Session create(@NonNull Session session) {
        return sessionRepository.save(session);
    }

    /**
     * Met à jour une session existante identifiée par {@code id}.
     *
     * @param id identifiant de la session à mettre à jour (non null)
     * @param session objet session contenant les nouvelles valeurs (non null)
     * @return la session mise à jour (non null)
     * @throws NotFoundException si la session n'existe pas
     */
    public @NonNull Session update(@NonNull Long id, @NonNull Session session) {
        Session existing = getById(id);
        session.setId(existing.getId());
        return sessionRepository.save(session);
    }

    /**
     * Supprime la session identifiée par {@code id}.
     *
     * @param id identifiant de la session à supprimer (non null)
     * @throws NotFoundException si la session n'existe pas
     */
    @SuppressWarnings("null")
    public void delete(@NonNull Long id) {
        sessionRepository.delete(getById(id));
    }

    /**
     * Retourne la liste de toutes les sessions.
     *
     * @return liste non nulle des sessions
     */
    public @NonNull List<Session> findAll() {
        return sessionRepository.findAll();
    }

    /**
     * Récupère une session par son identifiant.
     *
     * @param id identifiant de la session (non null)
     * @return la session trouvée (non null)
     * @throws NotFoundException si aucune session n'est trouvée
     */
    public @NonNull Session getById(@NonNull Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session introuvable avec id: " + id));
    }

    /**
     * Inscrit un utilisateur à une session.
     *
     * <p>Vérifie que la session et l'utilisateur existent puis interdit les
     * doubles inscriptions en levant une {@link BadRequestException} si
     * l'utilisateur est déjà inscrit.</p>
     *
     * @param sessionId identifiant de la session (non null)
     * @param userId identifiant de l'utilisateur (non null)
     * @throws NotFoundException si la session ou l'utilisateur est introuvable
     * @throws BadRequestException si l'utilisateur est déjà inscrit
     */
    public void participate(@NonNull Long sessionId, @NonNull Long userId) {
        Session session = getById(sessionId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id: " + userId));

        if (session.getUsers().stream().anyMatch(u -> u.getId().equals(userId))) {
            throw new BadRequestException("Utilisateur déjà inscrit à cette session");
        }

        session.getUsers().add(user);
        sessionRepository.save(session);
    }

    /**
     * Désinscrit un utilisateur d'une session.
     *
     * <p>Si l'utilisateur n'est pas inscrit, une {@link BadRequestException}
     * est levée.</p>
     *
     * @param sessionId identifiant de la session (non null)
     * @param userId identifiant de l'utilisateur (non null)
     * @throws NotFoundException si la session est introuvable
     * @throws BadRequestException si l'utilisateur n'est pas inscrit
     */
    public void noLongerParticipate(@NonNull Long sessionId, @NonNull Long userId) {
        Session session = getById(sessionId);

        if (session.getUsers().stream().noneMatch(u -> u.getId().equals(userId))) {
            throw new BadRequestException("Utilisateur non inscrit à cette session");
        }

        session.setUsers(session.getUsers().stream()
                .filter(u -> !u.getId().equals(userId))
                .collect(Collectors.toList()));

        sessionRepository.save(session);
    }
}
