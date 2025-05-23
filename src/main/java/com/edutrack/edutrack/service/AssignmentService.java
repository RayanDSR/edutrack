package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edutrack.edutrack.dto.AssignmentRequestDTO;
import com.edutrack.edutrack.dto.AssignmentResponseDTO;
import com.edutrack.edutrack.dto.AssignmentSummaryDTO;
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

    public AssignmentResponseDTO createAssignment(Long courseId, AssignmentRequestDTO assignmentRequestDTO) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        Assignment assignment = assignmentMapper.toEntity(assignmentRequestDTO, course);

        assignmentRepository.save(assignment);

        return assignmentMapper.toResponseDTO(assignment);
    }

    public List<AssignmentSummaryDTO> getAssignmentsForCourse(Long courseId, User currentUser) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        if (!isUserAllowedToViewCourse(course, currentUser)) {
            throw new RuntimeException("You can't view this course's assignments.");
        }

        return assignmentRepository.findByCourseId(courseId).stream()
            .map(assignmentMapper::toSummaryDTO)
            .toList();
    }

    private boolean isUserAllowedToViewCourse(Course course, User user) {
        if (user.getRole().equals(Role.ADMIN) || course.getTeacher().getId().equals(user.getId())) {
            return true;
        }

        return enrollmentRepository.findByCourse(course).stream()
            .anyMatch(enrollment -> enrollment.getStudent().getId().equals(user.getId()));
    }
}
