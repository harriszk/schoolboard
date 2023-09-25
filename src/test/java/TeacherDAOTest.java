import Model.Teacher;
import DAO.TeacherDAO;
import Exception.ItemDoesNotExistException;
import Exception.ItemAlreadyExistsException;
import Util.ConnectionSingleton;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TeacherDAOTest {
    private TeacherDAO teacherDAO;

    @Before
    public void setUp() {
        ConnectionSingleton.resetTestDatabase();
        teacherDAO = new TeacherDAO(ConnectionSingleton.getConnection());
    }

    @Test
    public void getAllTeachersTest() {
        List<Teacher> expected = new ArrayList<Teacher>();
        expected.add(new Teacher(1, "Zachary Harris"));
        expected.add(new Teacher(2, "Ralph Fatkullin"));

        List<Teacher> actual = teacherDAO.getAllTeachers();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTeacherByIdSuccessfulTest() {
        int id = 1;
        String name = "Zachary Harris";
        Teacher expected = new Teacher(id, name);
        Teacher actual = teacherDAO.getTeacherById(1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTeacherByIdUnsuccessfulTest() {
        int id = -1;
        Teacher actual = teacherDAO.getTeacherById(id);
        Assert.assertNull(actual);
    }

    @Test
    public void addNewTeacherTest() throws ItemAlreadyExistsException {
        //ConnectionSingleton.resetTestDatabase();
        int id = 3;
        String name = "John Doe";
        Teacher expected = new Teacher(id, name);
        teacherDAO.addTeacher(expected);

        Teacher actual = teacherDAO.getTeacherById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addTeacherWithSameIdTest() {
        int id = 1;
        String name = "John Doe";
        Teacher newTeacher = new Teacher(id, name);
        
        Assert.assertThrows(ItemAlreadyExistsException.class, () -> teacherDAO.addTeacher(newTeacher));
    }

    @Test
    public void updateExistingTeacherNameTest() throws ItemDoesNotExistException {
        int id = 1;
        String name = "New name";
        teacherDAO.updateTeacher(id, name);

        Teacher expected = new Teacher(id, name);
        Teacher actual = teacherDAO.getTeacherById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateNonexistentTeacherNameTest() {
        int id = -1;
        String name = "New name";

        Assert.assertThrows(ItemDoesNotExistException.class, () -> teacherDAO.updateTeacher(id, name));
    }

    @Test
    public void deleteTeacherSuccessfulTest() throws ItemDoesNotExistException {
        int id = 1;

        teacherDAO.deleteTeacher(id);
        Teacher actual = teacherDAO.getTeacherById(id);
        Assert.assertNull(actual);
    }

    @Test
    public void deleteTeacherUnsuccessfulTest() {
        int id = -1;

        Assert.assertThrows(ItemDoesNotExistException.class, () -> teacherDAO.deleteTeacher(id));
    }

}
