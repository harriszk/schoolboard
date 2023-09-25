package DaoLayerTest;

import Model.Student;
import DAO.StudentDAO;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;
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
    public void addNewStudentTest() throws ItemAlreadyExistsException {
        int id = 4;
        String name = "Raymond Welsh";
        String email = "rwelsh@email.com";
        Student expected = new Student(id, name, email);
        studentDAO.addStudent(expected);

        Student actual = studentDAO.getStudentById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addNewStudentWithSameIdlTest() {
        int id = 1;
        String name = "Raymond Welsh";
        String email = "rwelsh@email.com";
        Student newStudent = new Student(id, name, email);

        Assert.assertThrows(ItemAlreadyExistsException.class, () -> studentDAO.addStudent(newStudent));
    }

    @Test
    public void updateExistingStudentTest() throws ItemDoesNotExistException {
        int id = 1;
        String name = "Raymond Welsh";
        String email = "rwelsh@email.com";
        studentDAO.updateStudent(id, name, email);

        Student expected = new Student(id, name, email);
        Student actual = studentDAO.getStudentById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateNonexistentStudentTest() {
        int id = -1;
        String name = "Raymond Welsh";
        String email = "rwelsh@email.com";
        
        Assert.assertThrows(ItemDoesNotExistException.class, () -> studentDAO.updateStudent(id, name, email));
    }

    @Test
    public void deleteStudentSuccessfulTest() throws ItemDoesNotExistException {
        int id = 2;

        studentDAO.deleteStudentById(id);
        Student actual = studentDAO.getStudentById(id);
        Assert.assertNull(actual);
    }

    @Test
    public void deleteStudentUnsuccessfulTest() {
        int id = -1;

        Assert.assertThrows(ItemDoesNotExistException.class, () -> studentDAO.deleteStudentById(id));
    }
}
