package ServiceLayerTest;

import DAO.CourseDAO;
import Model.Course;
import Service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class CourseServiceTest {

    CourseService courseService;
    CourseDAO mockCourseDAO;

    @Before
    public void setUp(){
        mockCourseDAO = Mockito.mock(CourseDAO.class);
        courseService = new CourseService(mockCourseDAO);
    }

    /**
     * This test is testing the getAllCourses() method in a CourseService class.
     * The method gets empty ArrayList from DAO level.
     *
     * @Test verifies:
     *    ...that method invoked appropriate method in DAO level precisely one time and
     *    transferred an empty ArrayList from DAO level.
     */
    @Test
    public void getAllCoursesEmptyArrayTest(){
        List<Course> courses = new ArrayList<>();
        Mockito.when(mockCourseDAO.getAllCourses()).thenReturn(courses);
        List<Course> actual = courseService.getAllCourses();
        Mockito.verify(mockCourseDAO, Mockito.times(1)).getAllCourses();
        Assert.assertTrue(actual.isEmpty());
    }

    /**
     * This test is testing the getCourseById() method in a CourseService class.
     * The method gets instance of a Course class from DAO level.
     *
     * @Test verifies:
     *    ...that method invoked appropriate method in DAO level precisely one time and
     *    transferred an instance of a Course class from DAO level.
     */
    @Test
    public void getCourseById() {
        Course expected = new Course(1, "CHEM", 34100, "Organic Chemistry I", 4.000, 1);
        Mockito.when(mockCourseDAO.getCourseById(1)).thenReturn(expected);
        Course actual = courseService.getCourseById(1);
        Mockito.verify(mockCourseDAO,Mockito.times(1)).getCourseById(1);
        Assert.assertEquals(expected,actual);
    }

}
