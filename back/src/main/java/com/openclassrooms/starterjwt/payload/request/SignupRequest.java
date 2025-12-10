package com.openclassrooms.starterjwt.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class SignupRequest {
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    /**
     * Méthode utilitaire pour vérifier si l'utilisateur a un mot de passe fort.
     * Un mot de passe est considéré comme fort s'il fait au moins 8 caractères et contient un chiffre.
     */
    public boolean isStrongPassword() {
        if (password == null) return false;
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        return password.length() >= 8 && hasDigit;
    }
}
