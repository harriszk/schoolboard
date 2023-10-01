package Service;

import DAO.StudentDAO;
import Model.Student;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import java.sql.Connection;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;

    /**
     * 
     * @param studentDAO
     */
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    /**
     * 
     * @param conn
     */
    public StudentService(Connection conn){
        studentDAO = new StudentDAO(conn);
    }

    /**
     * 
     * @return
     */
    public List<Student> getAllStudents(){
        return this.studentDAO.getAllStudents();
    }

    /**
     * 
     * @param studentId
     * @return
     */
    public Student getStudentById(int studentId){
        return studentDAO.getStudentById(studentId);
    }

    /**
     * 
     * @param student
     * @throws ItemAlreadyExistsException
     */
    public void addStudent(Student student) throws ItemAlreadyExistsException {
        studentDAO.addStudent(student);
    }

    /**
     * 
     * @param studentId
     * @throws ItemDoesNotExistException
     */
    public void deleteStudentById(int studentId) throws ItemDoesNotExistException {
        studentDAO.deleteStudentById(studentId);
    }

    /**
     * 
     * @param id
     * @param name
     * @param email
     * @throws ItemDoesNotExistException
     */
    public void updateStudent(int id, String name, String email) throws ItemDoesNotExistException{
        studentDAO.updateStudent(id, name, email);
    }
}
