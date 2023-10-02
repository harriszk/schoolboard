package DAO;

import Model.Student;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection conn;
    
    /**
     * 
     * @param conn
     */
    public StudentDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * 
     * @return
     */
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

    /**
     * 
     * @param studentId
     * @return
     */
    public Student getStudentById(int studentId){
        Student student = null;

        try{
            PreparedStatement ps = conn.prepareStatement("select * from student where id=?");
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                student = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;


    }

    /**
     * 
     * @param student
     * @throws ItemAlreadyExistsException
     */
    public void addStudent(Student student) throws ItemAlreadyExistsException {
        try {
            PreparedStatement ps = conn.prepareStatement("insert into student (id, name, email) values (?,?,?)");
            ps.setInt(1,student.getId());
            ps.setString(2,student.getName());
            ps.setString(3, student.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ItemAlreadyExistsException("Student");
        }
    }

    /**
     * 
     * @param id
     * @param name
     * @param email
     * @throws ItemDoesNotExistException
     */
    public void updateStudent(int id, String name, String email) throws ItemDoesNotExistException {

        try {
            PreparedStatement ps = conn.prepareStatement("update student set student.name= ?, student.email=? where student.id=?");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, id);

            if (ps.executeUpdate() == 0){
                throw new ItemDoesNotExistException("Student");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * 
     * @param studentId
     * @throws ItemDoesNotExistException
     */
    public void deleteStudentById(int studentId) throws ItemDoesNotExistException {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from student where id=?");
            ps.setInt(1,studentId);
            int rs = ps.executeUpdate();

            //if delete succeded
            if (rs == 0) {
                throw new ItemDoesNotExistException("Student");
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
