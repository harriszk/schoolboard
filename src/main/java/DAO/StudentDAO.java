package DAO;

import Model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    Connection conn;
    public StudentDAO(Connection conn){
        this.conn = conn;
    }

    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("select * from student");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                students.add(new Student(rs.getInt("id"), rs.getString("name"),rs.getString("email") ));
            }


        }catch(SQLException e){
             e.printStackTrace();
        }
        return students;
    }

    public Student getStudentById(int id){
        Student student = null;

        try{
            PreparedStatement ps = conn.prepareStatement("select * from student where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                student = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return student;
    }

    public boolean addStudent(Student student){
        boolean result=false;

        try {
            PreparedStatement ps = conn.prepareStatement("insert into student (id, name, email) values (?,?,?)");
            ps.setInt(1,student.getId());
            ps.setString(2,student.getName());
            ps.setString(3, student.getEmail());

            int rs = ps.executeUpdate();
            //if insert was successful result=true
            if(rs!=0) result=true;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteStudentById(int id){
        boolean result = false;
        try {
            PreparedStatement ps = conn.prepareStatement("delete from student where id=?");
            ps.setInt(1,id);
            int rs = ps.executeUpdate();

            //if delete succeded
            if(rs!=0)  result=true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }


}
