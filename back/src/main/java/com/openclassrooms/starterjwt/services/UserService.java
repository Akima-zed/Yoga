package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



/**
 * Service Spring chargé des opérations liées aux utilisateurs.
 *
 * <p>Ce service expose des opérations de recherche, création et suppression
 * d'entités {@code User} en s'appuyant sur {@code UserRepository}. Les
 * vérifications métier (unicité d'email) sont effectuées ici et des
 * exceptions personnalisées sont levées pour signaler les erreurs.</p>
 *
 * <p>Remarques importantes :
 * <ul>
 *   <li>Le mot de passe fourni à {@link #create(String, String, String, String, boolean)}
 *       doit être encodé avant l'appel (ajouter un {@code PasswordEncoder} côté
 *       appelant ou injecter/adapter le code pour encoder ici).</li>
 *   <li>Les messages d'exception sont explicites ; adapter pour l'environnement
 *       de production si nécessaire afin d'éviter la fuite d'informations.</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Recherche un utilisateur par son identifiant.
     *
     * @param id identifiant de l'utilisateur (non null)
     * @return l'utilisateur correspondant (non null)
     * @throws NotFoundException si aucun utilisateur n'est trouvé pour cet id
     */
    public @NonNull User findById(@NonNull Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id: " + id));
    }

    /**
     * Recherche un utilisateur par son adresse email.
     *
     * @param email adresse email de l'utilisateur (non null)
     * @return l'utilisateur correspondant (non null)
     * @throws NotFoundException si aucun utilisateur n'est trouvé pour cet email
     */
    public @NonNull User findByEmail(@NonNull String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec email: " + email));
    }

    /**
     * Vérifie l'existence d'un utilisateur par email.
     *
     * @param email adresse email à vérifier (non null)
     * @return {@code true} si un utilisateur avec cet email existe, sinon {@code false}
     */
    public boolean existsByEmail(@NonNull String email) {
        return userRepository.existsByEmail(email);
    }


    /**
     * Crée et persiste un nouvel utilisateur.
     *
     * <p>Avant la création, l'unicité de l'email est vérifiée. Si l'email
     * existe déjà, une {@link BadRequestException} est levée.</p>
     *
     * @param email adresse email (non null)
     * @param lastName nom de famille (non null)
     * @param firstName prénom (non null)
     * @param password mot de passe (non null). Doit être encodé avant l'appel.
     * @param isAdmin indicateur si l'utilisateur est administrateur
     * @return l'utilisateur persisté (non null)
     * @throws BadRequestException si l'email est déjà utilisé
     */
    @SuppressWarnings("null")
    public @NonNull User create(@NonNull String email,
                                @NonNull String lastName,
                                @NonNull String firstName,
                                @NonNull String password,
                                boolean isAdmin) {

        if (existsByEmail(email)) {
            throw new BadRequestException("Email déjà utilisé");
        }

        User user = User.builder()
                .email(email)
                .lastName(lastName)
                .firstName(firstName)
                .password(password)
                .admin(isAdmin)
                .build();

        return userRepository.save(user);
    }

    /**
     * Supprime l'utilisateur identifié par {@code id}.
     *
     * @param id identifiant de l'utilisateur à supprimer (non null)
     * @throws NotFoundException si l'utilisateur n'existe pas
     */
    @SuppressWarnings("null")
    public void delete(@NonNull Long id) {
        userRepository.delete(findById(id));
    }
}
