package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.exception.UnauthorizedException;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


/**
 * Controller pour gérer les utilisateurs (lecture / suppression).
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        User user = userService.findById(id); // NotFoundException si absent
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User user = userService.findById(id);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.getUsername().equals(user.getEmail())) {
            throw new UnauthorizedException("Action non autorisée");
        }

        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
