package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Retourne toujours un User non null
    public @NonNull User findById(@NonNull Long id) {
        return Objects.requireNonNull(
                userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id: " + id))
        );
    }

    public @NonNull User findByEmail(@NonNull String email) {
        return Objects.requireNonNull(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec email: " + email))
        );
    }

    public boolean existsByEmail(@NonNull String email) {
        return userRepository.existsByEmail(email);
    }

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

        return Objects.requireNonNull(userRepository.save(user));
    }

    public void delete(@NonNull Long id) {
        User user = findById(id); // déjà garanti non null
        userRepository.delete(user);
    }
}
