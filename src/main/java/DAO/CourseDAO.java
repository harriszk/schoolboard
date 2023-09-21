package DAO;

import Model.Course;
import Model.Teacher;

import java.sql.*;
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

    public boolean addCourse(Course course){
        boolean result = false;

        try {
            String sql = "INSERT INTO course (id, name) VALUES (?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,course.getId());
            ps.setString(2,course.getName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            // If there is something in the result set, then we were successfully able
            // to add a course!
            if(rs.next()) {
                result = true;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean deleteCourse(int id){

        boolean result = false;

        try {

            PreparedStatement ps = conn.prepareStatement("select * from course where course.id=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){ //if exist -> delete it
                ps = conn.prepareStatement("delete from course where course.id=?");
                ps.setInt(1,id);
                ps.executeUpdate();

                //Check nonexistence anymore
                ps = conn.prepareStatement("select * from course where course.id=?");
                ps.setInt(1,id);
                rs = ps.executeQuery();
                result =  !rs.next();
            }else {//if course didn't exist
                result = rs.next();
            }






           /* if(rs.next()){
                result =  true;
            }*/

        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateCourse(int id, String courseName){
        boolean result = false;
        try {
            PreparedStatement ps = conn.prepareStatement("update course set course.name=? where course.id=?");
            ps.setString(1,courseName);
            ps.setInt(2,id);
            ps.executeUpdate();

            ps = conn.prepareStatement("select * from course where course.id=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = courseName.equals(rs.getString("name"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }






}
