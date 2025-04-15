package com.edutrack.edutrack.dto;

import com.edutrack.edutrack.model.Course;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
