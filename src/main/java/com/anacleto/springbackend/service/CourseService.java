package com.anacleto.springbackend.service;

import com.anacleto.springbackend.dto.CourseDTO;
import com.anacleto.springbackend.dto.mapper.CourseMapper;
import com.anacleto.springbackend.exception.RecordNotFoundException;
import com.anacleto.springbackend.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Validated
@Service
public class CourseService {

    private static final String ERROR_MESSAGE = "Course with id %s not found!";

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> listCourses() {
        return courseRepository.findAll()
                            .stream()
                            .map(courseMapper::toDTO)
                            .toList();
    }

    public CourseDTO findCourseById(@PathVariable @NotNull @Positive Long id) {
        return this.courseRepository.findById(id)
                                    .map(courseMapper::toDTO)
                                    .orElseThrow(
                                        () -> new RecordNotFoundException(String.format(ERROR_MESSAGE, id)));
    }

    public CourseDTO createCourse(@Valid @NotNull CourseDTO courseDTO) {
        return courseMapper.toDTO(
            this.courseRepository.save(this.courseMapper.toEntity(courseDTO))
        );
    }

    public CourseDTO updateCourse(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return this.courseRepository.findById(id)
                    .map(courseFound -> {
                        courseFound.setName(courseDTO.name());
                        courseFound.setCategory(courseDTO.category());

                        return courseMapper.toDTO(this.courseRepository.save(courseFound));
                    }).orElseThrow(
                        () -> new RecordNotFoundException(String.format(ERROR_MESSAGE, id))
                    );
    }

    public void deleteCourse(@PathVariable @NotNull @Positive Long id) {
        this.courseRepository.delete(
            this.courseRepository.findById(id)
                                .orElseThrow(
                                    () -> new RecordNotFoundException(String.format(ERROR_MESSAGE, id))
                                )
        );
    }
}
