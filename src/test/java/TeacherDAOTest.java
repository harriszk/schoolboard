import DAO.TeacherDAO;
import Model.Teacher;
import Util.ConnectionSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOTest {
    private TeacherDAO teacherDAO;

    @Before
    public void setUp() {
        Connection conn = ConnectionSingleton.getConnection();
        teacherDAO = new TeacherDAO(conn);
    }

    @Test
    public void getTeacherByIdSuccessfulTest() {
        Teacher expected = new Teacher(1, "Zachary Harris");
        Teacher actual = teacherDAO.getTeacherById(1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTeacherByIdUnsuccessfulTest() {
        Teacher actual = teacherDAO.getTeacherById(-1);
        Assert.assertNull(actual);
    }

    @Test
    public void addNewTeacherTest() {
        int newId = 3;
        Teacher expected = new Teacher(newId, "John Doe");
        teacherDAO.addTeacher(expected);
        Teacher actual = teacherDAO.getTeacherById(newId);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addTeacherWithSameIdTest() {
        int newId = 1;
        Teacher newTeacher = new Teacher(newId, "John Doe");
        boolean expected = false;
        boolean actual = teacherDAO.addTeacher(newTeacher);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateExistingTeacherNameTest() {
        int id = 1;
        String name = "New name";
        Teacher expected = new Teacher(id, name);

        teacherDAO.update(id, name);
        Teacher actual = teacherDAO.getTeacherById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateNonexistentTeacherNameTest() {
        int id = -1;
        String name = "New name";

        boolean expected = false;
        boolean actual = teacherDAO.update(id, name);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteTeacherSuccessfulTest() {
        int id = 1;
        boolean expected = true;
        boolean actual = teacherDAO.remove(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteTeacherUnsuccessfulTest() {
        int id = -1;
        boolean expected = false;
        boolean actual = teacherDAO.remove(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllTeachersTest() {
        List<Teacher> expected = new ArrayList<Teacher>();
        expected.add(new Teacher(1, "Zachary Harris"));
        expected.add(new Teacher(2, "Ralph Fatkullin"));

        List<Teacher> actual = teacherDAO.getAllTeachers();
        Assert.assertEquals(expected, actual);
    }
}
