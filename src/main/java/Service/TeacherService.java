package Service;

import Model.Teacher;
import DAO.TeacherDAO;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import java.sql.Connection;
import java.util.List;

public class TeacherService {
    private TeacherDAO teacherDAO;

    public TeacherService(Connection conn) {
        this.teacherDAO = new TeacherDAO(conn);
    }

    public TeacherService(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    public List<Teacher> getAllTeachers() {
        return teacherDAO.getAllTeachers();
    }

    public Teacher getTeacherById(int id) throws ItemDoesNotExistException{
        Teacher teacher = new Teacher();
        try {
             teacher = teacherDAO.getTeacherById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return teacher;
    }

    public void addTeacher(Teacher teacher) throws ItemAlreadyExistsException {
        teacherDAO.addTeacher(teacher);
    }

    public void updateTeacher(int id, String name) throws ItemDoesNotExistException {
        teacherDAO.updateTeacher(id, name);
    }

    public void deleteTeacher(int id) throws ItemDoesNotExistException {
        teacherDAO.deleteTeacher(id);
    }
}
