package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.TestSecurityConfig;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestSecurityConfig.class})
class TeacherControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        teacherRepository.deleteAll();
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacherRepository.save(teacher);
    }

    @Test
    void getAllTeachers_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/teacher").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void getTeacherById_shouldReturnTeacher() throws Exception {
        Teacher teacher = teacherRepository.findAll().get(0);
        mockMvc.perform(get("/api/teacher/" + teacher.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getTeacherById_shouldReturnNotFound_whenInvalidId() throws Exception {
        mockMvc.perform(get("/api/teacher/9999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTeacherById_notFound_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/teacher/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllTeachers_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/teacher"))
                .andExpect(status().isOk());
    }
}
