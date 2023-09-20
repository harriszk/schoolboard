import DAO.TeacherDAO;
import Model.Teacher;
import Util.ConnectionSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

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
        //Assert.assertThrows(Exception.class, ()->teacherDAO.getTeacherById(-1));
    }

    @Test
    public void addNewTeacherTest() {

    }

    @Test
    public void addTeacherWithSameIdTest() {

    }

    @Test
    public void updateExistingTeacherNameTest() {

    }

    @Test
    public void updateNonexistentTeacherNameTest() {

    }

    @Test
    public void deleteTeacherSuccessfulTest() {

    }

    @Test
    public void deleteTeacherUnsuccessfulTest() {
        
    }
}
