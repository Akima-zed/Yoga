package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service pour la gestion des utilisateurs.
 * Toutes les opérations CRUD et vérifications métier sont centralisées ici.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Récupère un utilisateur par son ID.
     *
     * @param id identifiant de l'utilisateur
     * @return utilisateur correspondant
     * @throws NotFoundException si l'utilisateur n'existe pas
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id: " + id));
    }

    /**
     * Récupère un utilisateur par son email.
     *
     * @param email email de l'utilisateur
     * @return utilisateur correspondant
     * @throws NotFoundException si l'utilisateur n'existe pas
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec email: " + email));
    }

    /**
     * Vérifie si un utilisateur existe par email.
     *
     * @param email email à vérifier
     * @return true si l'utilisateur existe
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Crée un nouvel utilisateur.
     *
     * @param email     email
     * @param lastName  nom
     * @param firstName prénom
     * @param password  mot de passe
     * @param isAdmin   rôle administrateur
     * @return utilisateur créé
     * @throws BadRequestException si l'email est déjà utilisé
     */
    public User create(String email, String lastName, String firstName, String password, boolean isAdmin) {
        if (existsByEmail(email)) {
            throw new BadRequestException("Email déjà utilisé");
        }
        User user = new User(email, lastName, firstName, password, isAdmin);
        return userRepository.save(user);
    }

    /**
     * Supprime un utilisateur par son ID.
     *
     * @param id identifiant de l'utilisateur à supprimer
     * @throws NotFoundException si l'utilisateur n'existe pas
     */
    public void delete(Long id) {
        User user = findById(id); // NotFoundException si absent
        userRepository.delete(user);
    }
}
