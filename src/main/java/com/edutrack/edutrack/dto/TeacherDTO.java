package com.edutrack.edutrack.dto;

import com.edutrack.edutrack.model.Teacher;

import lombok.Data;

@Data
public class TeacherDTO {
    private Long id;
    private String name;
    private String email;

    public TeacherDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.email = teacher.getEmail();
    }
}
