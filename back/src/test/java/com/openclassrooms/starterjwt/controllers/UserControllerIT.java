package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.TestSecurityConfig;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestSecurityConfig.class})
class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("Alice");
        user.setLastName("Smith");
        userRepository.save(user);
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        User user = userRepository.findAll().get(0);
        mockMvc.perform(get("/api/user/" + user.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test
    void getUserById_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/user/9999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserById_notFound_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/user/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsers_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@test.com"));
    }

    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {
        User user = userRepository.findAll().get(0);
        mockMvc.perform(delete("/api/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getUserById_shouldReturnUnauthorized_whenNotOwner() throws Exception {
        // Simule une requête avec un utilisateur non propriétaire
        mockMvc.perform(delete("/api/user/9999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
