package DAO;

import Model.Course;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;
import Model.StudentCourses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private Connection conn;
    
    public CourseDAO(Connection conn){
        this.conn = conn;
    }

    public List<Course> getAllCourses(){
        List<Course> courses = new ArrayList<Course>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from course");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                 courses.add(new Course(rs.getInt("id"),rs.getString("name")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return courses;
    }

    public Course getCourseById(int id){
        Course course = new Course();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from course where course.id=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
            } else course = null;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return course;
    }

    public void addCourse(Course course) throws ItemAlreadyExistsException {
        try {
            String sql = "INSERT INTO course (id, name) VALUES (?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, course.getId());
            ps.setString(2, course.getName());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            throw new ItemAlreadyExistsException("Course");
        }
    }

    public void deleteCourse(int id) throws ItemDoesNotExistException {
        try {
            StudentCoursesDAO scDAO = new StudentCoursesDAO(this.conn);
            scDAO.deleteEntriesByCourseId(id);

            String sql = "DELETE FROM course WHERE course.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            if(ps.executeUpdate() == 0) {
                throw new ItemDoesNotExistException("Course");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCourse(int id, String courseName) throws ItemDoesNotExistException {
        try {
            String sql = "UPDATE course SET course.name = ? WHERE course.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, courseName);
            ps.setInt(2, id);

            if(ps.executeUpdate() == 0) {
                throw new ItemDoesNotExistException("Course");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
