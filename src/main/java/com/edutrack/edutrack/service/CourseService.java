package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edutrack.edutrack.dto.CourseDTO;
import com.edutrack.edutrack.dto.CourseRequestDTO;
import com.edutrack.edutrack.dto.TeacherDTO;
import com.edutrack.edutrack.exception.CourseNotFoundException;
import com.edutrack.edutrack.exception.TeacherNotFoundException;
import com.edutrack.edutrack.model.Course;
import com.edutrack.edutrack.model.Teacher;
import com.edutrack.edutrack.repository.CourseRepository;
import com.edutrack.edutrack.repository.TeacherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
            .map(CourseDTO::new)
            .toList();
    }

    public CourseDTO getCourse(Long id) {
        return new CourseDTO(courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id)));
    }

    public CourseDTO createCourse(CourseRequestDTO request) {
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
            .orElseThrow(() -> new TeacherNotFoundException(request.getTeacherId()));

        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setTeacher(teacher);

        return new CourseDTO(courseRepository.save(course));
    }

    public CourseDTO updateCourse(Long id, CourseRequestDTO request) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
            .orElseThrow(() -> new TeacherNotFoundException(request.getTeacherId()));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setTeacher(teacher);

        return new CourseDTO(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public TeacherDTO getTeacherForCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        return new TeacherDTO(course.getTeacher());
    }
}
