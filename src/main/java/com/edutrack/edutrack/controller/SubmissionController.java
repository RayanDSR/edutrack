package com.edutrack.edutrack.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edutrack.edutrack.dto.SubmissionRequestDTO;
import com.edutrack.edutrack.dto.SubmissionResponseDTO;
import com.edutrack.edutrack.model.User;
import com.edutrack.edutrack.service.SubmissionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping("/{assignmentId}/submit")
    public ResponseEntity<SubmissionResponseDTO> submit(
            @PathVariable Long assignmentId,
            @RequestBody @Valid SubmissionRequestDTO dto,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(submissionService.submitAssignment(assignmentId, dto, student));
    }

    @GetMapping("/{assignmentId}/my-submissions")
    public ResponseEntity<List<SubmissionResponseDTO>> getAllForAssignment(
            @PathVariable Long assignmentId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(submissionService.getSubmissionsForAssignment(assignmentId, user));
    }

    @PutMapping("/submissions/{submissionId}/grade")
    public ResponseEntity<SubmissionResponseDTO> grade(
            @PathVariable Long submissionId,
            @RequestParam String grade,
            @AuthenticationPrincipal User teacher
    ) {
        return ResponseEntity.ok(submissionService.gradeSubmission(submissionId, grade, teacher));
    }
}
