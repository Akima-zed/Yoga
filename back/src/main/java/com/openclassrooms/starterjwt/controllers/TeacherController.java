package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller pour gérer les enseignants.
 */
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    public TeacherController(TeacherService teacherService,
                             TeacherMapper teacherMapper) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> findById(@PathVariable Long id) {
        Teacher teacher = teacherService.findById(id);
        return ResponseEntity.ok(teacherMapper.toDto(teacher));
    }

    @GetMapping
    public ResponseEntity<List<TeacherDto>> findAll() {
        return ResponseEntity.ok(teacherMapper.toDto(teacherService.findAll()));
    }
}
