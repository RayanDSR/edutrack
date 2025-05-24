package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edutrack.edutrack.dto.SubmissionRequestDTO;
import com.edutrack.edutrack.dto.SubmissionResponseDTO;
import com.edutrack.edutrack.exception.AccessDeniedException;
import com.edutrack.edutrack.exception.AlreadySubmittedException;
import com.edutrack.edutrack.exception.EntityNotFoundException;
import com.edutrack.edutrack.mapper.SubmissionMapper;
import com.edutrack.edutrack.model.Assignment;
import com.edutrack.edutrack.model.Course;
import com.edutrack.edutrack.model.Role;
import com.edutrack.edutrack.model.Submission;
import com.edutrack.edutrack.model.SubmissionStatus;
import com.edutrack.edutrack.model.User;
import com.edutrack.edutrack.repository.AssignmentRepository;
import com.edutrack.edutrack.repository.EnrollmentRepository;
import com.edutrack.edutrack.repository.SubmissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SubmissionMapper submissionMapper;

    public SubmissionResponseDTO submitAssignment(Long assignmentId, SubmissionRequestDTO dto, User student) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        boolean isEnrolled = enrollmentRepository.existsByStudentAndCourse(student, assignment.getCourse());

        if (!isEnrolled) {
            throw new AccessDeniedException("You are not enrolled in this course.");
        }

        if (submissionRepository.findByAssignmentAndStudent(assignment, student).isPresent()) {
            throw new AlreadySubmittedException("You've already submitted this assignment.");
        }

        Submission submission = submissionMapper.toEntity(dto, assignment, student);
        submissionRepository.save(submission);

        return submissionMapper.toResponseDTO(submission);
    }

    public List<SubmissionResponseDTO> getSubmissionsForAssignment(Long assignmentId, User user) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        Course course = assignment.getCourse();

        if (!user.getId().equals(course.getTeacher().getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Not allowed to view these submissions.");
        }

        return submissionRepository.findByAssignment(assignment).stream()
                .map(submissionMapper::toResponseDTO)
                .toList();
    }

    public SubmissionResponseDTO gradeSubmission(Long submissionId, String grade, User teacher) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new EntityNotFoundException("Submission not found"));

        Course course = submission.getAssignment().getCourse();

        if (!teacher.getId().equals(course.getTeacher().getId()) && !teacher.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Not allowed to grade this submission.");
        }

        submission.setGrade(grade);
        submission.setStatus(SubmissionStatus.GRADED);
        submissionRepository.save(submission);

        return submissionMapper.toResponseDTO(submission);
    }
}
