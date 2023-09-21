package Service;

import DAO.StudentDAO;

import java.sql.Connection;

public class StudentService {
    private StudentDAO studentDAO;

    public StudentService(Connection conn) {
        this.studentDAO = new StudentDAO(conn);
    }

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }
}
