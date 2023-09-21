package Service;

import DAO.StudentDAO;
import Model.Student;

import java.sql.Connection;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public StudentService(Connection conn){
        studentDAO = new StudentDAO(conn);
    }

    public List<Student> getAllStudents(){
        return this.studentDAO.getAllStudents();
    }

    public Student getStudentById(int Id){
        return studentDAO.getStudentById(Id);
    }
    public boolean addStudent(Student student){
        return studentDAO.addStudent(student);
    }

    public boolean deleteStudentById(int Id){
        return studentDAO.deleteStudentById(Id);
    }


}
