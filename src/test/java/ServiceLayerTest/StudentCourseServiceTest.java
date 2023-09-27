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

    /**
     * This test is testing the getAllEntries() method in a StudentCoursesService class.
     * The method gets ArrayList from DAO level.
     *
     * @Test verifies:
     *    ...that method invoked appropriate method in DAO level precisely one time and
     *    transferred an ArrayList from DAO level.
     */
    @Test
    public  void getAllEntriesTest(){
    List<StudentCourses> expected = new ArrayList<>();
    expected.add(new StudentCourses(1,1));
    Mockito.when(mockStudentCoursesDAO.getAllEntries()).thenReturn(expected);
    List<StudentCourses> actual = studentCoursesService.getAllEntries();
    Mockito.verify(mockStudentCoursesDAO,Mockito.times(1)).getAllEntries();
    Assert.assertEquals(expected,actual);
}

    /**
     * This test is testing the getAllCoursesByStudentId() method in a StudentCoursesService class.
     * The method gets ArrayList from DAO level.
     *
     * @Test verifies:
     *    ...that method invoked appropriate method in DAO level precisely one time and
     *    transferred an ArrayList from DAO level.
     */
@Test
public void getAllCoursesByStudentIdTest() {
    List<StudentCourses> expected = new ArrayList<>();
    expected.add(new StudentCourses(1,1));
    Mockito.when(mockStudentCoursesDAO.getAllCoursesByStudentId(1)).thenReturn(expected);
    List<StudentCourses> actual = studentCoursesService.getAllCoursesByStudentId(1);
    Mockito.verify(mockStudentCoursesDAO,Mockito.times(1)).getAllCoursesByStudentId(1);
    Assert.assertEquals(expected,actual);
}

}

