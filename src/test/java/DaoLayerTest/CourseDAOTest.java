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
        this.courseDAO = new CourseDAO(ConnectionSingleton.getConnection());
    }

    /**
     * This test is testing the getAllCourses() method in a CourseDAO class.
     *
     * @Test verifies:
     *    ...that method provides an ArrayList populated by instances of Course class.
     */

    @Test
    public void getAllCoursesTest() {
        List<Course> expected = new ArrayList<Course>();
        expected.add(new Course(1, "MATH", 15000, "Number Systems", 4.000, 1));
        expected.add(new Course(2, "BIOL", 12300, "Biology 101", 4.000, 1));
        expected.add(new Course(3, "HIST", 13300, "World History", 3.000, 2));
        expected.add(new Course(4, "ENG", 20200, "Literary Interpretation", 3.000, 3));
        expected.add(new Course(5, "ENG", 20400, "Introduction to Fiction", 3.000, 3));

        List<Course> actual = this.courseDAO.getAllCourses();
        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the getCourseById() method in a CourseDAO class.
     *
     * @Test verifies:
     *    ...that method provides an instances of Course class with a course.Id=3.
     */
    @Test
    public void getCourseByIdSuccessfulTest() {
        int id = 1;
        String courseSubject = "MATH";
        int courseNumber = 15000;
        String courseName = "Number Systems";
        double creditHours = 4.000;
        int teacherId = 1;

        Course expected = new Course(id, courseSubject, courseNumber, courseName, creditHours, teacherId);
        Course actual = this.courseDAO.getCourseById(id);

        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the getCourseById() method in a CourseDAO class.
     *
     * @Test verifies:
     *    ...that method return a Null value in attempting to get a course
     *    from Course table with a parameter id=-1.
     */

    @Test
    public void getCourseByIdUnsuccessfulTest() {
        Course actual = this.courseDAO.getCourseById(-1);
        Assert.assertNull(actual);
    }


    /**
     * This test is testing the addNewCourse() method in a CourseDAO class.
     *
     * @Test verifies:
     *    ...that the method performs an INSERT operation of a new record to Course table.
     *    If the record with that id is already exist in a table it throws ItemAlreadyExistsException.
     */

    @Test
    public void addNewCourseTest() throws ItemAlreadyExistsException {
        int id = 6;
        String courseSubject = "CHEM";
        int courseNumber = 34100;
        String courseName = "Organic Chemistry I";
        double creditHours = 5.000;
        int teacherId = 1;

        Course expected = new Course(id, courseSubject, courseNumber, courseName, creditHours, teacherId);
        this.courseDAO.addCourse(expected);

        Course actual = courseDAO.getCourseById(id);
        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the addCourse() method in a CourseDAO class.
     *
     * @Test verifies:
     *     ...that the method throws an ItemAlreadyExistsException when attempting to add
     *    a record to the Courses table with a value in the ID field that already exists in the table.
     */
    @Test
    public void addCourseWithSameIdTest() {
        int id = 1;
        String courseSubject = "CHEM";
        int courseNumber = 34100;
        String courseName = "Organic Chemistry I";
        double creditHours = 5.000;
        int teacherId = 1;

        Course newCourse = new Course(id, courseSubject, courseNumber, courseName, creditHours, teacherId);

        Assert.assertThrows(ItemAlreadyExistsException.class, () -> courseDAO.addCourse(newCourse));
    }


    /**
     * This test is testing the updateCourse() method in a CourseDAO class.
     *
     * @Test verifies:
     *    ...that the method performs an UPDATE operation on an existing record in the Courses table.
     *    If the record is not found, it throws an ItemDoesNotExistException.
     **/
    @Test
    public void updateExistingCourseTest() throws ItemDoesNotExistException {
        int id = 1;
        String courseSubject = "CHEM";
        int courseNumber = 34100;
        String courseName = "Organic Chemistry I";
        double creditHours = 5.000;
        int teacherId = 1;

        Course expected = new Course(id, courseSubject, courseNumber, courseName, creditHours, teacherId);
        courseDAO.updateCourse(expected);
        
        Course actual = courseDAO.getCourseById(id);
        Assert.assertEquals(expected, actual);
    }


    /**
     * This test is testing the updateCourse() method in a CourseDAO class.
     *
     * @Test verifies:
     *    ...that the method throws an ItemDoesNotExistException when attempting to UPDATE
     *    a nonexistent record in the Courses table.
     **/
    @Test
    public void updateNonexistentCourseNameTest() {
        int id = -1;
        String courseSubject = "CHEM";
        int courseNumber = 34100;
        String courseName = "Organic Chemistry I";
        double creditHours = 5.000;
        int teacherId = 1;

        Course course = new Course(id, courseSubject, courseNumber, courseName, creditHours, teacherId);

        Assert.assertThrows(ItemDoesNotExistException.class, () -> courseDAO.updateCourse(course));
    }

    /**
     * This test is testing the deleteCourse() method in a CourseDAO class.
     *
     * @Test verifies:
     *    ...that the method performs an DELETE operation on an existing record in the Courses table.
     *    If the record is not found, it throws an ItemDoesNotExistException.
     **/
    @Test
    public void deleteCourseSuccessfulTest() throws ItemDoesNotExistException {
        int id = 1;

        courseDAO.deleteCourse(id);
        Course actual = courseDAO.getCourseById(id);
        Assert.assertNull(actual);
    }

    /**
     * This test is testing the deleteCourse() method in a CourseDAO class.
     *
     * @Test verifies:
     *    ...that the method throws an ItemDoesNotExistException when attempting to DELETE
     *    a nonexistent record in the Courses table.
     **/
    @Test
    public void deleteCourseUnsuccessfulTest() {
        int id = -1;

        Assert.assertThrows(ItemDoesNotExistException.class, () -> courseDAO.deleteCourse(id));
    }
}
