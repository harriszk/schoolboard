package DAO;

import Model.Course;
import Model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    Connection conn;
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
        return null;
    }

    public boolean addCourse(Course course){
        return false;
    }

    public boolean deleteCourse(int id){
        return false;
    }

    public boolean updateCourse(int id, String courseName){
        return false;
    }






}
