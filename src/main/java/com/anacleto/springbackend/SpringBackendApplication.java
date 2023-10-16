package com.anacleto.springbackend;

import com.anacleto.springbackend.model.Course;
import com.anacleto.springbackend.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CourseRepository courseRepository) {
		return args -> {
			courseRepository.deleteAll();
			Course course = new Course();
			course.setName("Fullstack Angular and Spring");
			course.setCategory("Front-end");

			courseRepository.save(course);
		};
	}

}
