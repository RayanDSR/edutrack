package com.edutrack.edutrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutrack.edutrack.model.Assignment;
import com.edutrack.edutrack.model.Course;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCourse(Course course);
}
