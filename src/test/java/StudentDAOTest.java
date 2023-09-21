import DAO.CourseDAO;
import DAO.StudentDAO;
import Model.Student;
import Util.ConnectionSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOTest {

    private StudentDAO studentDAO;

    @Before
    public void setUp() {
        Connection conn = ConnectionSingleton.getConnection();
        studentDAO = new StudentDAO(conn);
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
        int id=-1;
        Student expected = null;
        Student actual = studentDAO.getStudentById(id);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addStudentSuccessfulTest(){
        boolean expected = true;
        Student student = new Student(4,"Brian Jones","BrianJones@@CrystalEngineer.com");
        boolean actual = studentDAO.addStudent(student);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addStudentUnsuccessfulTest(){
        boolean expected = false;
        Student student = new Student(1,"Brian Jones","BrianJones@@CrystalEngineer.com");
        boolean actual = studentDAO.addStudent(student);
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void deleteStudentByIdSuccessfulTest(){
        int id =1;
        boolean expected = true;
        boolean actual = studentDAO.deleteStudentById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteStudentByIdUnsuccessfulTest(){
        int id =-1;
        boolean expected = true;
        boolean actual = studentDAO.deleteStudentById(id);
        Assert.assertEquals(expected, actual);
    }

}
