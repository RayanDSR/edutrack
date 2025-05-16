package com.edutrack.edutrack.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutrack.edutrack.dto.CourseCreateDTO;
import com.edutrack.edutrack.dto.CourseResponseDTO;
import com.edutrack.edutrack.dto.CourseUpdateDTO;
import com.edutrack.edutrack.dto.UserResponseDTO;
import com.edutrack.edutrack.service.CourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public List<CourseResponseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseResponseDTO getCourseById(@PathVariable Long id) {
        return courseService.getCourse(id);
    }

    @PostMapping
    public CourseResponseDTO createCourse(@Valid @RequestBody CourseCreateDTO newCourse) {
        return courseService.createCourse(newCourse);
    }

    @PatchMapping("/{id}")
    public CourseResponseDTO updateCourse(@PathVariable Long id, @Valid @RequestBody CourseUpdateDTO updatedCourse) {
        return courseService.updateCourse(id, updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/user")
    public UserResponseDTO getUserByCourse(@PathVariable Long id) {
        return courseService.getUserForCourse(id);
    }
}
