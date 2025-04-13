package com.edutrack.edutrack.controller;

import com.edutrack.model.Course;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class CourseController {
    private List<Course> courses = new ArrayList<>();
    private Long courseIdCounter = 1L;

    public CourseController() {
        courses.add(new Course(courseIdCounter++, "Intro to Java", "Learn the basics of Java programming"));
        courses.add(new Course(courseIdCounter++, "Spring Boot 101", "Get started with Spring Boot"));
        courses.add(new Course(courseIdCounter++, "MySQL for Beginners", "Learn how to use MySQL with Java"));
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courses;
    }

    @PostMapping("/courses")
    public Course createCourse(@RequestBody Course newCourse) {
        newCourse.setId(courseIdCounter++);
        courses.add(newCourse);
        return newCourse;
    }
    
}
