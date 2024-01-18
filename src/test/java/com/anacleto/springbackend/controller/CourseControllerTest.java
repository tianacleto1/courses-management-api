package com.anacleto.springbackend.controller;

import com.anacleto.springbackend.dto.CourseDTO;
import com.anacleto.springbackend.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(CourseController.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @MockBean
    private CourseService mockService;

    @Autowired
    private  CourseController courseController;

    @Autowired
    private MockMvc mockMvc;

    private final CourseDTO courseDTOMock = createCourseDTOMock();

    @Test
    public void getCoursesTest() throws Exception {
        when(mockService.listCourses()).thenReturn(List.of(courseDTOMock));

        this.mockMvc.perform(get("/api/courses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]._id", is(1)))
                .andReturn();
    }

    @Test
    public void findCourseByIdTest() throws Exception {
        when(mockService.findCourseById(any())).thenReturn(courseDTOMock);

        this.mockMvc.perform(get("/api/courses/{id}", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._id", is(1)))
                    .andExpect(jsonPath("$.name", is("UpdatedCourse")));
    }

    @Test
    public void createCourseTest() throws Exception {
        when(mockService.createCourse(any())).thenReturn(courseDTOMock);

         this.mockMvc.perform(post("/api/courses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\": \"testName\", \"category\": \"Front-end\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$._id", is(1)))
                    .andExpect(jsonPath("$.name", is("UpdatedCourse")));
    }

    private CourseDTO createCourseDTOMock() {
        return new CourseDTO(
                1L,
                "UpdatedCourse",
                "Front-end"
        );
    }
}
