package com.edutrack.edutrack.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.edutrack.edutrack.dto.TeacherDTO;
import com.edutrack.edutrack.model.Course;
import com.edutrack.edutrack.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses().stream()
            .map(CourseDTO::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable Long id) {
        return new CourseDTO(courseService.getCourse(id));
    }

    @PostMapping
    public CourseDTO createCourse(@RequestBody Course newCourse) {
        return new CourseDTO(courseService.createCourse(newCourse));
    }

    @PutMapping("/{id}")
    public CourseDTO updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        return new CourseDTO(courseService.updateCourse(id, updatedCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/teacher")
    public TeacherDTO getTeacherByCourse(@PathVariable Long id) {
        return new TeacherDTO(courseService.getTeacherForCourse(id));
    }
}
