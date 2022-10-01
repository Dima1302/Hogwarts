package ru.hogwarts.school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

@Configuration
public class BeanConfig {
    @Bean
    public StudentService studentService() {
        return new StudentService();
    }

    @Bean
    public FacultyService facultyService() {
        return new FacultyService();
    }

}
