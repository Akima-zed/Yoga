package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class TeacherMapperTest {
    TeacherMapper mapper = Mappers.getMapper(TeacherMapper.class);

    @Test
    void toDto_shouldMapTeacherToDto() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("Doe");
        teacher.setFirstName("John");
        TeacherDto dto = mapper.toDto(teacher);
        assertEquals(teacher.getId(), dto.getId());
        assertEquals(teacher.getLastName(), dto.getLastName());
        assertEquals(teacher.getFirstName(), dto.getFirstName());
    }

    @Test
    void toEntity_shouldMapDtoToTeacher() {
        TeacherDto dto = new TeacherDto();
        dto.setId(2L);
        dto.setLastName("Smith");
        dto.setFirstName("Alice");
        Teacher teacher = mapper.toEntity(dto);
        assertEquals(dto.getId(), teacher.getId());
        assertEquals(dto.getLastName(), teacher.getLastName());
        assertEquals(dto.getFirstName(), teacher.getFirstName());
    }
}
