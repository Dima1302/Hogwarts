package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Validated
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("{id}")// GET http://localhost:8080/students//23
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping//POST http://localhost:8080/students
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping // PUT http://localhost:8080/students
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")// DELETE http://localhost:8080/students//23
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "age")
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping(params = {"min","max"})
    public ResponseEntity<Collection<Student>> findStudentsWithAgeBetween(@RequestParam int min,
                                                                          @RequestParam int max) {

        Collection<Student> students = studentService.findByAgeBetween(min, max);
        return ResponseEntity.ok(students);
    }


    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> findStudentFaculty(@PathVariable("id") int idStudent) {
        return ResponseEntity.ok(studentService.findByFaculty(idStudent));
    }

    @GetMapping("/totalCount")
    public int totalCountOfStudents() {
        return studentService.totalCountOfStudents();
    }

    @GetMapping("/averageAge")
    public double averageAgeOfStudents() {
        return studentService.averageAgeOfStudents();
    }

    @GetMapping("/lastStudents")
    public ResponseEntity<Collection<Student>> lastStudents(@RequestParam @Min(1) @Max(10) int count ) {
        Collection<Student> students = studentService.lastStudents(count);
        return ResponseEntity.ok(students);
    }



}

