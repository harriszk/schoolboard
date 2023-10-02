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

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParseException;
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

    /**
     * Initializes a new Controller instance.
     * 
     * This constructor creates a new Controller object and initializes various services required for
     * managing courses, students, teachers, and student courses using the provided database connection.
     * 
     * @param conn The database connection used for interacting with the underlying data store.
     */
    public Controller(Connection conn) {
        this.courseService = new CourseService(conn);
        this.studentService = new StudentService(conn);
        this.teacherService = new TeacherService(conn);
        this.studentCoursesService = new StudentCoursesService(conn);
    }

    public Javalin getAPI() {
        Javalin app = Javalin.create();

        // Courses endpoints
        app.get("/course", this::getAllCoursesHandler);
        app.post("/course", this::addCourseHandler);
        app.put("/course", this::updateCourseHandler);
        app.delete("/course/{course_id}", this::deleteCourseHandler);
        app.get("/course/{course_id}", this::getCourseByIdHandler);
        app.get("/course/teacher/{teacher_id}", this::getCoursesByTeacherIdHandler);
        app.get("/course/student/{student_id}", this::getCoursesByStudentIdHandler);
        app.get("/course/student/{student_id}/teacher/{teacher_id}", this::getCoursesByStudentAndTeacherIdHandler);

        // Student endpoints
        app.get("/student", this::getAllStudentsHandler);
        app.get("/student/{student_id}", this::getStudentByIdHandler);
        app.post("/student", this::addNewStudentHandler);
        app.put("/student", this::updateStudentHandler);
        app.delete("/student/{student_id}", this::deleteStudentHandler);
        app.get("/student/course/{course_id}", this::getStudentsByCourseIdHandler);
        app.post("/student/{student_id}/register/{course_id}", this::registerForCourseHandler);
        app.put("/student/{student_id}/unregister/{course_id}", this::unregisterForCourseHandler);

        // Teacher endpoints
        app.get("/teacher", this::getAllTeachersHandler);
        app.get("/teacher/{teacher_id}", this::getTeacherByIdHandler);
        app.get("/teachers-courses/{teacher_name}", this::getCoursesByTeacherHandler);
        app.post("/teacher", this::addNewTeacherHandler);
        app.put("/teacher", this::updateTeacherHandler);
        app.delete("/teacher/{teacher_id}", this::deleteTeacherHandler);

        return app;
    }

    // ==================== COURSES HANDLERS ====================
    /**
     * This handler displays all of courses in the database.
     * 
     * GET /course
     * 
     * @param context
     */
    private void getAllCoursesHandler(Context context) {
        List<Course> courses = this.courseService.getAllCourses();
        context.json(courses);

        /*
        String courseIdString = context.queryParam("courseId");
        String studentIdString = context.queryParam("studentId");
        String teacherIdString = context.queryParam("teacherId");

        if(studentIdString != null && teacherIdString != null) {
            int studentId = Integer.parseInt(studentIdString);
            int teacherId = Integer.parseInt(teacherIdString);

            // TODO: Create a new service layer for doing this!

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
            
        }
        */
    }

    /**
     * This handler displays the information for a specific course in the
     * database that is gotten by its id. If no course with the given id
     * exists then a 404 is returned.
     * 
     * GET /course/{course_id}
     * 
     * @param context
     */
    private void getCourseByIdHandler(Context context) {
        String input = context.pathParam("course_id");

        try {
            int courseId = Integer.parseInt(input);
            Course course = this.courseService.getCourseById(courseId);

            context.json(course);
        } catch (ItemDoesNotExistException e) {
            context.json(e.toString());
            context.status(404);
        } catch (NumberFormatException e) {
            context.html("Invalid input: '" + input + "' is not an integer!");
            context.status(400);
        }
    }

    /**
     * This handler adds a new course to the database, given that it is a course with a
     * new unique id.
     * 
     * POST /course
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void addCourseHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            Course course = mapper.readValue(context.body(), Course.class);

            this.courseService.addCourse(course);
            context.json("Successfully added course!");
        } catch(JsonParseException | JsonMappingException e) {
            context.json("Invalid JSON data in the request body.");
            context.status(400);
        } catch(ItemAlreadyExistsException e) {
            context.json(e.toString());
            context.status(400);
        } catch(ItemDoesNotExistException e) {
            context.json(e.toString());
            context.status(404);
        }
    }

    /**
     * This handler updates an existing course in the database. The given id for the course to be
     * updated needs to be a valid one that is already in the database, else a 400 is returned and
     * nothing is updated. The SQL request by default won't do anything and all is fine but we
     * show a 400 just to indicate that the attempt was a "failure".
     * 
     * PUT /course
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void updateCourseHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Course course = mapper.readValue(context.body(), Course.class);

            this.courseService.updateCourse(course);
            context.json("Successfully updated course!");
        } catch (JsonParseException | JsonMappingException e) {
            context.json("Invalid JSON data in the request body.");
            context.status(400);
        } catch(ItemDoesNotExistException e) {
            context.json(e.toString());
            context.status(404);
        }
    }

    /**
     * This handler deletes an existing course from the database. The given id for the course to be
     * updated needs to be a valid one that is already in the database, else a 400 is returned and
     * nothing is deleted. The SQL request by default won't do anything and all is fine but we
     * show a 400 just to indicate that the attempt was a "failure".
     * 
     * DELETE /course/{course_id}
     * 
     * @param context
     */
    private void deleteCourseHandler(Context context) {
        String input = context.pathParam("course_id");

        try {
            int courseId = Integer.parseInt(input);

            this.courseService.deleteCourse(courseId);
            context.json("Successfully deleted course!");
        } catch(ItemDoesNotExistException e) {
            context.json(e.toString());
            context.status(404);
        } catch (NumberFormatException e) {
            context.html("Invalid input: '" + input + "' is not an integer!");
            context.status(400);
        }
    }

    /**
     * This handler gets all of the courses that a teacher is teaching.
     * The path parameter is the teacher's id.
     * 
     * GET /course/teacher/{teacher_id}
     * 
     * @param context
     */
    private void getCoursesByTeacherIdHandler(Context context) {
        String input = context.pathParam("teacher_id");

        try {
            int teacherId = Integer.parseInt(input);
            List<Course> courses = this.courseService.getCoursesByTeacherId(teacherId);

            context.json(courses);
        } catch (NumberFormatException e) {
            context.html("Invalid input: '" + input + "' is not an integer!");
            context.status(400);
        }
    }

    /**
     * This handler gets all of the courses that a student is taking.
     * The path parameter is the student's id.
     * 
     * GET /course/student/{student_id}
     * 
     * @param context
     */
    private void getCoursesByStudentIdHandler(Context context) throws ItemDoesNotExistException {
        String input = context.pathParam("student_id");

        try {
            int studentId = Integer.parseInt(input);

            // TODO: Create a new service layer for doing this logic!
            // We only want something like 'this.service.getAllCoursesByStudentId(studentId)' here

            List<StudentCourses> entries = this.studentCoursesService.getAllCoursesByStudentId(studentId);
            List<Course> courses = new ArrayList<Course>();

            for(StudentCourses entry : entries) {
                courses.add(this.courseService.getCourseById(entry.getCourseId()));
            }

            context.json(courses);
        } catch (NumberFormatException e) {
            context.html("Invalid input: '" + input + "' is not an integer!");
            context.status(400);
        }
    }

    /**
     * This handler gets all of the courses that a specific teacher teaches given a student id.
     * That is, a student might want to know the other courses they are taking by a specific teacher.
     * 
     * GET /course/student/{student_id}/teacher/{teacher_id}
     * GET /course/student/{student_id}?teacher={teacher_id}
     * 
     * @param context
     */
    private void getCoursesByStudentAndTeacherIdHandler(Context context) throws ItemDoesNotExistException {
        String studentIdInput = context.pathParam("student_id");
        String teacherIdInput = context.pathParam("teacher_id");

        try {
            int studentId = Integer.parseInt(studentIdInput);
            int teacherId = Integer.parseInt(teacherIdInput);

            // TODO: Create a new service layer for doing this!
            // Might even just want to  combine this with the above
            // method and use query params!

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
        } catch (NumberFormatException e) {
            context.html("Invalid input: '" + studentIdInput + "' or '" + teacherIdInput + "' is not an integer!");
            context.status(400);
        }
    }

    // ==================== STUDENTS HANDLERS ====================
    /**
     * This handler displays all of the students in the database.
     * 
     * GET /student
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
     * GET /student/{student_id}
     * 
     * @param context
     */
    private void getStudentByIdHandler(Context context) {
        String input = context.pathParam("student_id");

        try {
            int studentId = Integer.parseInt(input);

            Student student = this.studentService.getStudentById(studentId);

            if(student==null) {
                context.html("No students with this id");
                context.status(404);
                return;
            }

            context.json(student);
        } catch (NumberFormatException e) {
            context.html("Invalid input: '" + input + "' is not an integer!");
            context.status(400);
        }
    }

    /**
     * This handler adds a new student to the database, given that it is a student with a
     * new unique id.
     * 
     * POST /student
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void addNewStudentHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Student student = mapper.readValue(context.body(), Student.class);

            studentService.addStudent(student);
            context.json("Student successfully added!");
        } catch (JsonParseException | JsonMappingException e) {
            context.json("Invalid JSON data in the request body.");
            context.status(400);
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
     * PUT /student
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void updateStudentHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            Student student = mapper.readValue(context.body(), Student.class);

            studentService.updateStudent(student.getId(), student.getName(), student.getEmail());
            context.json("Updated successfully!");
        } catch (JsonParseException | JsonMappingException e) {
            context.json("Invalid JSON data in the request body.");
            context.status(400);
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
     * Endpoint: DELETE student/{student_id}
     * 
     * @param context The context object containing the HTTP request and response information.
     */
    private void deleteStudentHandler(Context context) {
        String input = context.pathParam("student_id");

        try {
            int studentId = Integer.parseInt(input);

            this.studentService.deleteStudentById(studentId);
            context.json("Successfully deleted student!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json(e.toString());
            context.status(400);
        } catch (NumberFormatException e) {
            context.html("Invalid input: '" + input + "' is not an integer!");
            context.status(400);
        }
    }

    /**
     * This handler gets all of the students that are registered for a
     * specific course.
     * 
     * GET /student/course/{course_id}
     * 
     * @param context
     */
    private void getStudentsByCourseIdHandler(Context context) {
        String input = context.pathParam("course_id");

        try {
            int courseId = Integer.parseInt(input);

            // TODO: Create a new service layer for doing this!

            List<StudentCourses> entries = this.studentCoursesService.getAllStudentsByCourseId(courseId);
            List<Student> students = new ArrayList<Student>();

            for(StudentCourses entry : entries) {
                students.add(this.studentService.getStudentById(entry.getStudentId()));
            }

            context.json(students);
        } catch (NumberFormatException e) {
            context.html("Invalid input: '" + input + "' is not an integer!");
            context.status(400);
        }
    }

    /**
     * This handler registers a specific student for a specific course.
     * 
     * POST /student/{student_id}/register/{course_id}
     * 
     * @param context
     */
    private void registerForCourseHandler(Context context) {
        String studentIdInput = context.pathParam("student_id");
        String courseIdInput = context.pathParam("course_id");

        try {
            int studentId = Integer.parseInt(studentIdInput);
            int courseId = Integer.parseInt(courseIdInput);

            this.studentCoursesService.addNewEntry(studentId, courseId);
            context.json("Successfully registered student for the course!");
        } catch(ItemAlreadyExistsException e) {
            e.printStackTrace();
            context.json(e.toString());
        } catch (NumberFormatException e) {
            context.html("Invalid input: '" + studentIdInput + "' or '" + courseIdInput + "' is not an integer!");
            context.status(400);
        }
    }

    /**
     * CHECK
     * This handler unregister's a specific student from a specific course
     * that they are currently registered for.
     * 
     * PUT /student/{student_id}/unregister/{course_id}
     * 
     * @param context
     */
    private void unregisterForCourseHandler(Context context) {
        String studentIdInput = context.pathParam("student_id");
        String courseIdInput = context.pathParam("course_id");

        try {
            int studentId = Integer.parseInt(studentIdInput);
            int courseId = Integer.parseInt(courseIdInput);

            this.studentCoursesService.deleteEntry(studentId, courseId);
            context.json("Successfully unregistered student for the course!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json(e.toString());
        } catch (NumberFormatException e) {
            context.html("Invalid input: '" + studentIdInput + "' or '" + courseIdInput + "' is not an integer!");
            context.status(400);
        }
    }

    // ==================== TEACHERS HANDLERS ====================
    /**
     * This handler displays all of teachers in the database.
     * 
     * GET /teacher
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
     * GET /teacher/{teacher_id}
     * 
     * @param context
     * @throws ItemDoesNotExistException
     */
    private void getTeacherByIdHandler(Context context)   {
        String input = context.pathParam("teacher_id");

        try {
            int teacherId = Integer.parseInt(input);

            Teacher teacher  = this.teacherService.getTeacherById(teacherId);

            context.json(teacher);
        } catch (ItemDoesNotExistException e) {
            context.json(e.toString());
            context.status(404);
        } catch (NumberFormatException e){
            context.html("Invalid input: '" + input + "' is not an integer!");
            context.status(400);
        }
        
    }

    /**
     * This handler adds a new teacher to the database, given that it is a teacher with a
     * new unique id.
     * 
     * POST /teacher
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void addNewTeacherHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Teacher teacher = mapper.readValue(context.body(), Teacher.class);

            this.teacherService.addTeacher(teacher);
            context.json("Successfully added teacher!");
        } catch (JsonParseException | JsonMappingException e) {
            context.json("Invalid JSON data in the request body.");
            context.status(400);
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
     * PUT /teachers
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void updateTeacherHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Teacher teacher = mapper.readValue(context.body(), Teacher.class);

            this.teacherService.updateTeacher(teacher.getId(), teacher.getName());
            context.json("Successfully updated teacher!");
        } catch (JsonParseException | JsonMappingException e) {
            context.json("Invalid JSON data in the request body.");
            context.status(400);
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
     * DELETE /teacher/{teacher_id}
     * 
     * @param context
     */
    private void deleteTeacherHandler(Context context) {
        String input = context.pathParam("teacher_id");
        
        try {
            int teacherId = Integer.parseInt(input);

            this.teacherService.deleteTeacher(teacherId);
            context.json("Successfully deleted teacher!");
        } catch(ItemDoesNotExistException e) {
            e.printStackTrace();
            context.json(e.toString());
            context.status(400);
        } catch (NumberFormatException e) {
            context.html("Invalid input: '" + input + "' is not an integer!");
            context.status(400);
        }
    }

    /**
     * 
     * 
     * GET /teachers-courses/{teacher_name}
     * 
     * @param context
     */
    private void getCoursesByTeacherHandler(Context context){
        String teacherName = context.pathParam("teacher_name");
        log.info("teacherName:{}",teacherName);

        try {
            List<Course> courses = this.teacherService.coursesByTeacherName(teacherName);
            context.json(courses);
        } catch(ItemDoesNotExistException e) {
            context.json(e.toString());
            e.printStackTrace();
        }
    }
}