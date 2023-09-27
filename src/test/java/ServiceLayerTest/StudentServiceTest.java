package ServiceLayerTest;

import DAO.StudentDAO;
import Model.Student;
import Model.Teacher;
import Service.StudentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import Exception.ItemDoesNotExistException;

public class StudentServiceTest {

    StudentService studentService;
    StudentDAO mockStudentDAO;


@Before
public void setUp(){
    mockStudentDAO = Mockito.mock(StudentDAO.class);
    studentService = new StudentService(mockStudentDAO);
}


@Test
    public void getAllStudentsSuccessfulTest(){
        List<Student> expected = new ArrayList<>();
        Mockito.when(mockStudentDAO.getAllStudents()).thenReturn(expected);
        List<Student> actual = studentService.getAllStudents();
        Mockito.verify(mockStudentDAO,Mockito.times(1)).getAllStudents();
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void getStudentByIdTest() throws ItemDoesNotExistException {
        Student expected = new Student(1,"Test Test","Test@Test.com");
        Mockito.when(mockStudentDAO.getStudentById(1)).thenReturn(expected);
        Student actual = studentService.getStudentById(1);
        Mockito.verify(mockStudentDAO,Mockito.times(1)).getStudentById(1);
        Assert.assertEquals(expected,actual);
    }


}
