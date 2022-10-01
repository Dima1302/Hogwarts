package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("student")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService){
        this.facultyService = facultyService;
    }
    @GetMapping("{id}")// GET http://localhost:8080/faculties//23
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id){
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }


    @PostMapping// POST http://localhost:8080/faculties
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return   facultyService.createFaculty(faculty);
    }

    @PutMapping //PUT http://localhost:8080/faculties
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(foundFaculty);
    }
    @DeleteMapping("{id}")// DELETE http://localhost:8080/faculties//23
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
          facultyService.deleteFaculty(id);
          return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

}
