package com.edutrack.edutrack.mapper;

import org.springframework.stereotype.Component;

import com.edutrack.edutrack.dto.AssignmentRequestDTO;
import com.edutrack.edutrack.dto.AssignmentResponseDTO;
import com.edutrack.edutrack.dto.AssignmentSummaryDTO;
import com.edutrack.edutrack.model.Assignment;
import com.edutrack.edutrack.model.Course;

@Component
public class AssignmentMapper {

    public Assignment toEntity(AssignmentRequestDTO dto, Course course) {
        return Assignment.builder()
            .title(dto.getTitle())
            .description(dto.getDescription())
            .dueDate(dto.getDueDate())
            .course(course)
            .build();
    }

    public AssignmentResponseDTO toResponseDTO(Assignment assignment) {
        return AssignmentResponseDTO.builder()
            .id(assignment.getId())
            .title(assignment.getTitle())
            .description(assignment.getDescription())
            .dueDate(assignment.getDueDate())
            .courseId(assignment.getCourse().getId())
            .build();
    }

    public AssignmentSummaryDTO toSummaryDTO(Assignment assignment) {
        return AssignmentSummaryDTO.builder()
            .id(assignment.getId())
            .title(assignment.getTitle())
            .dueDate(assignment.getDueDate())
            .build();
    }
}
