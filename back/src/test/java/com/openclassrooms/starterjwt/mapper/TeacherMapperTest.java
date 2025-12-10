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

        @Test
        void toEntity_shouldMapDtoListToTeacherList() {
            TeacherDto dto1 = new TeacherDto();
            dto1.setId(1L);
            dto1.setLastName("A");
            TeacherDto dto2 = new TeacherDto();
            dto2.setId(2L);
            dto2.setLastName("B");
            var dtoList = java.util.Arrays.asList(dto1, dto2);
            var teacherList = mapper.toEntity(dtoList);
            assertEquals(2, teacherList.size());
            assertEquals(dto1.getId(), teacherList.get(0).getId());
            assertEquals(dto2.getId(), teacherList.get(1).getId());
        }

        @Test
        void toDto_shouldMapTeacherListToDtoList() {
            Teacher teacher1 = new Teacher();
            teacher1.setId(1L);
            teacher1.setLastName("A");
            Teacher teacher2 = new Teacher();
            teacher2.setId(2L);
            teacher2.setLastName("B");
            var teacherList = java.util.Arrays.asList(teacher1, teacher2);
            var dtoList = mapper.toDto(teacherList);
            assertEquals(2, dtoList.size());
            assertEquals(teacher1.getId(), dtoList.get(0).getId());
            assertEquals(teacher2.getId(), dtoList.get(1).getId());
        }
}
