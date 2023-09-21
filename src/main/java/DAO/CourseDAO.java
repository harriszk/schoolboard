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
            PreparedStatement ps = conn.prepareStatement("insert into course (id, name) values (?,?)");
            ps.setInt(1,course.getId());
            ps.setString(2,course.getName());
            ResultSet rs = ps.executeQuery();

            //Check existence
            ps = conn.prepareStatement("select * from course where course.id=?");
            ps.setInt(1,course.getId());
            rs = ps.executeQuery();

            Course newCourse = new Course();
            while(rs.next()){
                newCourse.setId(rs.getInt("id"));
                newCourse.setName(rs.getString("name"));
            }
            //compare parametr with retrived from DB
            result = course.equals(newCourse);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteCourse(int id){

        boolean result = false;

        try {
            PreparedStatement ps = conn.prepareStatement("delete from course where course.id=?");
            ps.setInt(1,id);
            ps.executeUpdate();


            ps = conn.prepareStatement("select * from course where course.id=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            result =  !rs.next();

            /*while(rs.next()){
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
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
