package ServiceLayerTest;

import DAO.CourseDAO;
import Service.CourseService;
import org.junit.Before;
import org.mockito.Mockito;

public class CourseServiceTest {

    CourseService courseService;
    CourseDAO mockCourseDAO;

    @Before
    public void setUp(){
        mockCourseDAO = Mockito.mock(CourseDAO.class);
        courseService = new CourseService(mockCourseDAO);
    }



}
