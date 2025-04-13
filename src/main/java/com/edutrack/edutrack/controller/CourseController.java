package com.edutrack.edutrack.controller;

import com.edutrack.model.Course;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @PutMapping("/courses/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        for (Course course : courses) {
            if (course.getId().equals(id)) {
                course.setTitle(updatedCourse.getTitle());
                course.setDescription(updatedCourse.getDescription());
                return course;
            }
        }
        throw new RuntimeException("Course not found with ID: " + id);
    }

    @DeleteMapping("/courses/{id}")
    public String deleteCourse(@PathVariable Long id) {
        Iterator<Course> iterator = courses.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (course.getId().equals(id)) {
                iterator.remove();
                return "Course deleted successfully";
            }
        }
        throw new RuntimeException("Course not found with ID: " + id);
    }
}
