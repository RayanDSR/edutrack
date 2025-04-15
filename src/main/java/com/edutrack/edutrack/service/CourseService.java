package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edutrack.edutrack.exception.CourseNotFoundException;
import com.edutrack.edutrack.model.Course;
import com.edutrack.edutrack.model.Teacher;
import com.edutrack.edutrack.repository.CourseRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Course course = getCourse(id);

        if (courseDetails.getTitle() != null) {
            course.setTitle(courseDetails.getTitle());
        }

        if (courseDetails.getDescription() != null) {
            course.setDescription(courseDetails.getDescription());
        }

        if (courseDetails.getTeacher() != null) {
            course.setTeacher(courseDetails.getTeacher());
        }

        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Teacher getTeacherForCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
        return course.getTeacher();
    }
}
