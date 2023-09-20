--h2 is typically used to setup a test database, not a prod database.
--first, drop your tables (to reset your database for testing)
--then create your tables


drop table StudentCourses if exists;
drop table TeacherCourses if exists;
drop table Student if exists;
drop table Teacher if exists;
drop table Course if exists;

-- Create a schema

CREATE TABLE Student
(
  id INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Teacher
(
  id INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Course
(
  id INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE StudentCourses
(
  student_id INT NOT NULL,
  course_id INT NOT NULL,
  PRIMARY KEY (student_id, course_id),
  FOREIGN KEY (student_id) REFERENCES Student(id),
  FOREIGN KEY (course_id) REFERENCES Course(id)
);

CREATE TABLE TeacherCourses
(
  teacher_id INT NOT NULL,
  course_id INT NOT NULL,
  PRIMARY KEY (teacher_id, course_id),
  FOREIGN KEY (course_id) REFERENCES Course(id),
  FOREIGN KEY (teacher_id) REFERENCES Teacher(id)
);


-- Fill a data

-- Teacher table
insert into teacher (id, name) values (1, 'Zachary Harris');
insert into teacher (id, name) values (2, 'Ralph Fatkullin');

-- Course table
insert into course (id, name) values (1, 'Math');
insert into course (id, name) values (2, 'Science');
insert into course (id, name) values (3, 'Databases');
insert into course (id, name) values (4, 'English');
insert into course (id, name) values (5, 'Physics');

--