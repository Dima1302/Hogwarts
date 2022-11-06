package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

@Service

public class FacultyService {
    private final static Logger LOGGER = LoggerFactory.getLogger(FacultyService.class);


    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        LOGGER.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        LOGGER.info("Was invoked method for find faculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        LOGGER.info("Was invoked method for edit faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        LOGGER.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        LOGGER.info("Was invoked method for find faculty by color");
        return facultyRepository.findByColor(color);
    }

    public Faculty findByNameOrColor(String name ){
        LOGGER.info("Was invoked method for find faculty by name or color");
        return facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(name,name);
    }

    public Collection<Student> findByStudent(long idFaculty) {
        LOGGER.info("Was invoked method for find faculty by student");
 return Objects.requireNonNull(facultyRepository.findById(idFaculty).orElse(null)).getStudents();

 }

 public ResponseEntity<String>findFacultyNameWithTheMaxLength() {
     LOGGER.info("Was invoked method for find faculty name with max length");
     Optional <String> maxFacultyName = facultyRepository
             .findAll().stream().map(Faculty::getName).max(Comparator.comparing(String::length));

     if (maxFacultyName.isEmpty()){
         LOGGER.error("There is no faculties");
         return ResponseEntity.notFound().build();
     } else {
         LOGGER.error("Faculty with the max length is: {}", maxFacultyName.get());

     } return ResponseEntity.ok(maxFacultyName.get());

 }
}
