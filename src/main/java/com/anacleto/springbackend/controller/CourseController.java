package com.anacleto.springbackend.controller;

import com.anacleto.springbackend.dto.CourseDTO;
import com.anacleto.springbackend.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDTO> listCourses() {
        return this.courseService.listCourses();
    }

    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable @NotNull @Positive Long id) {
        return this.courseService.findCourseById(id);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody @Valid CourseDTO courseDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.createCourse(courseDTO));
    }

    @PutMapping("/{id}")
    public CourseDTO updateCourse(
            @PathVariable @NotNull @Positive Long id,
            @RequestBody @Valid CourseDTO courseDTO
    ) {
        return this.courseService.updateCourse(id, courseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable @NotNull @Positive Long id) {
        this.courseService.deleteCourse(id);
    }
}
