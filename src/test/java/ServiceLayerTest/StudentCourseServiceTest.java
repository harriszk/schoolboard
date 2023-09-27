package ServiceLayerTest;

import DAO.StudentCoursesDAO;
import Model.StudentCourses;
import Service.StudentCoursesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class StudentCourseServiceTest {

    StudentCoursesService studentCoursesService;
    StudentCoursesDAO mockStudentCoursesDAO;


    @Before
    public void setUp() {
        mockStudentCoursesDAO = Mockito.mock(StudentCoursesDAO.class);
        studentCoursesService = new StudentCoursesService(mockStudentCoursesDAO);
    }
@Test
    public  void getAllEntries(){
    List<StudentCourses> expected = new ArrayList<>();
    expected.add(new StudentCourses(1,1));
    Mockito.when(mockStudentCoursesDAO.getAllEntries()).thenReturn(expected);
    List<StudentCourses> actual = studentCoursesService.getAllEntries();
    Mockito.verify(mockStudentCoursesDAO,Mockito.times(1)).getAllEntries();
    Assert.assertEquals(expected,actual);
}

}

