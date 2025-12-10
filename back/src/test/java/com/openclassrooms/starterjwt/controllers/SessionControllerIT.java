package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.TestSecurityConfig;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestSecurityConfig.class})
class SessionControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository.deleteAll();
        Session session = new Session();
        session.setName("Yoga du matin");
        sessionRepository.save(session);
    }

    @Test
    void getSessionById_shouldReturnSession() throws Exception {
        Session session = sessionRepository.findAll().get(0);
        mockMvc.perform(get("/api/session/" + session.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yoga du matin"));
    }

    @Test
    void getSessionById_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/session/9999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllSessions_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/session").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Yoga du matin"));
    }

    @Test
    void createSession_shouldReturnCreated() throws Exception {
        String body = "{\"name\":\"Yoga du soir\"}";
        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void updateSession_shouldReturnOk() throws Exception {
        Session session = sessionRepository.findAll().get(0);
        String body = "{\"name\":\"Yoga modifié\"}";
        mockMvc.perform(put("/api/session/" + session.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSession_shouldReturnNoContent() throws Exception {
        Session session = sessionRepository.findAll().get(0);
        mockMvc.perform(delete("/api/session/" + session.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void participate_shouldReturnOk_whenValid() throws Exception {
        Session session = sessionRepository.findAll().get(0);
        long sessionId = session.getId();
        long userId = 1L; // à adapter selon la base
        mockMvc.perform(post("/api/session/" + sessionId + "/participate/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
