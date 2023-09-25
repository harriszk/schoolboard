package DAO;

import Model.Teacher;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class TeacherDAO {
    private Connection conn;

    public TeacherDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Teacher> getAllTeachers() {
        return null;
    }

    public Teacher getTeacherById(int id) {
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

    public void addTeacher(Teacher teacher) throws ItemAlreadyExistsException {
        try {
            PreparedStatement ps = conn.prepareStatement("insert into teacher (id,name) values (?,?)");
            ps.setInt(1,teacher.getId());
            ps.setString(2,teacher.getName());
            if(ps.executeUpdate()==0){
                throw new ItemAlreadyExistsException("teacher");
            };
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateTeacher(int id, String name) throws ItemDoesNotExistException {

    }

    public void removeTeacher(int id) throws ItemDoesNotExistException {

    }
}
