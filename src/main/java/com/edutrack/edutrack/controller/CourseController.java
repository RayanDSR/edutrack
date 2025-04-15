package com.edutrack.edutrack.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutrack.edutrack.dto.CourseDTO;
import com.edutrack.edutrack.dto.CourseRequestDTO;
import com.edutrack.edutrack.dto.TeacherDTO;
import com.edutrack.edutrack.service.CourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable Long id) {
        return courseService.getCourse(id);
    }

    @PostMapping
    public CourseDTO createCourse(@Valid @RequestBody CourseRequestDTO newCourse) {
        return courseService.createCourse(newCourse);
    }

    @PutMapping("/{id}")
    public CourseDTO updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequestDTO updatedCourse) {
        return courseService.updateCourse(id, updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/teacher")
    public TeacherDTO getTeacherByCourse(@PathVariable Long id) {
        return courseService.getTeacherForCourse(id);
    }
}
