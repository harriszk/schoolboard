--h2 is typically used to setup a test database, not a prod database.
--first, drop your tables (to reset your database for testing)
--then create your tables

drop table StudentCourses if exists;
drop table Student if exists;
drop table Course if exists;
drop table Teacher if exists;

-- Create a schema
CREATE TABLE Student
(
  id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Teacher
(
  id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Course
(
  id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  teacher_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (teacher_id) REFERENCES Teacher(id)
);

CREATE TABLE StudentCourses
(
  student_id INT NOT NULL,
  course_id INT NOT NULL,
  PRIMARY KEY (student_id, course_id),
  FOREIGN KEY (student_id) REFERENCES Student(id),
  FOREIGN KEY (course_id) REFERENCES Course(id)
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

-- Student table
insert into student (id, name, email) values (1, 'John Doe', 'johnD@someCompany.com');
insert into student (id, name, email) values (2, 'Jane Doe', 'janeD@someCompany.com');
insert into student (id, name, email) values (3, 'Daisy Moyer', 'DaisyMoyer@CrystalEngineer.com');
/*
INSERT INTO Student (id, name, email) values (4, 'Raymond Welsh', 'rwelsh@email.com');
INSERT INTO Student (id, name, email) values (5, 'Maya Lam', 'mlam@email.com');
INSERT INTO Student (id, name, email) values (6, 'Mae Garza', 'mgarza@email.com');
INSERT INTO Student (id, name, email) values (7, 'Eve Henry', 'ehenry@email.com');
INSERT INTO Student (id, name, email) values (8, 'Gracie Webster', 'gwebster@email.com');
INSERT INTO Student (id, name, email) values (9, 'Angus Khan', 'akhan@email.com');
INSERT INTO Student (id, name, email) values (10, 'Rodney Frazier', 'rfrazier@email.com');
INSERT INTO Student (id, name, email) values (11, 'Calum Jensen', 'cjensen@email.com');
INSERT INTO Student (id, name, email) values (12, 'Stephanie Carson', 'scarson@email.com');
INSERT INTO Student (id, name, email) values (13, 'Ricardo George', 'rgeorge@email.com');
INSERT INTO Student (id, name, email) values (14, 'Trey Gregory', 'tgregory@email.com');
INSERT INTO Student (id, name, email) values (15, 'Tommy Barton', 'tbarton@email.com');
*/

-- StudentCourses table
INSERT INTO StudentCourses (student_id, course_id) VALUES (1, 1);
INSERT INTO StudentCourses (student_id, course_id) VALUES (1, 2);
INSERT INTO StudentCourses (student_id, course_id) VALUES (1, 5);
INSERT INTO StudentCourses (student_id, course_id) VALUES (2, 2);
INSERT INTO StudentCourses (student_id, course_id) VALUES (2, 3);
INSERT INTO StudentCourses (student_id, course_id) VALUES (2, 4);
INSERT INTO StudentCourses (student_id, course_id) VALUES (3, 1);
INSERT INTO StudentCourses (student_id, course_id) VALUES (3, 3);