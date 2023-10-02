package Service;

import Model.Course;
import Model.Teacher;
import DAO.TeacherDAO;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import java.sql.Connection;
import java.util.List;

public class TeacherService {
    private TeacherDAO teacherDAO;

    /**
     * 
     * @param conn
     */
    public TeacherService(Connection conn) {
        this.teacherDAO = new TeacherDAO(conn);
    }

    /**
     * 
     * @param teacherDAO
     */
    public TeacherService(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    /**
     * 
     * @return
     */
    public List<Teacher> getAllTeachers() {
        return teacherDAO.getAllTeachers();
    }

    /**
     * 
     * @param id
     * @return
     * @throws ItemDoesNotExistException
     */
    public Teacher getTeacherById(int id) throws ItemDoesNotExistException{
        return this.teacherDAO.getTeacherById(id);
    }

    /**
     * 
     * @param teacher
     * @throws ItemAlreadyExistsException
     */
    public void addTeacher(Teacher teacher) throws ItemAlreadyExistsException {
        teacherDAO.addTeacher(teacher);
    }

    /**
     * 
     * @param id
     * @param name
     * @throws ItemDoesNotExistException
     */
    public void updateTeacher(int id, String name) throws ItemDoesNotExistException {
        teacherDAO.updateTeacher(id, name);
    }

    /**
     * 
     * @param id
     * @throws ItemDoesNotExistException
     */
    public void deleteTeacher(int id) throws ItemDoesNotExistException {
        teacherDAO.deleteTeacher(id);
    }

    /**
     * 
     * @param name
     * @return
     * @throws ItemDoesNotExistException
     */
    public Teacher searchTeacherByName(String name) throws ItemDoesNotExistException {
        return teacherDAO.searchTeacherByName(name);
    }

    /**
     * 
     * @param name
     * @return
     * @throws ItemDoesNotExistException
     */
    public List<Course> coursesByTeacherName(String name) throws ItemDoesNotExistException {
        Teacher teacher = this.searchTeacherByName(name);
        return teacherDAO.coursesByTeacher(teacher);
    }
}
