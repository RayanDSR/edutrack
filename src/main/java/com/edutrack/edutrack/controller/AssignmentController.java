package com.edutrack.edutrack.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutrack.edutrack.dto.AssignmentRequestDTO;
import com.edutrack.edutrack.dto.AssignmentResponseDTO;
import com.edutrack.edutrack.dto.AssignmentSummaryDTO;
import com.edutrack.edutrack.model.User;
import com.edutrack.edutrack.service.AssignmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/courses/{courseId}/assignments")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<AssignmentResponseDTO> createAssignment( @PathVariable Long courseId, @RequestBody @Valid AssignmentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assignmentService.createAssignment(courseId, dto));
    }

    @GetMapping
    public ResponseEntity<List<AssignmentSummaryDTO>> getAssignmentsForCourse(@PathVariable Long courseId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(assignmentService.getAssignmentsForCourse(courseId, user));
    }
}
