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
public class TeacherRequestDTO {

    @NotBlank(message = "Teacher name is required")
    private String name;

    @NotBlank(message = "Teacher email is required")
    private String email;
}
