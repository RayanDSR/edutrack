package com.edutrack.edutrack.controller;

import com.edutrack.model.Course;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CourseController {
    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return Arrays.asList(
            new Course(1L, "Intro to Java", "Learn the basics of Java programming"),
            new Course(2L, "Spring Boot 101", "Get started with Spring Boot"),
            new Course(3L, "MySQL for Beginners", "Learn how to use MySQL with Java"),
            new Course(4L, "Git Fundamentals", "Learn Git from the ground up")
        );
    }
}
