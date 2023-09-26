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


    @Test
    public void getAllTeachersEmptyArrayTest(){
        List<Teacher> teachers = new ArrayList<>();
        Mockito.when(mockTeacherDAO.getAllTeachers()).thenReturn(teachers);
        List<Teacher> actual = teacherService.getAllTeachers();
        Mockito.verify(mockTeacherDAO, Mockito.times(1)).getAllTeachers();
        Assert.assertTrue(actual.isEmpty());
    }

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

     @Test
    public void getTeacherByIdTest() throws ItemDoesNotExistException {
        Teacher expected = new Teacher(1,"John Doe");
        Mockito.when(mockTeacherDAO.getTeacherById(1)).thenReturn(expected);
        List<Teacher> actual = teacherService.getAllTeachers();
        Mockito.verify(mockTeacherDAO, Mockito.times(1)).getAllTeachers();
    }

    /*
    @Test
    public void addTeacher() {
        try {
            Teacher expected = new Teacher(1,"John Doe");
            Mockito.when(mockTeacherDAO.addTeacher(expected)).thenReturn(Mockito.any());
        }catch (ItemAlreadyExistsException e){
            e.printStackTrace();
        }

    }*/

    public void updateTeacher(){
    }

    public void deleteTeacher(){
    }

    public void searchTeacherByName(){
    }

    public  void coursesByTeacherName(){
    }


    /*  !!!!!!!!!
    @Test
    public void getAllTeachersProvideExceptionTest(){
        List<Teacher> teachers = new ArrayList<>();
        ItemDoesNotExistException e = new ItemDoesNotExistException("teacher");
        Mockito.when(mockTeacherDAO.getAllTeachers()).thenThrow(e);
        List<Teacher> actual = teacherService.getAllTeachers();
        Mockito.verify(mockTeacherDAO, Mockito.times(1)).getAllTeachers();
        Assert.assertTrue(actual.isEmpty());
    }
*/




}
