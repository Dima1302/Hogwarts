--liquibase formatted sql


--changeSet dmitriy:12
CREATE INDEX find_student_by_name ON students(name);


--changeSet dmitriy:14
CREATE INDEX find_faculty_by_name_and_color ON faculties(name, color) ;




