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
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                student = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return student;


    }


}
