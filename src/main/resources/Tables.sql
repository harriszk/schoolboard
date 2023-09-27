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

/*
CONSTRAINT check_length CHECK (your_column_name >= 10000 AND your_column_name <= 99999)
*/

CREATE TABLE Course
(
  id INT NOT NULL,
  subject VARCHAR(4) NOT NULL,
  number INT NOT NULL,
  title VARCHAR(255) NOT NULL,
  credit_hours DECIMAL(5, 3) NOT NULL,
  teacher_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (teacher_id) REFERENCES Teacher(id) ON DELETE CASCADE
);

/*
CREATE TABLE Course
(
  id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  teacher_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (teacher_id) REFERENCES Teacher(id) ON DELETE CASCADE
);
*/

CREATE TABLE StudentCourses
(
  student_id INT NOT NULL,
  course_id INT NOT NULL,
  PRIMARY KEY (student_id, course_id),
  FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES Course(id) ON DELETE CASCADE
);

-- Fill a data

-- Teacher table
insert into teacher (id, name) values (1, 'Zachary Harris');
insert into teacher (id, name) values (2, 'Ralph Fatkullin');
insert into teacher (id, name) values (3, 'Walt Whitman');


-- Course table
INSERT INTO Course (id, subject, number, title, credit_hours, teacher_id) VALUES
(1, 'MATH', 11000, 'Fundamentals of Algebra', 4.000, NULL),
(2, 'MATH', 15100, 'Algebra and Trigonometry', 4.000, NULL),
(3, 'MATH', 15400, 'Trigonometry', 3.000, NULL),
(4, 'MATH', 15900, 'Pre-calculus', 5.000, NULL),
(5, 'MATH', 16500, 'Analytic Geometry and Calculus I', 4.000, NULL),
(6, 'MATH', 16600, 'Analytic Geometry and Calculus II', 4.000, NULL),
(7, 'MATH', 26100, 'Multivariate Calculus', 4.000, NULL),
(8, 'MATH', 26600, 'Ordinary Differential Equations', 3.000, NULL),
(9, 'MATH', 30000, 'Logic and the Foundations of Algebra', 3.000, NULL),
(10, 'MATH', 35100, 'Elementary Linear Algebra', 3.000, NULL),
(11, 'MATH', 41400, 'Numerical Methods', 3.000, NULL),
(12, 'MATH', 44400, 'Real Analysis I', 3.000, NULL),
(13, 'MATH', 44500, 'Real Analysis II', 3.000, NULL),
(14, 'MATH', 45300, 'Elementary Abstract Algebra', 3.000, NULL),
(15, 'MATH', 46200, 'Elementary Differential Geometry', 3.000, NULL),
(16, 'MATH', 52300, 'Introduction to Partial Differential Equations', 3.000, NULL),
(17, 'MATH', 52500, 'Introduction to Complex Analysis', 3.000, NULL);

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