package DAO;

import java.sql.Connection;
import java.util.List;

import Model.StudentCourses;

public class StudentCoursesDAO {
    private Connection conn;

    public StudentCoursesDAO(Connection conn) {
        this.conn = conn;
    }

    public List<StudentCourses> getAllEntries() {
        return null;
    }

    public List<StudentCourses> getAllCoursesByStudentId(int id) {
        return null;
    }

    public List<StudentCourses> getAllStudentsByCourseId(int id) {
        return null;
    }

    public void addNewEntry(StudentCourses entry) {
        
    }

    public void deleteEntry(StudentCourses entry) {
        
    }

    public void deleteEntriesByStudentId(int id) {
        
    }

    public void deleteEntriesByCourseId(int id) {
        
    }
}
