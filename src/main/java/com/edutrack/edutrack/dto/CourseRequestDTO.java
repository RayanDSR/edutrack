package com.edutrack.edutrack.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequestDTO {

    @NotBlank(message = "Course name is required")
    private String title;

    @NotBlank(message = "Course description is required")
    private String description;

    private Long teacherId;
}
