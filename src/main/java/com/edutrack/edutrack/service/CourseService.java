package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edutrack.edutrack.dto.CourseCreateDTO;
import com.edutrack.edutrack.dto.CourseResponseDTO;
import com.edutrack.edutrack.dto.CourseUpdateDTO;
import com.edutrack.edutrack.dto.UserResponseDTO;
import com.edutrack.edutrack.exception.CourseNotFoundException;
import com.edutrack.edutrack.exception.UserNotFoundException;
import com.edutrack.edutrack.mapper.CourseMapper;
import com.edutrack.edutrack.mapper.UserMapper;
import com.edutrack.edutrack.model.Course;
import com.edutrack.edutrack.model.User;
import com.edutrack.edutrack.repository.CourseRepository;
import com.edutrack.edutrack.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;
    private final UserMapper userMapper;

    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
            .map(courseMapper::toResponseDTO)
            .toList();
    }

    public CourseResponseDTO getCourse(Long id) {
        return courseMapper.toResponseDTO(courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id)));
    }

    public CourseResponseDTO createCourse(CourseCreateDTO request) {
        User teacher = userRepository.findById(request.getTeacherId())
            .orElseThrow(() -> new UserNotFoundException(request.getTeacherId()));

        Course course = courseMapper.toEntity(request, teacher);

        return courseMapper.toResponseDTO(courseRepository.save(course));
    }

    public CourseResponseDTO updateCourse(Long id, CourseUpdateDTO request) {
        User teacher = null;

        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        if (request.getTeacherId() != null) {
            teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new UserNotFoundException(request.getTeacherId()));
        }

        courseMapper.updateEntity(request, course, teacher);

        return courseMapper.toResponseDTO(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public UserResponseDTO getUserForCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        return userMapper.toResponseDTO(course.getTeacher());
    }
}
