package com.edutrack.edutrack.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentRequestDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;

    private String description;

    @FutureOrPresent(message = "Due date must be in the future or today")
    private LocalDate dueDate;
}
