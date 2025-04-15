package com.edutrack.edutrack.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeacherRequestDTO {

    @NotBlank(message = "Teacher name is required")
    private String name;

    @NotBlank(message = "Teacher email is required")
    private String email;
}
