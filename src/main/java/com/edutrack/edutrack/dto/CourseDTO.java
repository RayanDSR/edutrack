package com.edutrack.edutrack.dto;

import com.edutrack.edutrack.model.Course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private String teacherName;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.teacherName = course.getTeacher() != null ? course.getTeacher().getName() : null;
    }
}
