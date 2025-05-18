package com.edutrack.edutrack.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.edutrack.edutrack.dto.EnrollmentResponseDTO;
import com.edutrack.edutrack.model.Course;
import com.edutrack.edutrack.model.Enrollment;
import com.edutrack.edutrack.model.User;

@Component
public class EnrollmentMapper {
    public EnrollmentResponseDTO toResponseDTO(Enrollment enrollment) {
        return EnrollmentResponseDTO.builder()
            .id(enrollment.getId())
            .courseTitle(enrollment.getCourse().getTitle())
            .enrolledAt(enrollment.getEnrolledAt())
            .completed(enrollment.isCompleted())
            .build();
    }

    public Enrollment toEntity(User student, Course course) {
        return Enrollment.builder()
            .student(student)
            .course(course)
            .enrolledAt(LocalDateTime.now())
            .completed(false)
            .build();
    }
}
