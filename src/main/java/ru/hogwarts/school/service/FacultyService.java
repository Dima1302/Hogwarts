package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Objects;

@Service

public class FacultyService {
    private final static Logger logger = LoggerFactory.getLogger(FacultyService.class);


    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("Was invoked method for find faculty by color");
        return facultyRepository.findByColor(color);
    }

    public Faculty findByNameOrColor(String name ){
        logger.info("Was invoked method for find faculty by name or color");
        return facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(name,name);
    }

    public Collection<Student> findByStudent(long idFaculty) {
        logger.info("Was invoked method for find faculty by student");
 return Objects.requireNonNull(facultyRepository.findById(idFaculty).orElse(null)).getStudents();

 }
}
