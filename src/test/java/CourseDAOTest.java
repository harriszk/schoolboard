import DAO.CourseDAO;
import Model.Course;
import Exception.CourseAlreadyExistsException;
import Exception.CourseDoesNotExistException;
import Util.ConnectionSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CourseDAOTest {
    private CourseDAO courseDAO;

    @Before
    public void setUp() {
        ConnectionSingleton.resetTestDatabase();
        courseDAO = new CourseDAO(ConnectionSingleton.getConnection());
    }

    @Test
    public void getAllCoursesTest() {
        List<Course> expected = new ArrayList<Course>();
        expected.add(new Course(1, "Math"));
        expected.add(new Course(2, "Science"));
        expected.add(new Course(3, "Databases"));
        expected.add(new Course(4, "English"));
        expected.add(new Course(5, "Physics"));

        List<Course> actual = courseDAO.getAllCourses();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCourseByIdSuccessfulTest() {
        int id = 3;
        String courseName = "Databases";
        Course expected = new Course(id, courseName);
        Course actual = courseDAO.getCourseById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCourseByIdUnsuccessfulTest() {
        Course actual = courseDAO.getCourseById(-1);
        Assert.assertNull(actual);
    }

    @Test
    public void addNewCourseTest() throws CourseAlreadyExistsException {
        int id = 6;
        String courseName = "History";
        Course expected = new Course(id, courseName);
        courseDAO.addCourse(expected);

        Course actual = courseDAO.getCourseById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addCourseWithSameIdTest() {
        int id = 1;
        String courseName = "Some Course";
        Course newCourse = new Course(id, courseName);

        Assert.assertThrows(CourseAlreadyExistsException.class, () -> courseDAO.addCourse(newCourse));
    }

    @Test
    public void updateExistingCourseNameTest() throws CourseDoesNotExistException {
        int id = 3;
        String courseName = "New Course Name";
        courseDAO.updateCourse(id, courseName);

        Course expected = new Course(id, courseName);
        Course actual = courseDAO.getCourseById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateNonexistentCourseNameTest() {
        int id = -1;
        String courseName = "New Course Name";

        Assert.assertThrows(CourseDoesNotExistException.class, () -> courseDAO.updateCourse(id, courseName));
    }

    @Test
    public void deleteCourseSuccessfulTest() throws CourseDoesNotExistException {
        int id = 1;

        courseDAO.deleteCourse(id);
        Course actual = courseDAO.getCourseById(id);
        Assert.assertNull(actual);
    }

    @Test
    public void deleteCourseUnsuccessfulTest() {
        int id = -1;

        Assert.assertThrows(CourseDoesNotExistException.class, () -> courseDAO.deleteCourse(id));
    }
}
