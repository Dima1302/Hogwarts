package ru.hogwarts.school.service;



import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.Collection;
import java.util.Objects;

@Service

public class StudentService {
    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;

    }


    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Ожидается min < max");
        }
        return studentRepository.findByAgeBetween(min, max);

    }
    public Faculty findByFaculty(long idStudents) {
        return Objects.requireNonNull(studentRepository.findById(idStudents).orElse(null)).getFaculty();
    }


}


