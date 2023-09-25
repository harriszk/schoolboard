package DAO;

import Model.Teacher;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import static Util.LogUtil.log;

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

    }

    public void removeTeacher(int id) throws ItemDoesNotExistException {

    }
}
