package com.edutrack.edutrack.mapper;

import org.springframework.stereotype.Component;

import com.edutrack.edutrack.dto.CourseCreateDTO;
import com.edutrack.edutrack.dto.CourseResponseDTO;
import com.edutrack.edutrack.dto.CourseUpdateDTO;
import com.edutrack.edutrack.model.Course;
import com.edutrack.edutrack.model.User;

@Component
public class CourseMapper {
    public CourseResponseDTO toResponseDTO(Course course) {
        return CourseResponseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .teacherName(course.getTeacher().getName())
                .build();
    }

    public Course toEntity(CourseCreateDTO courseCreateDTO, User teacher) {
        return Course.builder()
                .title(courseCreateDTO.getTitle())
                .description(courseCreateDTO.getDescription())
                .teacher(teacher)
                .build();
    }

    public void updateEntity(CourseUpdateDTO courseUpdateDTO, Course course, User teacher) {
        if (courseUpdateDTO.getTitle() != null && !courseUpdateDTO.getTitle().isEmpty()) {
            course.setTitle(courseUpdateDTO.getTitle());
        }

        if (courseUpdateDTO.getDescription() != null && !courseUpdateDTO.getDescription().isEmpty()) {
            course.setDescription(courseUpdateDTO.getDescription());
        }

        if (courseUpdateDTO.getTeacherId() != null) {
            course.setTeacher(teacher);
        }
    }
}
