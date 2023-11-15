package com.anacleto.springbackend.dto.mapper;

import com.anacleto.springbackend.dto.CourseDTO;
import com.anacleto.springbackend.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (this.isNull(course))
            return null;

        return new CourseDTO(course.getId(), course.getName(), course.getCategory());
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (isNull(courseDTO))
            return null;

        Course course = new Course();

        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }

        course.setName(courseDTO.name());
        course.setCategory(courseDTO.category());

        return course;
    }

    private boolean isNull(Object entity) {
        return entity == null;
    }
}
