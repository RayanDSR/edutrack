package com.edutrack.edutrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCreateDTO {

    @NotBlank(message = "Course name is required")
    private String title;

    @NotBlank(message = "Course description is required")
    private String description;

    @NotNull(message = "Teacher id is required")
    private Long teacherId;
}
