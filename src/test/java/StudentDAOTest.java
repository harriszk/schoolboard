import DAO.StudentDAO;
import Model.Student;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOTest {

    private StudentDAO studentDAO;

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
}
