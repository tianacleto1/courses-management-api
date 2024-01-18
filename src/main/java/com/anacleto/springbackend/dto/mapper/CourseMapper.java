package com.anacleto.springbackend.dto.mapper;

import com.anacleto.springbackend.dto.CourseDTO;
import com.anacleto.springbackend.enums.Category;
import com.anacleto.springbackend.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (this.isNull(course))
            return null;

        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue());
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (isNull(courseDTO))
            return null;

        Course course = new Course();

        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }

        course.setName(courseDTO.name());
        course.setCategory(this.convertCategoryValue(courseDTO.category()));

        return course;
    }

    public Category convertCategoryValue(String value) {
        if (isNull(value))
            return null;

        return switch (value) {
            case "Front-end" -> Category.FRONT_END;
            case "Back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Invalid category: " + value);
        };
    }

    private boolean isNull(Object entity) {
        return entity == null;
    }
}
