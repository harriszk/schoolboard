package ServiceLayerTest;

import DAO.CourseDAO;
import Model.Course;
import Service.CourseService;
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


    @Test
    public void getAllCoursesEmptyArrayTest(){
        List<Course> courses = new ArrayList<>();
        Mockito.when(mockCourseDAO.getAllCourses()).thenReturn(courses);
        courseService.getAllCourses();
        Mockito.verify(mockCourseDAO, Mockito.times(1)).getAllCourses();
    }
    /*
    *@Test
    public void updateArrivalCitiesMultipleFlightsChangedTest(){
//        arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(new Flight(111, "tampa", "baltimore"));
        flightList.add(new Flight(111, "tampa", "philadelphia"));
        flightList.add(new Flight(111, "tampa", "baltimore"));
        Mockito.when(mockFlightDAO.queryAllFlight()).thenReturn(flightList);
//        act
        flightService.resetArrivalCities("baltimore", "philadelphia");
//        assert
        Mockito.verify(mockFlightDAO, Mockito.times(2)).updateArrivalCity(Mockito.anyInt(), Mockito.any());
    }
    *
    * */



}
