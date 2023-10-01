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

    /**
     * Sets up the test environment before each test case.
     * 
     * It resets the test database and initializes the `courseDAO` instance with a new database connection,
     * providing a clean testing environment for each test.
     */
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
    public void getCourseByIdSuccessfulTest() throws ItemDoesNotExistException {
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
    public void getCourseByIdUnsuccessfulTest() throws ItemDoesNotExistException {
        int id = -1;

        Assert.assertThrows(ItemDoesNotExistException.class, () -> this.courseDAO.getCourseById(id));
    }

    /**
     * Tests the getCoursesByTeacherId() method in the CourseDAO class when retrieving courses
     * taught by a teacher with a valid teacher ID.
     * 
     * This test case verifies that the getCoursesByTeacherId() method returns the expected list of
     * courses when provided with a valid teacher ID. It compares the expected list of courses to the
     * actual list obtained from the database and asserts that they are equal.
     */
    @Test
    public void getCoursesByTeacherIdSuccessfulTest() {
        int teacherId = 3;

        List<Course> expected = new ArrayList<Course>();
        expected.add(new Course(4, "ENG", 20200, "Literary Interpretation", 3.000, 3));
        expected.add(new Course(5, "ENG", 20400, "Introduction to Fiction", 3.000, 3));

        List<Course> actual = this.courseDAO.getCoursesByTeacherId(teacherId);
        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests the getCoursesByTeacherId() method in the CourseDAO class when retrieving courses
     * taught by a nonexistent teacher with an invalid teacher ID.
     * 
     * This test case verifies that the getCoursesByTeacherId() method returns an empty list when
     * provided with an invalid (nonexistent) teacher ID. It checks that the actual list obtained
     * from the database is empty, indicating that no courses are associated with the given teacher ID.
     */
    @Test
    public void getCoursesByTeacherIdNonexistentTeacherIdTest() {
        int teacherId = -1;

        List<Course> actual = this.courseDAO.getCoursesByTeacherId(teacherId);
        Assert.assertTrue(actual.isEmpty());
    }


    /**
     * This test is testing the addNewCourse() method in a CourseDAO class.
     *
     * @Test verifies:
     *    ...that the method performs an INSERT operation of a new record to Course table.
     *    If the record with that id is already exist in a table it throws ItemAlreadyExistsException.
     */
    @Test
    public void addNewCourseTest() throws ItemAlreadyExistsException, ItemDoesNotExistException {
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
     * Tests the addCourse() method in the CourseDAO class when adding a course with a nonexistent teacher ID.
     * 
     * This test case verifies that the addCourse() method correctly handles the scenario where a course
     * is added with a teacher ID that does not exist in the database. It expects the method to throw an
     * {@link ItemDoesNotExistException} due to the invalid teacher ID.
     */
    @Test
    public void addCourseWithNonexistentTeacherIdTest() {
        int id = 6;
        String courseSubject = "CHEM";
        int courseNumber = 34100;
        String courseName = "Organic Chemistry I";
        double creditHours = 5.000;
        int teacherId = -1;

        Course newCourse = new Course(id, courseSubject, courseNumber, courseName, creditHours, teacherId);

        Assert.assertThrows(ItemDoesNotExistException.class, () -> courseDAO.addCourse(newCourse));
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
    public void updateNonexistentCourseIdTest() {
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
        Assert.assertThrows(ItemDoesNotExistException.class, () -> this.courseDAO.getCourseById(id));
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
