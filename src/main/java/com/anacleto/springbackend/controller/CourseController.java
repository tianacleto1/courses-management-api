package com.anacleto.springbackend.controller;

import com.anacleto.springbackend.model.Course;
import com.anacleto.springbackend.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> listCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id)
                            .map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.courseRepository.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return this.courseRepository.findById(id)
                                    .map(courseFound -> {
                                        courseFound.setName(course.getName());
                                        courseFound.setCategory(course.getCategory());

                                        return ResponseEntity.ok(this.courseRepository.save(courseFound));
                                    })
                                    .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        return this.courseRepository.findById(id)
                                    .map(courseFound -> {
                                        this.courseRepository.deleteById(courseFound.getId());

                                        return ResponseEntity.noContent().<Void>build();
                                    })
                                    .orElse(ResponseEntity.notFound().build());
    }
}
