package Service;

import Model.Course;
import Model.Student;
import Model.StudentCourses;
import Util.ConnectionSingleton;
import DAO.StudentCoursesDAO;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;

import static Util.LogUtil.log;


public class StudentCoursesService {
    private StudentCoursesDAO studentCoursesDAO;

    /**
     * 
     * @param conn
     */
    public StudentCoursesService(Connection conn) {
        studentCoursesDAO = new StudentCoursesDAO(conn);
    }

    /**
     * 
     * @param studentCoursesDAO
     */
    public StudentCoursesService(StudentCoursesDAO studentCoursesDAO) {
        this.studentCoursesDAO = studentCoursesDAO;
    }

    public List<StudentCourses> getAllEntries() {
        return this.studentCoursesDAO.getAllEntries();
    }

    public List<Course> getAllCoursesByStudentId(int studentId) {
        List<StudentCourses> entries = this.studentCoursesDAO.getAllCoursesByStudentId(studentId);
        List<Course> courses = new ArrayList<Course>();

        // Do we want this in this service file?
        CourseService courseService = new CourseService(ConnectionSingleton.getConnection());

        for(StudentCourses entry : entries) {
            courses.add(courseService.getCourseById(entry.getCourseId()));
        }

        return courses;
    }

    public List<Student> getAllStudentsByCourseId(int courseId) {
        List<StudentCourses> entries = this.studentCoursesDAO.getAllCoursesByStudentId(courseId);
        List<Student> students = new ArrayList<Student>();

        // Do we want this in this service file?
        StudentService studentService = new StudentService(ConnectionSingleton.getConnection());

        for(StudentCourses entry : entries) {
            log.debug(entry.toString());
            //students.add(studentService.getStudentById(entry.getStudentId()));
        }

        return students;
    }

    public void addNewEntry(int studentId, int courseId) throws ItemAlreadyExistsException {
        StudentCourses newEntry = new StudentCourses(studentId, courseId);

        this.studentCoursesDAO.addNewEntry(newEntry);
    }

    public void deleteEntry() throws ItemDoesNotExistException {

    }

    public void deleteEntriesByStudentId() {

    }

    public void deleteEntriesByCourseId() {}
}
