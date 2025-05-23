package com.edutrack.edutrack.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutrack.edutrack.dto.EnrollmentRequestDTO;
import com.edutrack.edutrack.dto.EnrollmentResponseDTO;
import com.edutrack.edutrack.model.User;
import com.edutrack.edutrack.service.EnrollmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> enrollInCourse(@Valid @RequestBody EnrollmentRequestDTO enrollmentRequestDTO, @AuthenticationPrincipal User user) {
        enrollmentService.enrollInCourse(enrollmentRequestDTO, user);
        return ResponseEntity.ok("Enrolled successfully");
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<EnrollmentResponseDTO>> myEnrollments(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(enrollmentService.getMyEnrollments(user));
    }
}
