package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * Controller pour l'authentification et l'inscription des utilisateurs.
 * Toutes les opérations sont déléguées aux services.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    /**
     * Authentifie un utilisateur et retourne un JWT.
     *
     * @param loginRequest email et mot de passe
     * @return JwtResponse avec token et informations utilisateur
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userService.findByEmail(userDetails.getUsername()); // NotFoundException si absent

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                user.isAdmin()
        ));
    }


    /**
     * Inscrit un nouvel utilisateur.
     *
     * @param signUpRequest informations de l'utilisateur
     * @return MessageResponse confirmation
     */
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email is already taken!");
        }

        userService.create(
                signUpRequest.getEmail(),
                signUpRequest.getLastName(),
                signUpRequest.getFirstName(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                false
        );

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
