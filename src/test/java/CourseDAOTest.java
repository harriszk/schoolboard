import DAO.CourseDAO;
import DAO.TeacherDAO;
import Model.Course;
import Model.Teacher;
import Util.ConnectionSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOTest {
    private CourseDAO courseDAO;

    @Before
    public void setUp() {
        Connection conn = ConnectionSingleton.getConnection();
        courseDAO = new CourseDAO(conn);
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
    public void addNewCourseTest() {
        int newId = 6;
        String courseName = "History";
        Course newCourse = new Course(newId, courseName);
        boolean expected = true;
        boolean actual = courseDAO.addCourse(newCourse);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addCourseWithSameIdTest() {
        int newId = 3;
        String courseName = "History";
        Course newCourse = new Course(newId, courseName);
        boolean expected = false;
        boolean actual = courseDAO.addCourse(newCourse);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateExistingCourseNameTest() {
        int id = 3;
        String courseName = "History";
        boolean expected = true;
        boolean actual = courseDAO.updateCourse(id, courseName);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateNonexistentCourseNameTest() {
        int id = -1;
        String courseName = "History";
        boolean expected = false;
        boolean actual = courseDAO.updateCourse(id, courseName);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteCourseSuccessfulTest() {
        int id = 1;
        boolean expected = true;
        boolean actual = courseDAO.deleteCourse(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteCourseUnsuccessfulTest() {
        int id = -1;
        boolean expected = false;
        boolean actual = courseDAO.deleteCourse(id);
        Assert.assertEquals(expected, actual);
    }
}