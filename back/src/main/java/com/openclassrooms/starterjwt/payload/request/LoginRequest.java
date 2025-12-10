package com.openclassrooms.starterjwt.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    /**
     * Méthode utilitaire pour vérifier si le mot de passe contient au moins une lettre et un chiffre.
     */
    public boolean isPasswordAlphaNumeric() {
        if (password == null) return false;
        boolean hasLetter = password.chars().anyMatch(Character::isLetter);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        return hasLetter && hasDigit;
    }

}
