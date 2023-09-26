package DaoLayerTest;

import Model.Course;
import DAO.CourseDAO;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;
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
        expected.add(new Course(1, "Math",2));
        expected.add(new Course(2, "Science",1));
        expected.add(new Course(3, "Databases",1));
        expected.add(new Course(4, "English",3));
        expected.add(new Course(5, "Physics",2));
        expected.add(new Course(6, "History",3));

        List<Course> actual = courseDAO.getAllCourses();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCourseByIdSuccessfulTest() {
        int id = 3;
        String courseName = "Databases";
        int teacherId = 1;
        Course expected = new Course(id, courseName, teacherId);
        System.out.println(expected);
        Course actual = courseDAO.getCourseById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCourseByIdUnsuccessfulTest() {
        Course actual = courseDAO.getCourseById(-1);
        Assert.assertNull(actual);
    }

    @Test
    public void addNewCourseTest() throws ItemAlreadyExistsException {
        int id = 7;
        String courseName = "Literature";
        int teacher_id = 3;
        Course expected = new Course(id, courseName, teacher_id);
        courseDAO.addCourse(expected);

        Course actual = courseDAO.getCourseById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addCourseWithSameIdTest() {
        int id = 1;
        String courseName = "Some Course";
        int teacher_id = 3;
        Course newCourse = new Course(id, courseName, teacher_id);

        Assert.assertThrows(ItemAlreadyExistsException.class, () -> courseDAO.addCourse(newCourse));
    }

    @Test
    public void updateExistingCourseNameTest() throws ItemDoesNotExistException {
        int id = 3;
        String courseName = "New Course Name";
        int teacherId = 3;
        courseDAO.updateCourse(id, courseName, teacherId);

        Course expected = new Course(id, courseName, teacherId);
        Course actual = courseDAO.getCourseById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateNonexistentCourseNameTest() {
        int id = -1;
        String courseName = "New Course Name";
        int teacherId = 3;

        Assert.assertThrows(ItemDoesNotExistException.class, () -> courseDAO.updateCourse(id, courseName, teacherId));
    }

    @Test
    public void deleteCourseSuccessfulTest() throws ItemDoesNotExistException {
        int id = 1;

        courseDAO.deleteCourse(id);
        Course actual = courseDAO.getCourseById(id);
        Assert.assertNull(actual);
    }

    @Test
    public void deleteCourseUnsuccessfulTest() {
        int id = -1;

        Assert.assertThrows(ItemDoesNotExistException.class, () -> courseDAO.deleteCourse(id));
    }
}
