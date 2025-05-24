package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edutrack.edutrack.dto.AssignmentRequestDTO;
import com.edutrack.edutrack.dto.AssignmentResponseDTO;
import com.edutrack.edutrack.dto.AssignmentSummaryDTO;
import com.edutrack.edutrack.exception.AccessDeniedException;
import com.edutrack.edutrack.exception.CourseNotFoundException;
import com.edutrack.edutrack.mapper.AssignmentMapper;
import com.edutrack.edutrack.model.Assignment;
import com.edutrack.edutrack.model.Course;
import com.edutrack.edutrack.model.Role;
import com.edutrack.edutrack.model.User;
import com.edutrack.edutrack.repository.AssignmentRepository;
import com.edutrack.edutrack.repository.CourseRepository;
import com.edutrack.edutrack.repository.EnrollmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final AssignmentMapper assignmentMapper;

    public AssignmentResponseDTO createAssignment(Long courseId, AssignmentRequestDTO assignmentRequestDTO, User user) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        if (!course.getTeacher().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not authorized to create assignments for this course.");
        }

        Assignment assignment = assignmentMapper.toEntity(assignmentRequestDTO, course);

        assignmentRepository.save(assignment);

        return assignmentMapper.toResponseDTO(assignment);
    }

    public List<AssignmentSummaryDTO> getAssignmentsForCourse(Long courseId, User currentUser) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        if (!isUserAllowedToViewCourse(course, currentUser)) {
            throw new AccessDeniedException("You are not allowed to view assignments for this course.");
        }

        return assignmentRepository.findByCourseId(courseId).stream()
            .map(assignmentMapper::toSummaryDTO)
            .toList();
    }

    private boolean isUserAllowedToViewCourse(Course course, User user) {
        if (user.getRole().equals(Role.ADMIN) || course.getTeacher().getId().equals(user.getId())) {
            return true;
        }

        // if (user.getRole().equals(Role.STUDENT)) {
            return enrollmentRepository.existsByStudentAndCourse(user, course);
        // }

        // return false;
    }
}
