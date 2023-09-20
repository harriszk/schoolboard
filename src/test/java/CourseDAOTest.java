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

    }

    @Test
    public void addCourseWithSameIdTest() {

    }

    @Test
    public void updateExistingCourseNameTest() {

    }

    @Test
    public void updateNonexistentCourseNameTest() {

    }

    @Test
    public void deleteCourseSuccessfulTest() {

    }

    @Test
    public void deleteCourseUnsuccessfulTest() {

    }
}
