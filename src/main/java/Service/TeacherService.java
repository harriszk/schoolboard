package Service;

import DAO.TeacherDAO;

import java.sql.Connection;

public class TeacherService {
    private TeacherDAO teacherDAO;

    public TeacherService(Connection conn) {
        this.teacherDAO = new TeacherDAO(conn);
    }

    public TeacherService(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }
}
