package ru.hogwarts.school;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import java.net.URI;
import java.util.Collection;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HogwartsApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
   private TestRestTemplate restTemplate;

    @Test
    public void createStudentTest()  {
        Student student = studentTest("Hugo",20);
        ResponseEntity<Student> response = whenSendingCreateStudentRequest(getUriBuilder().build().toUri() ,student);
        thenStudentHasBeenCreated(response);

    }

    @Test
    public void getStudentByIdTest() {
        Student student = studentTest("Hugo",20);
        ResponseEntity<Student> createResponse = whenSendingCreateStudentRequest(getUriBuilder().build().toUri() ,student);
        thenStudentHasBeenCreated(createResponse);

        Student createdStudent = createResponse.getBody();
        thenStudentWithIdHasBeenFound(createdStudent.getId(),createdStudent);
    }

    @Test
    public void findByAgeTest(){
        Student student1 = studentTest("Hugo",20);
        Student student2 = studentTest("Gleb",15);
        Student student3 = studentTest("Oleg",12);
        Student student4 = studentTest("Viktor",25);


        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(),student1);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(),student2);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(),student3);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(),student4);

        MultiValueMap<String,String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("age", "25");
         thenStudentsFound(queryParams,student4);
    }

    @Test
    public void findByAgeBetweenTest(){
        Student student1 = studentTest("Hugo",20);
        Student student2 = studentTest("Gleb",15);
        Student student3 = studentTest("Oleg",12);
        Student student4 = studentTest("Viktor",25);


        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(),student1);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(),student2);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(),student3);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(),student4);

        MultiValueMap<String,String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("minAge", "12");
        queryParams.add("maxAge", "30");
        thenStudentsFound(queryParams,student4,student3);
    }


    @Test
    public void testUpdate() {
        Student student = studentTest("Hugo",20);
        ResponseEntity<Student> responseEntity = whenSendingCreateStudentRequest(getUriBuilder().build().toUri() ,student);
        thenStudentHasBeenCreated(responseEntity);

        Student createdStudent = responseEntity.getBody();

        whenUpdatedStudent(createdStudent,32,"newName");
        thenStudentHasBeenUpdated(createdStudent,32,"newName");
    }

    @Test
    public void deleteTest() {
        Student student = studentTest("Hugo",20);
        ResponseEntity<Student> responseEntity = whenSendingCreateStudentRequest(getUriBuilder().build().toUri() ,student);
        thenStudentHasBeenCreated(responseEntity);

        Student createdStudent = responseEntity.getBody();
whenDeletingStudent(createdStudent);
thenStudentNotFound(createdStudent);

    }

    private void  whenDeletingStudent(Student createdStudent) {
        restTemplate.delete(getUriBuilder().cloneBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri());
    }
    private void thenStudentNotFound(Student createdStudent) {
        URI uri = getUriBuilder().cloneBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri();
        ResponseEntity<Student>emptyRs = restTemplate.getForEntity(uri, Student.class);
        Assertions.assertThat(emptyRs.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    private void thenStudentsFound(MultiValueMap<String,String> queryParams, Student...students ) {
URI uri = getUriBuilder().queryParams(queryParams).build().toUri();

ResponseEntity<Student> response = restTemplate.exchange(
        uri,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Student>() {
        });
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

         Set<Student> actualResult = (Set<Student>) response.getBody();
        resetIds((Collection<Student>) actualResult);
        Assertions.assertThat(actualResult).containsExactlyInAnyOrder(students);
    }

    private void whenUpdatedStudent (Student createdStudent, int newAge,String newName) {
        createdStudent.setAge(newAge);
        createdStudent.setName(newName);
    }

    private void thenStudentHasBeenUpdated(Student createdStudent,int newAge,String newName) {
        URI uri = getUriBuilder().cloneBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri();
        ResponseEntity<Student>updatedStudentRs = restTemplate.getForEntity(uri, Student.class);
        Assertions.assertThat(updatedStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(updatedStudentRs.getBody()).isNotNull();
        Assertions.assertThat(updatedStudentRs.getBody().getAge()).isEqualTo(newAge);
        Assertions.assertThat(updatedStudentRs.getBody().getName()).isEqualTo(newName);


    }

    private void thenStudentWithIdHasBeenFound(Long studentId, Student student) {
        URI uri = getUriBuilder().cloneBuilder().path("/{id}").buildAndExpand(studentId).toUri();
        ResponseEntity<Student>response = restTemplate.getForEntity(uri, Student.class);
        Assertions.assertThat(response.getBody()).isEqualTo(student);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    private void thenStudentHasBeenCreated(ResponseEntity<Student> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }

    private Student studentTest(String name, int age) {
        return  new Student();
    }

    private void resetIds(Collection<Student> students) {
        students.forEach(it -> it.setId(null));
    }

    private ResponseEntity<Student> whenSendingCreateStudentRequest(URI uri, Student student) {
        return restTemplate.postForEntity(uri,student,Student.class);
    }


    private UriComponentsBuilder getUriBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/hogwarts/student");
    }


    }

