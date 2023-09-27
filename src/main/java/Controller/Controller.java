package Controller;

import Model.Course;
import Model.Student;
import Model.StudentCourses;
import Model.Teacher;
import Service.CourseService;
import Service.StudentCoursesService;
import Service.StudentService;
import Service.TeacherService;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static Util.LogUtil.log;

public class Controller {
    private CourseService courseService;
    private StudentService studentService;
    private StudentCoursesService studentCoursesService;
    private TeacherService teacherService;

    public Controller(Connection conn) {
        this.courseService = new CourseService(conn);
        this.studentService = new StudentService(conn);
        this.teacherService = new TeacherService(conn);
        this.studentCoursesService = new StudentCoursesService(conn);
    }

    public Javalin getAPI() {
        Javalin app = Javalin.create();

        // Courses endpoints
        app.get("/courses", this::getAllCoursesHandler);
        app.post("/course", this::addCourseHandler);
        app.put("/course", this::updateCourseHandler);
        app.delete("/course/{id}", this::deleteCourseHandler);

        app.get("/course/{id}", this::getCourseByIdHandler);
        app.get("/courses/teacher/{id}", this::getCoursesByTeacherIdHandler);
        app.get("/courses/student/{id}", this::getCoursesByStudentIdHandler);
        app.get("/courses/student/{student_id}/teacher/{teacher_id}", this::getCoursesByStudentAndTeacherIdHandler);

        // Student endpoints
        app.get("/students", this::getAllStudentsHandler);
        app.get("/student/{id}", this::getStudentByIdHandler);
        app.post("/student", this::addNewStudentHandler);
        app.put("/student", this::updateStudentHandler);
        app.delete("/student/{id}", this::deleteStudentHandler);

        app.get("/students/course/{id}", this::getStudentsByCourseIdHandler);
        app.post("/students/{student_id}/register/{course_id}", this::registerForCourseHandler);
        app.put("/students/{student_id}/unregister/{course_id}", this::unregisterForCourseHandler);

        // Teacher endpoints
        app.get("/teachers", this::getAllTeachersHandler);
        app.get("/teachers/{id}", this::getTeacherByIdHandler);
        app.get("/teachers-courses/{name}", this::getCoursesByTeacherHandler);
        app.post("/teachers", this::addNewTeacherHandler);
        app.put("/teachers", this::updateTeacherHandler);
        app.delete("/teachers/{id}", this::deleteTeacherHandler);

        return app;
    }

    // ==================== COURSES HANDLERS ====================
    /**
     * This handler displays all of courses in the database.
     * 
     * GET -> "/course"
     * 
     * @param context
     */
    private void getAllCoursesHandler(Context context) {
        String courseIdString = context.queryParam("courseId");
        String studentIdString = context.queryParam("studentId");
        String teacherIdString = context.queryParam("teacherId");

        if(studentIdString != null && teacherIdString != null) {
            int studentId = Integer.parseInt(studentIdString);
            int teacherId = Integer.parseInt(teacherIdString);

            List<StudentCourses> entries = this.studentCoursesService.getAllCoursesByStudentId(studentId);
            List<Course> courses = new ArrayList<Course>();

            Course tempCourse;

            for(StudentCourses entry : entries) {
                tempCourse = this.courseService.getCourseById(entry.getCourseId());

                if(tempCourse.getTeacherId() == teacherId) {
                    courses.add(tempCourse);
                }
            }

            context.json(courses);
        } else {
            List<Course> courses = this.courseService.getAllCourses();
            context.json(courses);
        }
    }

    /**
     * This handler displays the information for a specific course in the
     * database that is gotten by its id. If no course with the given id
     * exists then a 404 is returned.
     * 
     * GET -> "/course/{id}"
     * 
     * @param context
     */
    private void getCourseByIdHandler(Context context) {
        Course course = this.courseService.getCourseById(
                Integer.parseInt(context.pathParam("id")));

        if(course == null) {
            context.html("No course with that id!");
            context.status(404);
            return;
        }

        context.json(course);
    }

    /**
     * This handler adds a new course to the database, given that it is a course with a
     * new unique id.
     * 
     * POST -> "/course"
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void addCourseHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Course course = mapper.readValue(context.body(), Course.class);

        try {
            this.courseService.addCourse(course);
            context.json("Successfully added course!");
        } catch(ItemAlreadyExistsException e) {
            e.printStackTrace();
            context.json(e.toString());
            context.status(400);
        }
    }

    /**
     * This handler updates an existing course in the database. The given id for the course to be
     * updated needs to be a valid one that is already in the database, else a 400 is returned and
     * nothing is updated. The SQL request by default won't do anything and all is fine but we
     * show a 400 just to indicate that the attempt was a "failure".
     * 
     * PUT -> "/course"
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void updateCourseHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Course course = mapper.readValue(context.body(), Course.class);

        try {
            this.courseService.updateCourse(course);
            context.json("Successfully updated course!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json(e.toString());
            context.status(400);
        }
    }

    /**
     * This handler deletes an existing course from the database. The given id for the course to be
     * updated needs to be a valid one that is already in the database, else a 400 is returned and
     * nothing is deleted. The SQL request by default won't do anything and all is fine but we
     * show a 400 just to indicate that the attempt was a "failure".
     * 
     * DELETE -> "/course/{id}"
     * 
     * @param context
     */
    private void deleteCourseHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));

        try {
            this.courseService.deleteCourse(id);
            context.json("Successfully deleted course!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json(e.toString());
            context.status(400);
        }
    }

    /**
     * CHECK
     * This handler gets all of the courses that a teacher is teaching.
     * The path parameter is the teacher's id.
     * 
     * GET -> "/courses/teacher/{id}"
     * 
     * @param context
     */
    private void getCoursesByTeacherIdHandler(Context context) {
        int teacherId = Integer.parseInt(context.pathParam("id"));
        List<Course> courses = this.courseService.getCoursesByTeacherId(teacherId);

        /*
        for(Course course : courses) {
            if(course.getTeacherId() != teacherId) {
                courses.remove(course);
            }
        }
        */

        context.json(courses);
    }

    /**
     * This handler gets all of the courses that a student is taking.
     * The path parameter is the student's id.
     * 
     * GET -> "/courses/student/{id}"
     * 
     * @param context
     */
    private void getCoursesByStudentIdHandler(Context context) {
        int studentId = Integer.parseInt(context.pathParam("id"));

        List<StudentCourses> entries = this.studentCoursesService.getAllCoursesByStudentId(studentId);
        List<Course> courses = new ArrayList<Course>();

        for(StudentCourses entry : entries) {
            courses.add(this.courseService.getCourseById(entry.getCourseId()));
        }

        context.json(courses);
    }

    /**
     * CHECK
     * This handler gets all of the courses that a specific teacher teaches given a student id.
     * That is, a student might want to know the other courses they are taking by a specific teacher.
     * 
     * GET -> "/courses/student/{student_id}/teacher/{teacher_id}"
     * GET -> "/courses?student={student_id}&teacher={teacher_id}"
     * 
     * @param context
     */
    private void getCoursesByStudentAndTeacherIdHandler(Context context) {
        int studentId = Integer.parseInt(context.pathParam("student_id"));
        int teacherId = Integer.parseInt(context.pathParam("teacher_id"));

        List<StudentCourses> entries = this.studentCoursesService.getAllCoursesByStudentId(studentId);
        List<Course> courses = new ArrayList<Course>();

        Course tempCourse;

        for(StudentCourses entry : entries) {
            tempCourse = this.courseService.getCourseById(entry.getCourseId());

            if(tempCourse.getTeacherId() == teacherId) {
                courses.add(tempCourse);
            }
        }

        context.json(courses);
    }

    // ==================== STUDENTS HANDLERS ====================
    /**
     * This handler displays all of the students in the database.
     * 
     * GET -> "/student"
     * 
     * @param context
     */
    private void getAllStudentsHandler(Context context) {
        List<Student> students = this.studentService.getAllStudents();
        context.json(students);
    }

    /**
     * This handler displays the information for a specific student in the
     * database that is gotten by its id. If no student with the given id
     * exists then a 404 is returned.
     * 
     * GET -> "/student/{id}"
     * 
     * @param context
     */
    private void getStudentByIdHandler(Context context) {
        Student student = this.studentService.getStudentById(Integer.parseInt(context.pathParam("id")));

        if(student==null) {
            context.html("No students with this id");
            context.status(404);
            return;
        }


        context.json(student);
    }

    /**
     * This handler adds a new student to the database, given that it is a student with a
     * new unique id.
     * 
     * POST -> "/student"
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void addNewStudentHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Student student = om.readValue(context.body(), Student.class);


        try {
            studentService.addStudent(student);
            context.json("Student successfully added!");
        } catch(ItemAlreadyExistsException e) {
            e.printStackTrace();
            context.json(e.toString());
            context.status(400);
        }
    }

    /**
     * This handler updates an existing student in the database. The given id for the student to be
     * updated needs to be a valid one that is already in the database, else a 400 is returned and
     * nothing is updated. The SQL request by default won't do anything and all is fine but we
     * show a 400 just to indicate that the attempt was a "failure".
     * 
     * PUT -> "/student"
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void updateStudentHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Student student = om.readValue(context.body(), Student.class);

        try {
            studentService.updateStudent(student.getId(), student.getName(), student.getEmail());
            context.json("Updated successfully!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json(e.toString());
            context.status(400);
        }
    }

    /**
     * This handler deletes an existing student from the database. The given id for the student to be
     * updated needs to be a valid one that is already in the database, else a 400 is returned and
     * nothing is deleted. The SQL request by default won't do anything and all is fine but we
     * show a 400 just to indicate that the attempt was a "failure".
     * 
     * DELETE -> "student/{id}"
     * 
     * @param context
     */
    private void deleteStudentHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));

        try {
            this.studentService.deleteStudentById(id);
            context.json("Successfully deleted student!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json(e.toString());
            context.status(400);
        }
    }

    /**
     * This handler gets all of the students that are registered for a
     * specific course.
     * 
     * GET -> "/students/course/{id}"
     * 
     * @param context
     */
    private void getStudentsByCourseIdHandler(Context context) {
        int courseId = Integer.parseInt(context.pathParam("id"));

        List<StudentCourses> entries = this.studentCoursesService.getAllStudentsByCourseId(courseId);
        List<Student> students = new ArrayList<Student>();

        for(StudentCourses entry : entries) {
            students.add(this.studentService.getStudentById(entry.getStudentId()));
        }

        context.json(students);
    }

    /**
     * This handler registers a specific student for a specific course.
     * 
     * POST -> "/students/{student_id}/register/{course_id}"
     * 
     * @param context
     */
    private void registerForCourseHandler(Context context) {
        int studentId = Integer.parseInt(context.pathParam("student_id"));
        int courseId = Integer.parseInt(context.pathParam("course_id"));

        try {
            this.studentCoursesService.addNewEntry(studentId, courseId);
            context.json("Successfully registered student for the course!");
        } catch(ItemAlreadyExistsException e) {
            e.printStackTrace();
            context.json(e.toString());
        }
    }

    /**
     * CHECK
     * This handler unregister's a specific student from a specific course
     * that they are currently registered for.
     * 
     * PUT -> "/students/{student_id}/unregister/{course_id}"
     * 
     * @param context
     */
    private void unregisterForCourseHandler(Context context) {
        int studentId = Integer.parseInt(context.pathParam("student_id"));
        int courseId = Integer.parseInt(context.pathParam("course_id"));

        try {
            this.studentCoursesService.deleteEntry(studentId, courseId);
            context.json("Successfully unregistered student for the course!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json(e.toString());
        }
    }

    // ==================== TEACHERS HANDLERS ====================
    /**
     * This handler displays all of teachers in the database.
     * 
     * GET -> "/teachers"
     * 
     * @param context
     */
    private void getAllTeachersHandler(Context context) {
        List<Teacher> teachers = this.teacherService.getAllTeachers();
        context.json(teachers);
    }

    /**
     * This handler displays the information for a specific teacher in the
     * database that is gotten by its id. If no teacher with the given id
     * exists then a 404 is returned.
     * 
     * GET -> "/teachers/{id}"
     * 
     * @param context
     * @throws ItemDoesNotExistException
     */
    private void getTeacherByIdHandler(Context context) throws ItemDoesNotExistException {
        Teacher teacher  = this.teacherService.getTeacherById(
                Integer.parseInt(context.pathParam("id")));

        if(teacher == null) {
            context.html("No teacher with that id!");
            context.status(404);
            return;
        }

        context.json(teacher);
    }

    /**
     * This handler adds a new teacher to the database, given that it is a teacher with a
     * new unique id.
     * 
     * POST -> "/teachers"
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void addNewTeacherHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Teacher teacher = mapper.readValue(context.body(), Teacher.class);

        try {
            this.teacherService.addTeacher(teacher);
            context.json("Successfully added teacher!");
        } catch(ItemAlreadyExistsException e) {
            e.printStackTrace();
            context.json(e.toString());
            context.status(400);
        }
    }

    /**
     * This handler updates an existing teacher in the database. The given id for the teacher to be
     * updated needs to be a valid one that is already in the database, else a 400 is returned and
     * nothing is updated. The SQL request by default won't do anything and all is fine but we
     * show a 400 just to indicate that the attempt was a "failure".
     * 
     * PUT -> "/teachers"
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void updateTeacherHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Teacher teacher = mapper.readValue(context.body(), Teacher.class);

        try {
            this.teacherService.updateTeacher(teacher.getId(), teacher.getName());
            context.json("Successfully updated teacher!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json(e.toString());
            context.status(400);
        }
    }

    /**
     * This handler deletes an existing teacher from the database. The given id for the teacher to be
     * updated needs to be a valid one that is already in the database, else a 400 is returned and
     * nothing is deleted. The SQL request by default won't do anything and all is fine but we
     * show a 400 just to indicate that the attempt was a "failure".
     * 
     * DELETE -> "/teachers/{id}"
     * 
     * @param context
     */
    private void deleteTeacherHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));

        try {
            this.teacherService.deleteTeacher(id);
            context.json("Successfully deleted teacher!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json(e.toString());
            context.status(400);
        }
    }

    private void getCoursesByTeacherHandler(Context context){
        String name = context.pathParam("name");
        log.info("name:{}",name);
        try {
            List<Course> courses = this.teacherService.coursesByTeacherName(name);
            context.json(courses);
        }catch(ItemDoesNotExistException e){
            context.json(e.toString());
            e.printStackTrace();
        }
    }
}