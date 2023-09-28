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
  subject VARCHAR(4) NOT NULL,
  number INT NOT NULL,
  title VARCHAR(255) NOT NULL,
  credit_hours DECIMAL(5, 3) NOT NULL,
  teacher_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (teacher_id) REFERENCES Teacher(id) ON DELETE CASCADE
);

CREATE TABLE StudentCourses
(
  student_id INT NOT NULL,
  course_id INT NOT NULL,
  PRIMARY KEY (student_id, course_id),
  FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES Course(id) ON DELETE CASCADE
);

-- Populate tables with sample data

-- Teacher table
INSERT INTO Teacher (id, name) VALUES
(1, 'Zachary Harris'),
(2, 'Ralph Fatkullin'),
(3, 'Walt Whitman');

-- Course table
INSERT INTO Course (id, subject, number, title, credit_hours, teacher_id) VALUES
(1, 'MATH', 15000, 'Number Systems', 4.000, 1),
(2, 'BIOL', 12300, 'Biology 101', 4.000, 1),
(3, 'HIST', 13300, 'World History', 3.000, 2),
(4, 'ENG', 20200, 'Literary Interpretation', 3.000, 3),
(5, 'ENG', 20400, 'Introduction to Fiction', 3.000, 3);

-- Student table
INSERT INTO Student (id, name, email) VALUES 
(1, 'John Doe', 'johnD@someCompany.com'),
(2, 'Jane Doe', 'janeD@someCompany.com'),
(3, 'Daisy Moyer', 'DaisyMoyer@CrystalEngineer.com');

-- StudentCourses table
INSERT INTO StudentCourses (student_id, course_id) VALUES 
(1, 1),
(1, 2),
(1, 5),
(2, 2),
(2, 3),
(2, 4),
(3, 1),
(3, 3);