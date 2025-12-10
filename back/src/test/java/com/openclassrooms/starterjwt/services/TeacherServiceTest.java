package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    TeacherRepository teacherRepository;
    @InjectMocks
    TeacherService teacherService;

    @Test
    void findById_shouldReturnTeacher_whenTeacherExists() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        Teacher result = teacherService.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_shouldThrowNotFound_whenTeacherNotExists() {
        when(teacherRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> teacherService.findById(2L));
    }

    @Test
    void findAll_shouldReturnListOfTeachers() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        List<Teacher> teachers = Collections.singletonList(teacher);
        when(teacherRepository.findAll()).thenReturn(teachers);
        List<Teacher> result = teacherService.findAll();
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }
}
