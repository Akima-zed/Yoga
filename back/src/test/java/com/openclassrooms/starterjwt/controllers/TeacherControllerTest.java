package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherControllerTest {
    @Mock
    private TeacherService teacherService;
    @Mock
    private TeacherMapper teacherMapper;
    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_shouldReturnTeacherDto() {
        Teacher teacher = new Teacher();
        TeacherDto dto = new TeacherDto();
        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(dto);
        ResponseEntity<TeacherDto> response = teacherController.findById(1L);
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void findAll_shouldReturnListOfTeacherDto() {
        List<Teacher> teachers = Collections.singletonList(new Teacher());
        List<TeacherDto> dtos = Collections.singletonList(new TeacherDto());
        when(teacherService.findAll()).thenReturn(teachers);
        when(teacherMapper.toDto(teachers)).thenReturn(dtos);
        ResponseEntity<List<TeacherDto>> response = teacherController.findAll();
        assertEquals(dtos, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
