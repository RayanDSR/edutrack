package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edutrack.edutrack.dto.EnrollmentRequestDTO;
import com.edutrack.edutrack.dto.EnrollmentResponseDTO;
import com.edutrack.edutrack.exception.AlreadyEnrolledException;
import com.edutrack.edutrack.exception.CourseNotFoundException;
import com.edutrack.edutrack.mapper.EnrollmentMapper;
import com.edutrack.edutrack.model.Course;
import com.edutrack.edutrack.model.Enrollment;
import com.edutrack.edutrack.model.User;
import com.edutrack.edutrack.repository.CourseRepository;
import com.edutrack.edutrack.repository.EnrollmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final AuthService authService;
    private final EnrollmentMapper enrollmentMapper;

    public void enrollInCourse(EnrollmentRequestDTO enrollmentRequestDTO) {
        Long courseId = enrollmentRequestDTO.getCourseId();

        User student = authService.getCurrentUser();

        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new CourseNotFoundException(courseId));

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new AlreadyEnrolledException("User is already enrolled in this course");
        }

        Enrollment enrollment = enrollmentMapper.toEntity(student, course);

        enrollmentRepository.save(enrollment);
    }

    public List<EnrollmentResponseDTO> getMyEnrollments() {
        User student = authService.getCurrentUser();

        return enrollmentRepository.findByStudent(student).stream()
            .map(enrollmentMapper::toResponseDTO)
            .toList();
    }
}
