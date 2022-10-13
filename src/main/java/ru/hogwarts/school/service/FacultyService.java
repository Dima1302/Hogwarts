package ru.hogwarts.school.service;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Objects;

@Service

public class FacultyService {


    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {

        return facultyRepository.findByColor(color);
    }

    public Faculty findByNameOrColor(String name ){
        return facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(name,name);
    }

    public Collection<Student> findByStudent(long idFaculty) {
 return Objects.requireNonNull(facultyRepository.findById(idFaculty).orElse(null)).getStudents();

 }
}
