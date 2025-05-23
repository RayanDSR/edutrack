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
public class AssignmentSummaryDTO {

    private Long id;

    private String title;

    private LocalDate dueDate;
}
