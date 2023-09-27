package ServiceLayerTest;

import DAO.CourseDAO;
import DAO.TeacherDAO;
import Model.Course;
import Model.Teacher;
import Service.CourseService;
import Service.TeacherService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import Exception.ItemDoesNotExistException;
import Exception.ItemAlreadyExistsException;

public class TeacherServiceTest {

    TeacherService teacherService;
    TeacherDAO mockTeacherDAO;

    @Before
    public void setUp(){
        mockTeacherDAO = Mockito.mock(TeacherDAO.class);
        teacherService = new TeacherService(mockTeacherDAO);
    }


    /**
     * This test is testing the getAllTeachersEmptyArrayTest() method in a TeacherService class.
     * The method gets empty ArrayList from DAO level.
     *
     * @Test verifies:
     *    ...that method invoked appropriate method in DAO level precisely one time and
     *    transferred an empty ArrayList from DAO level.
     */
    @Test
    public void getAllTeachersEmptyArrayTest(){
        List<Teacher> teachers = new ArrayList<>();
        Mockito.when(mockTeacherDAO.getAllTeachers()).thenReturn(teachers);
        List<Teacher> actual = teacherService.getAllTeachers();
        Mockito.verify(mockTeacherDAO, Mockito.times(1)).getAllTeachers();
        Assert.assertTrue(actual.isEmpty());
    }

    /**
     * This test is testing the getAllTeachersArrayTest() method in a TeacherService class.
     * The method gets instance of a Teacher class from DAO level.
     *
     * @Test verifies:
     *    ...that method invoked appropriate method in DAO level precisely one time and
     *    transferred an instance of a Teacher class from DAO level.
     */
    @Test
    public void getAllTeachersArrayTest(){
        List<Teacher> expected = new ArrayList<>();
        expected.add(new Teacher(1,"John Doe"));
        expected.add(new Teacher(2,"Jane Doe"));

        Mockito.when(mockTeacherDAO.getAllTeachers()).thenReturn(expected);
        List<Teacher> actual = teacherService.getAllTeachers();
        Mockito.verify(mockTeacherDAO, Mockito.times(1)).getAllTeachers();
        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the getTeacherByIdTest() method in a TeacherService class.
     * The method gets instance of a Teacher class from DAO level.
     *
     * @Test verifies:
     *    ...that method invoked appropriate method in DAO level precisely one time and
     *    transferred an instance of a Teacher class from DAO level.
     */
     @Test
    public void getTeacherByIdTest() throws ItemDoesNotExistException {
        Teacher expected = new Teacher(1,"John Doe");
        Mockito.when(mockTeacherDAO.getTeacherById(1)).thenReturn(expected);
        Teacher actual = teacherService.getTeacherById(1);
        Mockito.verify(mockTeacherDAO, Mockito.times(1)).getTeacherById(1);
        Assert.assertEquals(expected,actual);
    }

}
