package Controller;

import DAO.StudentDAO;
import Model.Course;
import Model.Student;
import Service.CourseService;
import Service.StudentService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class Controller {
    /*
    // TODO: Need to implement the service layers!
    private CourseService courseService;
    private StudentService studentService;
    private TeacherService teacherService;

    public Controller(CourseService courseService, StudentService studentService, TeacherService teacherService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }
    */

    private CourseService courseService;
    private StudentService studentService;

    public Controller(CourseService courseService, StudentService studentService ) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    public Javalin getAPI() {
        Javalin app = Javalin.create();

        // Courses endpoints
        app.get("/courses", this::getAllCoursesHandler);
        app.get("/courses/{id}", this::getCourseByIdHandler);
        app.post("/courses", this::addCourseHandler);
        app.put("/courses", this::updateCourseHandler);
        app.delete("/courses/{id}", this::deleteCourseHandler);

        app.get("/courses/teacher/{id}", this::getCoursesByTeacherIdHandler);
        app.get("/courses/student/{id}", this::getCoursesByStudentIdHandler);

        // Student endpoints
        app.get("/students", this::getAllStudentsHandler);
        app.get("/students/{id}", this::getStudentByIdHandler);
        app.post("/students", this::addNewStudentHandler);
        app.put("/students", this::updateStudentHandler);
        app.delete("/students/{id}", this::deleteStudentHandler);

        // Teacher endpoints
        app.get("/teachers", this::getAllTeachersHandler);
        app.get("/teachers/{id}", this::getTeacherByIdHandler);
        app.post("/teachers", this::addNewTeacherHandler);
        app.put("/teachers", this::updateTeacherHandler);
        app.delete("/teachers/{id}", this::deleteTeacherHandler);

        return app;
    }

    // ========== COURSES HANDLERS ==========
    private void getAllCoursesHandler(Context context) {
        List<Course> authors = this.courseService.getAllCourses();
        context.json(authors);
    }

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

    private void addCourseHandler(Context context) {

    }

    private void updateCourseHandler(Context context) {

    }

    private void deleteCourseHandler(Context context) {

    }

    private void getCoursesByTeacherIdHandler(Context context) {

    }

    private void getCoursesByStudentIdHandler(Context context) {

    }

    // ========== STUDENTS HANDLERS ==========
    private void getAllStudentsHandler(Context context) {
        List<Student> students = this.studentService.getAllStudents();
        context.json(students);
    }

    private void getStudentByIdHandler(Context context) {
        Student student = this.studentService.getStudentById(
                Integer.parseInt(context.pathParam("id")));
        if (student==null){
            context.html("No students with this id");
            context.status(404);
        }
    }

    private void addNewStudentHandler(Context context) {

    }

    private void updateStudentHandler(Context context) {

    }

    private void deleteStudentHandler(Context context) {

    }

    // ========== TEACHERS HANDLERS ==========
    private void getAllTeachersHandler(Context context) {

    }

    private void getTeacherByIdHandler(Context context) {

    }

    private void addNewTeacherHandler(Context context) {

    }

    private void updateTeacherHandler(Context context) {

    }

    private void deleteTeacherHandler(Context context) {

    }
}