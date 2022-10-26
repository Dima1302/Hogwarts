--liquibase formatted sql

--changeSet dmitriy:11
CREATE TABLE students (
                       id SERIAL,
                       name TEXT,
                       age SERIAL
)
--changeSet dmitriy:12
CREATE INDEX find_student_by_name ON students(name);

--changeSet dmitriy:13
CREATE TABLE faculties (
                        name TEXT,
                        color TEXT

)
--changeSet dmitriy:14
CREATE INDEX find_faculty_by_name_and_color ON faculties(name, color) ;




