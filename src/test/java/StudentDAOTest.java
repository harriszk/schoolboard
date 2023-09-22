import DAO.StudentDAO;
import Model.Student;
import Util.ConnectionSingleton;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOTest {
    private StudentDAO studentDAO;

    @Before
    public void setUp() {
        ConnectionSingleton.resetTestDatabase();
        studentDAO = new StudentDAO(ConnectionSingleton.getConnection());
    }

    @Test
    public void getAllStudentsTest(){
        List<Student> expected = new ArrayList<>();

        expected.add(new Student(1, "John Doe", "johnD@someCompany.com"));
        expected.add(new Student(2,"Jane Doe", "janeD@someCompany.com"));
        expected.add(new Student(3, "Daisy Moyer", "DaisyMoyer@CrystalEngineer.com"));

        List<Student> actual = studentDAO.getAllStudents();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getStudentByIdSuccessfulTest(){
        int id = 1;
        Student expected = new Student(1, "John Doe", "johnD@someCompany.com");
        Student actual = studentDAO.getStudentById(id);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getStudentByIdUnsuccessfulTest(){
        int id = -1;
        Student actual = studentDAO.getStudentById(id);

        Assert.assertNull(actual);
    }

    @Test
    public void addNewStudentTest() {

    }

    @Test
    public void addNewStudentWithSameIdlTest() {

    }

    @Test
    public void updateExistingStudentTest() {

    }

    @Test
    public void updateNonexistentStudentTest() {

    }

    @Test
    public void deleteStudentSuccessfulTest() {

    }

    @Test
    public void deleteStudentUnsuccessfulTest() {
        
    }
}
