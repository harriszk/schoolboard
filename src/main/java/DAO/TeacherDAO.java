package DAO;

import Model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDAO {
    Connection conn;
    public TeacherDAO(Connection conn){
        this.conn = conn;
    }

    public Teacher getTeacherById(int id){
        try{
            PreparedStatement ps = conn.prepareStatement("select * from teacher where teacher.id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Teacher tmpTeacher = new Teacher(rs.getInt("id"), rs.getString("name"));
                return tmpTeacher;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}