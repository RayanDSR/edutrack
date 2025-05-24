package com.edutrack.edutrack.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.edutrack.edutrack.dto.SubmissionRequestDTO;
import com.edutrack.edutrack.dto.SubmissionResponseDTO;
import com.edutrack.edutrack.model.Assignment;
import com.edutrack.edutrack.model.Submission;
import com.edutrack.edutrack.model.SubmissionStatus;
import com.edutrack.edutrack.model.User;

@Component
public class SubmissionMapper {
    public Submission toEntity(SubmissionRequestDTO dto, Assignment assignment, User student) {
        return Submission.builder()
            .content(dto.getContent())
            .submissionDate(LocalDate.now())
            .status(SubmissionStatus.SUBMITTED)
            .assignment(assignment)
            .student(student)
            .build();
    }

    public SubmissionResponseDTO toResponseDTO(Submission submission) {
        return SubmissionResponseDTO.builder()
            .id(submission.getId())
            .content(submission.getContent())
            .submissionDate(submission.getSubmissionDate())
            .status(submission.getStatus().name())
            .grade(submission.getGrade())
            .assignmentId(submission.getAssignment().getId())
            .studentId(submission.getStudent().getId())
            .build();
    }
}
