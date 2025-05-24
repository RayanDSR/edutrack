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
public class SubmissionResponseDTO {

    private Long id;

    private String content;

    private LocalDate submissionDate;

    private String status;

    private String grade;

    private Long assignmentId;

    private Long studentId;
}
