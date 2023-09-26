package Controller;

import DAO.StudentDAO;
import Model.Course;
import Model.Student;
import Model.Teacher;
import Service.CourseService;
import Service.StudentService;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import Service.TeacherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import static Util.LogUtil.log;

public class Controller {
    private CourseService courseService;
    private StudentService studentService;
    private TeacherService teacherService;

    public Controller(CourseService courseService, StudentService studentService, TeacherService teacherService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    public Javalin getAPI() {
        Javalin app = Javalin.create();

        // Courses endpoints
        app.get("/courses", this::getAllCoursesHandler);
        app.get("/course/{id}", this::getCourseByIdHandler);
        app.post("/course", this::addCourseHandler);
        app.put("/course", this::updateCourseHandler);
        app.delete("/course/{id}", this::deleteCourseHandler);

        app.get("/courses/teacher/{id}", this::getCoursesByTeacherIdHandler);
        app.get("/courses/student/{id}", this::getCoursesByStudentIdHandler);

        // Student endpoints
        app.get("/students", this::getAllStudentsHandler);
        app.get("/student/{id}", this::getStudentByIdHandler);
        app.post("/student", this::addNewStudentHandler);
        app.put("/student", this::updateStudentHandler);
        app.delete("/student/{id}", this::deleteStudentHandler);

        // Teacher endpoints
        app.get("/teachers", this::getAllTeachersHandler);
        app.get("/teachers/{id}", this::getTeacherByIdHandler);
        app.get("/teachers-courses/{name}", this::getCoursesByTeacherHandler);
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

    private void addCourseHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Course course = mapper.readValue(context.body(), Course.class);

        try {
            this.courseService.addCourse(course);
            context.json("Successfully added course!");
        } catch (ItemAlreadyExistsException e) {
            e.printStackTrace();
            context.json("Cannot add course!");
            context.status(400);
        }
    }

    private void updateCourseHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Course course = mapper.readValue(context.body(), Course.class);

        try {
            this.courseService.updateCourse(course.getId(), course.getName(), course.getTeacherId());
            context.json("Successfully updated course!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json("This student doesn't exist!");
            context.status(400);
        }
    }

    private void deleteCourseHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));

        try {
            this.courseService.deleteCourse(id);
            context.json("Successfully deleted course!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json("ItemDoesNotExistException");
            context.status(400);
        }
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
        Student student = this.studentService.getStudentById(Integer.parseInt(context.pathParam("id")));
        if (student==null){
            context.html("No students with this id");
            context.status(404);
            return;
        }
        context.json(student);
    }

    private void addNewStudentHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Student student = om.readValue(context.body(), Student.class);
        try {
            studentService.addStudent(student);
            context.json("student successfuly added!");
        }catch (ItemAlreadyExistsException e){
            e.printStackTrace();
            context.json("This student already exist!");
            context.status(400);
        }
    }

    private void updateStudentHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Student student = om.readValue(context.body(), Student.class);

        try{
            studentService.updateStudent(student.getId(), student.getName(), student.getEmail());
            context.json("Updated successfuly!");
        }catch (ItemDoesNotExistException e){
            e.printStackTrace();
            context.json("This student doesn't exist!");
            context.status(400);
        }
    }

    private void deleteStudentHandler(Context context) {

    }

    // ========== TEACHERS HANDLERS ==========
    private void getAllTeachersHandler(Context context) {
        List<Teacher> teachers = this.teacherService.getAllTeachers();
        context.json(teachers);
    }

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

    private void addNewTeacherHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Teacher teacher = mapper.readValue(context.body(), Teacher.class);

        try {
            this.teacherService.addTeacher(teacher);
            context.json("Successfully added teacher!");
        } catch (ItemAlreadyExistsException e) {
            e.printStackTrace();
            //context.json("Cannot add teacher!");
            context.json(e.toString());
            context.status(400);
        }
    }

    private void updateTeacherHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Teacher teacher = mapper.readValue(context.body(), Teacher.class);

        try {
            this.teacherService.updateTeacher(teacher.getId(), teacher.getName());
            context.json("Successfully updated teacher!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            //context.json("This teacher doesn't exist!");
            context.json(e.toString());
            context.status(400);
        }
    }

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
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}