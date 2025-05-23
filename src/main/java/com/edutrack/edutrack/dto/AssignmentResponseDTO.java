package com.edutrack.edutrack.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentResponseDTO {

    private Long id;

    private String title;

    private String description;

    private LocalDate dueDate;

    private Long courseId;
}
