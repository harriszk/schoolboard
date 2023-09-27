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
        expected.add(new Course(1, "MATH", 11000, "Fundamentals of Algebra", 4.000, 0));
        expected.add(new Course(2, "MATH", 15100, "Algebra and Trigonometry", 4.000, 0));
        expected.add(new Course(3, "MATH", 15400, "Trigonometry", 3.000, 0));
        expected.add(new Course(4, "MATH", 15900, "Pre-calculus", 5.000, 0));
        expected.add(new Course(5, "MATH", 16500, "Analytic Geometry and Calculus I", 4.000, 0));
        expected.add(new Course(6, "MATH", 16600, "Analytic Geometry and Calculus II", 4.000, 0));
        expected.add(new Course(7, "MATH", 26100, "Multivariate Calculus", 4.000, 0));
        expected.add(new Course(8, "MATH", 26600, "Ordinary Differential Equations", 3.000, 0));
        expected.add(new Course(9, "MATH", 30000, "Logic and the Foundations of Algebra", 3.000, 0));
        expected.add(new Course(10, "MATH", 35100, "Elementary Linear Algebra", 3.000, 0));
        expected.add(new Course(11, "MATH", 41400, "Numerical Methods", 3.000, 0));
        expected.add(new Course(12, "MATH", 44400, "Real Analysis I", 3.000, 0));
        expected.add(new Course(13, "MATH", 44500, "Real Analysis II", 3.000, 0));
        expected.add(new Course(14, "MATH", 45300, "Elementary Abstract Algebra", 3.000, 0));
        expected.add(new Course(15, "MATH", 46200, "Elementary Differential Geometry", 3.000, 0));
        expected.add(new Course(16, "MATH", 52300, "Introduction to Partial Differential Equations", 3.000, 0));
        expected.add(new Course(17, "MATH", 52500, "Introduction to Complex Analysis", 3.000, 0));

        List<Course> actual = this.courseDAO.getAllCourses();
        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the getCourseById() method in a CourseDAO class.
     *
     * @Test verifies:
     *    ...that method provides an instances of Course class with particular Course.ID.
     */
    @Test
    public void getCourseByIdSuccessfulTest() {
        int id = 1;
        String courseSubject = "MATH";
        int courseNumber = 11000;
        String courseName = "Fundamentals of Algebra";
        double creditHours = 4.000;
        int teacherId = 0;

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
        int id = 18;
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
