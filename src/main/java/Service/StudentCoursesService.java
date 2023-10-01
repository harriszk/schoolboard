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

    /**
     * 
     * @return
     */
    public List<StudentCourses> getAllEntries() {
        return this.studentCoursesDAO.getAllEntries();
    }

    /**
     * 
     * @param studentId
     * @return
     */
    public List<StudentCourses> getAllCoursesByStudentId(int studentId) {
        List<StudentCourses> entries = this.studentCoursesDAO.getAllCoursesByStudentId(studentId);

        return entries;
    }

    /**
     * 
     * @param courseId
     * @return
     */
    public List<StudentCourses> getAllStudentsByCourseId(int courseId) {
        List<StudentCourses> entries = this.studentCoursesDAO.getAllStudentsByCourseId(courseId);

        return entries;
    }

    /**
     * 
     * @param studentId
     * @param courseId
     * @throws ItemAlreadyExistsException
     */
    public void addNewEntry(int studentId, int courseId) throws ItemAlreadyExistsException {
        StudentCourses newEntry = new StudentCourses(studentId, courseId);

        this.studentCoursesDAO.addNewEntry(newEntry);
    }

    /**
     * 
     * @param studentId
     * @param courseId
     * @throws ItemDoesNotExistException
     */
    public void deleteEntry(int studentId, int courseId) throws ItemDoesNotExistException {
        StudentCourses entry = new StudentCourses(studentId, courseId);

        this.studentCoursesDAO.deleteEntry(entry);
    }

    /**
     * 
     * @param studentId
     */
    public void deleteEntriesByStudentId(int studentId) {
        this.studentCoursesDAO.deleteEntriesByStudentId(studentId);
    }

    /**
     * 
     * @param courseId
     */
    public void deleteEntriesByCourseId(int courseId) {
        this.studentCoursesDAO.deleteEntriesByCourseId(courseId);
    }
}
