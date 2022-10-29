package ru.hogwarts.school.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.Collection;
import java.util.Objects;

@Service

public class StudentService {
    private final StudentRepository studentRepository;
    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;

    }


    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {

        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for find student by age");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find student by age between");
        if (min > max) {
            logger.error("Wrong value of min = " + min);
            throw new IllegalArgumentException("Expected min < max");
        }
        return studentRepository.findByAgeBetween(min, max);

    }
    public Faculty findByFaculty(long idStudents) {
        logger.info("Was invoked method for find student by faculty");
        return Objects.requireNonNull(studentRepository.findById(idStudents).orElse(null)).getFaculty();
    }

    public int totalCountOfStudents() {
        logger.info("Was invoked method for total count of students");
        return studentRepository.totalCountOfStudents();
    }

    public double averageAgeOfStudents() {
        logger.info("Was invoked method for find average age of students");
        return studentRepository.averageAgeOfStudents();
    }

    public Collection<Student> lastStudents(int count) {
        logger.info("Was invoked method for count last students");
        return studentRepository.lastStudents(count);
    }


}


