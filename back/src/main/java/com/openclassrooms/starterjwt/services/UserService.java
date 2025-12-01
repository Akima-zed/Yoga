package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec email: " + email));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User create(String email, String lastName, String firstName, String password, boolean isAdmin) {
        if (existsByEmail(email)) {
            throw new BadRequestException("Email déjà utilisé");
        }
        User user = new User(email, lastName, firstName, password, isAdmin);
        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = findById(id); // NotFoundException si absent
        userRepository.delete(user);
    }
}
