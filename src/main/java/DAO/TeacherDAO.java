package DAO;

import Model.Course;
import Model.Teacher;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import static Util.LogUtil.log;

public class TeacherDAO {
    private Connection conn;

    public TeacherDAO(Connection conn) {
        this.conn = conn;
    }

    //Make a search a teachers by a name and return Arraylist
    public Teacher searchTeacherByName(String name) {
        //get a List of all teachers
        List<Teacher> allTeachers = this.getAllTeachers();
        Teacher teacher = new Teacher();
        //Loop over List of allTeachers and check a name
        for (Teacher teacherElement: allTeachers) {
            if (teacherElement.getName().equals(name)){
                teacher = teacherElement;
            }
        }
        return teacher;
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from teacher");
            ResultSet rs = ps.executeQuery();
            //log.info("ps.executeUpdate()={}",rs);
            while (rs.next()){
                teachers.add(new Teacher(rs.getInt("id"),rs.getString("name") ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return teachers;
    }

    public Teacher getTeacherById(int id) throws ItemDoesNotExistException{
        Teacher teacher = new Teacher();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from teacher where teacher.id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            log.info("rs={}",rs);
            //!!!!!!!!!!!!!!!!!!
            // Take out loop and add ItemDoesntEx exception
            if(rs.next()){
                teacher = new Teacher(rs.getInt("id"), rs.getString("name"));
            }else {
                throw new ItemDoesNotExistException("teacher");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return teacher;
    }

    public void addTeacher(Teacher teacher) throws ItemAlreadyExistsException {
        try {
            log.info("Add teacher:{}",teacher);
            PreparedStatement ps = conn.prepareStatement("insert into teacher (id,name) values (?,?)");
            ps.setInt(1,teacher.getId());
            ps.setString(2,teacher.getName());
            int rs = ps.executeUpdate();
            log.info("ps.executeUpdate()={}",rs);
        }catch (SQLException e){
            e.printStackTrace();
            throw new ItemAlreadyExistsException("teacher");
        }
    }

    public void updateTeacher(int id, String name) throws ItemDoesNotExistException {
        try {
            PreparedStatement ps = conn.prepareStatement("update teacher set name=? where id=?");
            ps.setString(1,name);
            ps.setInt(2,id);
            int rs = ps.executeUpdate();
            log.info("ps.executeupdate()={}",rs);
            if (rs==0)  throw new ItemDoesNotExistException("teacher");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void deleteTeacher(int id) throws ItemDoesNotExistException {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from teacher where id=?");
            ps.setInt(1,id);
            int rs= ps.executeUpdate();
            log.info("rs={}",rs);
            if (rs==0) throw new ItemDoesNotExistException("teacher");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Course> coursesByTeacher(Teacher teacher) {
        List<Course> courses = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from course where course.name=?");
            ps.setString(1,teacher.getName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                courses.add(new Course(rs.getInt("id"), rs.getString("name")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return courses;
    }
}
