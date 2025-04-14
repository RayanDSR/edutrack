package com.edutrack.edutrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutrack.edutrack.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    
}
