package Service;

import DAO.StudentDAO;
import Model.Student;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

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
    public void addStudent(Student student) throws ItemAlreadyExistsException {
        studentDAO.addStudent(student);
    }

    public void deleteStudentById(int Id) throws ItemDoesNotExistException {
        studentDAO.deleteStudentById(Id);
    }


}
