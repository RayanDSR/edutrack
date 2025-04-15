package com.edutrack.edutrack.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseRequestDTO {

    @NotBlank(message = "Course name is required")
    private String title;

    @NotBlank(message = "Course description is required")
    private String description;

    private Long teacherId;
}
