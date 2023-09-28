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
    public Teacher searchTeacherByName(String name) throws ItemDoesNotExistException {
        //get a List of all teachers
        List<Teacher> allTeachers = this.getAllTeachers();
        Teacher teacher = null;


        //Loop over List of allTeachers and check a name
        for (Teacher teacherElement : allTeachers) {
            if (teacherElement.getName().equals(name)) {
                teacher = teacherElement;
            }
        }
        if (teacher == null) {
            String strName = "teacher " + name;
            throw new ItemDoesNotExistException(strName);
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

    public List<Course> coursesByTeacher(Teacher teacher) throws ItemDoesNotExistException{
        List<Course> courses = new ArrayList<>();
        log.info("*************************************");
        try {
            PreparedStatement ps = conn.prepareStatement("select * from course where course.teacher_id=?");
            ps.setInt(1,teacher.getId());
            ResultSet rs = ps.executeQuery();

            int id;
            String subject;
            int number;
            String title;
            double creditHours;
            int teacherId;

            while (rs.next()){
                id = rs.getInt("id");
                subject = rs.getString("subject");
                number = rs.getInt("number");
                title = rs.getString("title");
                creditHours = rs.getDouble("credit_hours");
                teacherId = rs.getInt("teacher_id");
                Course tempCourse = new Course(id, subject, number, title, creditHours, teacherId);

                courses.add(tempCourse);
                log.info("courses: {}",courses);
            }
            if (courses.isEmpty()){
                throw new ItemDoesNotExistException("teacher");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return courses;
    }
}
