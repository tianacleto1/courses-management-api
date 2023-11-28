package com.anacleto.springbackend.service;


import com.anacleto.springbackend.dto.CourseDTO;
import com.anacleto.springbackend.dto.mapper.CourseMapper;
import com.anacleto.springbackend.exception.RecordNotFoundException;
import com.anacleto.springbackend.model.Course;
import com.anacleto.springbackend.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    private final CourseRepository repository = mock(CourseRepository.class);

    private final CourseService courseService = new CourseService(
            repository, new CourseMapper()
    );

    private final Course courseMock = createCourseMock();
    private final CourseDTO courseDTOMock = createCourseDTOMock();

    @Test
    void whenListCourseCalledItShouldReturnAListOfCourses() {
        when(repository.findAll()).thenReturn(List.of(courseMock));

        List<CourseDTO> result = courseService.listCourses();

        assertEquals("Fullstack Angular and Spring", result.get(0).name());
    }

    @Test
    void whenFindCourseByIdIsCalled_ItShouldReturnTheCourseWithId() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(courseMock));

        CourseDTO result = courseService.findCourseById(1L);

        assertEquals(1L, result.id());
        assertEquals("Fullstack Angular and Spring", result.name());
    }

    @Test
    void whenFindCourseByIdIsNotFound_ItShouldThrowRecordNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        var thrown = assertThrows(
            RecordNotFoundException.class,
            () -> courseService.findCourseById(1L)
        );

        assertSame(RecordNotFoundException.class, thrown.getClass());
        assertEquals("Course with id 1 not found!", thrown.getMessage());

//        try {
//            courseService.findCourseById(1L);
//            fail("It should throw RecordNotFoundException");
//        } catch (RecordNotFoundException ex) {
//            assertSame(RecordNotFoundException.class, ex.getClass());
//            assertEquals("Course with id 1 not found!", ex.getMessage());
//        }
    }

    @Test
    void whenCreateCourseIsCalled_ItShouldCreateAndReturnNewCourse() {
        when(repository.save(any())).thenReturn(courseMock);

        CourseDTO result = courseService.createCourse(any());

        assertEquals(1L, result.id());
        assertEquals("Fullstack Angular and Spring", result.name());
    }

    @Test
    void whenUpdateCourseIsCalled_ItShouldUpdateAndReturnCourse() {
        when(repository.findById(any())).thenReturn(Optional.of(courseMock));
        when(repository.save(any())).thenReturn(courseMock);

        CourseDTO result = courseService.updateCourse(1L, this.courseDTOMock);

        assertEquals(1L, result.id());
        assertEquals("UpdatedCourse", result.name());
    }

    @Test
    void whenCourseToUpdateIsNotFound_ItShouldThrowRecordNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        var thrown = assertThrows(
                RecordNotFoundException.class,
                () -> courseService.updateCourse(1L, courseDTOMock)
        );

        assertSame(RecordNotFoundException.class, thrown.getClass());
        assertEquals("Course with id 1 not found!", thrown.getMessage());
    }

    @Test
    void whenDeleteCourseIsCalled_ItShouldDeleteTheCourse() {
        when(repository.findById(any())).thenReturn(Optional.of(courseMock));

        this.courseService.deleteCourse(1L);
    }

    @Test
    void whenCourseToDeleteIsNotFound_ItShouldThrowRecordNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        var thrown = assertThrows(
                RecordNotFoundException.class,
                () -> courseService.deleteCourse(1L)
        );

        assertSame(RecordNotFoundException.class, thrown.getClass());
        assertEquals("Course with id 1 not found!", thrown.getMessage());
    }

    private Course createCourseMock() {
        Course course = new Course();
        course.setId(1L);
        course.setName("Fullstack Angular and Spring");
        course.setCategory("Front-end");

        return course;
    }

    private CourseDTO createCourseDTOMock() {
        return new CourseDTO(
            1L,
            "UpdatedCourse",
            "Front-end"
        );
    }
}
