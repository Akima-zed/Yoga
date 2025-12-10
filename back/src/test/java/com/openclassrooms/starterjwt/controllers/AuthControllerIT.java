package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestSecurityConfig.class})
class AuthControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void login_shouldReturnBadRequest_whenMissingBody() throws Exception {
        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_shouldReturnUnauthorized_whenWrongCredentials() throws Exception {
        String body = "{\"email\":\"wrong@test.com\",\"password\":\"badpass\"}";
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void register_shouldReturnBadRequest_whenMissingBody() throws Exception {
        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_shouldCreateUser_whenValidBody() throws Exception {
        String body = "{\"email\":\"newuser@test.com\",\"firstName\":\"Test\",\"lastName\":\"User\",\"password\":\"pass123\",\"admin\":false}";
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());
    }
}
