package ServiceLayerTest;

import Model.Course;
import DAO.CourseDAO;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;
import Service.CourseService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

public class CourseServiceTest {
    CourseService courseService;
    CourseDAO mockCourseDAO;

    /**
     * Set up the test environment before each test case.
     * 
     * It creates a Mockito mock for the `CourseDAO` class and initializes the
     * `courseService` instance with this mock, allowing the testing of the
     * `CourseService` class in isolation from its actual dependencies.
     */
    @Before
    public void setUp() {
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
    public void getAllCoursesEmptyArrayTest() {
        List<Course> courses = new ArrayList<>();
        Mockito.when(mockCourseDAO.getAllCourses()).thenReturn(courses);
        List<Course> actual = courseService.getAllCourses();
        Mockito.verify(mockCourseDAO, Mockito.times(1)).getAllCourses();
        Assert.assertTrue(actual.isEmpty());
    }

    /**
     * Tests the behavior of the getAllCourses() method in the CourseService class.
     * 
     * This test case verifies that the getAllCourses method returns the expected list of courses
     * by using Mockito to mock the behavior of the CourseDAO dependency. It sets up a mock response
     * for getAllCourses, calls the method, and then compares the expected and actual results.
     */
    @Test
    public void getAllCoursesTest() {
        List<Course> expected = new ArrayList<>();
        expected.add(new Course(1, "MATH", 15000, "Number Systems", 4.000, 1));
        expected.add(new Course(2, "BIOL", 12300, "Biology 101", 4.000, 1));

        Mockito.when(this.mockCourseDAO.getAllCourses()).thenReturn(expected);

        List<Course> actual = this.courseService.getAllCourses();

        Mockito.verify(this.mockCourseDAO, times(1)).getAllCourses();

        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the getCourseById() method in a CourseService class.
     * The method gets instance of a Course class from DAO level.
     *
     * @Test verifies:
     *    ...that method invoked appropriate method in DAO level precisely one time and
     *    transferred an instance of a Course class from DAO level.
     *
     * @throws ItemDoesNotExistException
     */
    @Test
    public void getCourseByIdTest() throws ItemDoesNotExistException {
        int courseId = 1;
        int teacherId = 1;
        Course expected = new Course(courseId, "MATH", 15000, "Number Systems", 4.000, teacherId);

        Mockito.when(this.mockCourseDAO.getCourseById(courseId)).thenReturn(expected);

        Course actual = this.courseService.getCourseById(courseId);

        Mockito.verify(this.mockCourseDAO, times(1)).getCourseById(courseId);

        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests the behavior of the getCoursesByTeacherId() method in the CourseService class.
     * 
     * This test case verifies that the getCoursesByTeacherId method returns the expected list of courses
     * when provided with a valid teacher ID. It uses Mockito to mock the behavior of the CourseDAO dependency,
     * sets up a mock response for getCoursesByTeacherId, calls the method, and compares the expected and actual results.
     */
    @Test
    public void getCoursesByTeacherIdTest() {
        int teacherId = 3;
        List<Course> expected = new ArrayList<>();
        expected.add(new Course(4, "ENG", 20200, "Literary Interpretation", 3.0, teacherId));
        expected.add(new Course(5, "ENG", 20400, "Introduction to Fiction", 3.0, teacherId));

        Mockito.when(this.mockCourseDAO.getCoursesByTeacherId(teacherId)).thenReturn(expected);

        List<Course> actual = this.courseService.getCoursesByTeacherId(teacherId);

        Mockito.verify(this.mockCourseDAO, times(1)).getCoursesByTeacherId(teacherId);

        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests the behavior of the addCourse() method in the CourseService class.
     * 
     * This test case verifies that the addCourse method successfully adds a new course when provided
     * with valid course data. It uses Mockito to mock the behavior of the CourseDAO dependency, sets up
     * the mock response for addCourse to do nothing, calls the method, and verifies that addCourse is
     * called once with the expected course data.
     * 
     * @throws ItemAlreadyExistsException If the course being added already exists.
     * @throws ItemDoesNotExistException  If any referenced item (e.g., teacherId) does not exist.
     */
    @Test
    public void addCourseTest() throws ItemAlreadyExistsException, ItemDoesNotExistException {
        Course course = new Course(3, "HIST", 30100, "World History", 3.0, 2);

        Mockito.doNothing().when(this.mockCourseDAO).addCourse(course);

        this.courseService.addCourse(course);

        Mockito.verify(this.mockCourseDAO, times(1)).addCourse(course);
    }

    /**
     * Tests the behavior of the deleteCourse() method in the CourseService class.
     * 
     * This test case verifies that the deleteCourse method successfully deletes a course when provided
     * with a valid course ID. It uses Mockito to mock the behavior of the CourseDAO dependency, sets up
     * the mock response for deleteCourse to do nothing, calls the method, and verifies that deleteCourse is
     * called once with the expected course ID.
     * 
     * @throws ItemDoesNotExistException If the course being deleted does not exist.
     */
    @Test
    public void deleteCourseTest() throws ItemDoesNotExistException {
        int courseId = 2;

        Mockito.doNothing().when(this.mockCourseDAO).deleteCourse(courseId);

        this.courseService.deleteCourse(courseId);

        Mockito.verify(this.mockCourseDAO, times(1)).deleteCourse(courseId);
    }

    /**
     * Tests the behavior of the updateCourse() method in the CourseService class.
     * 
     * This test case verifies that the updateCourse method successfully updates a course when provided
     * with valid updated course data. It uses Mockito to mock the behavior of the CourseDAO dependency, sets up
     * the mock response for updateCourse to do nothing, calls the method, and verifies that updateCourse is
     * called once with the expected updated course data.
     * 
     * @throws ItemDoesNotExistException If the course being updated does not exist.
     */
    @Test
    public void updateCourseTest() throws ItemDoesNotExistException {
        Course course = new Course(1, "MATH", 10100, "Advanced Algebra", 4.0, 1);

        Mockito.doNothing().when(this.mockCourseDAO).updateCourse(course);

        this.courseService.updateCourse(course);

        Mockito.verify(this.mockCourseDAO, times(1)).updateCourse(course);
    }
}
