package DAO;

import Model.Course;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;
import Model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Util.LogUtil.log;

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
                int id = rs.getInt("id");
                String subject = rs.getString("subject");
                int number = rs.getInt("number");
                String title = rs.getString("title");
                double creditHours = rs.getDouble("credit_hours");
                int teacherId = rs.getInt("teacher_id");

                courses.add(new Course(id, subject, number, title, creditHours, teacherId));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public Course getCourseById(int id){
        Course course = new Course();

        try {
            String sql = "select * from course where course.id=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                String subject = rs.getString("subject");
                int number = rs.getInt("number");
                String title = rs.getString("title");
                double creditHours = rs.getDouble("credit_hours");
                int teacherId = rs.getInt("teacher_id");

                course.setId(id);
                course.setSubject(subject);
                course.setNumber(number);
                course.setTitle(title);
                course.setCreditHours(creditHours);
                course.setTeacherId(teacherId);

                log.info("Course: {}", course);
            } else {
                course = null;
            } 
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    public void addCourse(Course course) throws ItemAlreadyExistsException {
        try {
            String sql = "INSERT INTO course (id, subject, number, title, credit_hours, teacher_id) VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, course.getId());
            ps.setString(2, course.getSubject());
            ps.setInt(3,course.getNumber());
            ps.setString(4, course.getTitle());
            ps.setDouble(5, course.getCreditHours());
            ps.setInt(6, course.getTeacherId());

            ps.executeUpdate();
        } catch(SQLException e) {
            // Fix: Could be the entry already exists or something wrong with
            // SQL code. Potentially no teacher id exists in the teacher table!
            e.printStackTrace();
            throw new ItemAlreadyExistsException("Course");
        }
    }

    public void updateCourse(Course course) throws ItemDoesNotExistException {
        try {
            String sql = "UPDATE course SET course.subject = ?, course.number = ?, course.title = ?, course.credit_hours = ?, course.teacher_id = ? WHERE course.id = ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, course.getSubject());
            ps.setInt(2,course.getNumber());
            ps.setString(3, course.getTitle());
            ps.setDouble(4, course.getCreditHours());
            ps.setInt(5,course.getTeacherId());
            ps.setInt(6, course.getId());

            if(ps.executeUpdate() == 0) {
                throw new ItemDoesNotExistException("Course");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(int id) throws ItemDoesNotExistException {
        try {
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
}
