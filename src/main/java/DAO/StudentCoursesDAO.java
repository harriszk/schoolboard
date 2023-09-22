package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;
import Model.Course;
import Model.StudentCourses;

public class StudentCoursesDAO {
    private Connection conn;

    // TODO: Add comments!
    /**
     * 
     * @param conn
     */
    public StudentCoursesDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * 
     * @return
     */
    public List<StudentCourses> getAllEntries() {
        List<StudentCourses> entries = new ArrayList<StudentCourses>();

        try {
            String sql = "SELECT * FROM StudentCourses;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                int courseId = rs.getInt("course_id");
                StudentCourses entry = new StudentCourses(studentId, courseId);
                entries.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entries;
    }

    /**
     * 
     * @param id
     * @return
     */
    public List<StudentCourses> getAllCoursesByStudentId(int id) {
        List<StudentCourses> entries = new ArrayList<StudentCourses>();

        try {
            String sql = "SELECT * FROM StudentCourses WHERE StudentCourses.student_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                int courseId = rs.getInt("course_id");
                StudentCourses entry = new StudentCourses(studentId, courseId);
                entries.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entries;
    }

    /**
     * 
     * @param id
     * @return
     */
    public List<StudentCourses> getAllStudentsByCourseId(int id) {
        List<StudentCourses> entries = new ArrayList<StudentCourses>();

        try {
            String sql = "SELECT * FROM StudentCourses WHERE StudentCourses.course_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                int courseId = rs.getInt("course_id");
                StudentCourses entry = new StudentCourses(studentId, courseId);
                entries.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entries;
    }

    /**
     * 
     * @param entry
     */
    public void addNewEntry(StudentCourses entry) throws ItemAlreadyExistsException {
        try {
            String sql = "INSERT INTO StudentCourses (student_id, course_id) VALUES (?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entry.getStudentId());
            ps.setInt(2, entry.getCourseId());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            throw new ItemAlreadyExistsException("StudentCourses");
        }
    }

    /**
     * 
     * @param entry
     */
    public void deleteEntry(StudentCourses entry) throws ItemDoesNotExistException {
        try {
            String sql = "DELETE FROM StudentCourses WHERE StudentCourses.student_id = ? AND StudentCourses.course_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entry.getStudentId());
            ps.setInt(2, entry.getCourseId());

            if(ps.executeUpdate() == 0) {
                throw new ItemDoesNotExistException("StudentCourses");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param id
     */
    public void deleteEntriesByStudentId(int id) {
        try {
            String sql = "DELETE FROM StudentCourses WHERE StudentCourses.student_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param id
     */
    public void deleteEntriesByCourseId(int id) {
        try {
            String sql = "DELETE FROM StudentCourses WHERE StudentCourses.course_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
