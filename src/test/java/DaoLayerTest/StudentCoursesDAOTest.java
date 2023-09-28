package DaoLayerTest;

import Model.StudentCourses;
import DAO.StudentCoursesDAO;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;
import Util.ConnectionSingleton;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StudentCoursesDAOTest {
    private StudentCoursesDAO studentCoursesDAO;

    @Before
    public void setUp() {
        ConnectionSingleton.resetTestDatabase();
        studentCoursesDAO = new StudentCoursesDAO(ConnectionSingleton.getConnection());
    }

    /**
     * This test is testing the getAllEntries() method in a StudentCoursesDAO class.
     *
     * @Test verifies:
     *    ...that method provides an ArrayList populated by instances of StudentCoursesDAO class.
     */
    @Test
    public void getAllEntriesTest() {
        List<StudentCourses> expected = new ArrayList<StudentCourses>();
        expected.add(new StudentCourses(1, 1));
        expected.add(new StudentCourses(1, 2));
        expected.add(new StudentCourses(1, 5));
        expected.add(new StudentCourses(2, 2));
        expected.add(new StudentCourses(2, 3));
        expected.add(new StudentCourses(2, 4));
        expected.add(new StudentCourses(3, 1));
        expected.add(new StudentCourses(3, 3));

        List<StudentCourses> actual = studentCoursesDAO.getAllEntries();
        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the getAllCoursesByStudentId() method in a StudentCoursesDAO class.
     *
     * @Test verifies:
     *    ...that method provides an ArrayList populated by instances of StudentCoursesDAO class
     *    with a studentId=1.
     */
    @Test
    public void getAllCoursesByStudentIdTest() {
        int id = 1;
        List<StudentCourses> expected = new ArrayList<StudentCourses>();
        expected.add(new StudentCourses(id, 1));
        expected.add(new StudentCourses(id, 2));
        expected.add(new StudentCourses(id, 5));

        List<StudentCourses> actual = studentCoursesDAO.getAllCoursesByStudentId(id);
        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the getAllStudentsByCourseId() method in a StudentCoursesDAO class.
     *
     * @Test verifies:
     *    ...that method provides an ArrayList populated by instances of StudentCoursesDAO class
     *    with a courseId=3.
     */
    @Test
    public void getAllStudentsByCourseIdTest() {
        int id = 3;
        List<StudentCourses> expected = new ArrayList<StudentCourses>();
        expected.add(new StudentCourses(2, id));
        expected.add(new StudentCourses(3, id));

        List<StudentCourses> actual = studentCoursesDAO.getAllStudentsByCourseId(id);
        Assert.assertEquals(expected, actual);
    }


    /**
     * This test is testing the addNewStudentCourseEntry() method in a StudentCoursesDAO class.
     *
     * @Test verifies:
     *    ...that the method performs an INSERT operation of a new record to StudentCourses table.
     *    If the record with that id is already exist in a table it throws ItemAlreadyExistsException.
     */
    @Test
    public void addNewStudentCourseEntrySuccessfulTest() throws ItemAlreadyExistsException {
        int studentId = 1;
        int courseId = 3;
        StudentCourses expected = new StudentCourses(studentId, courseId);

        studentCoursesDAO.addNewEntry(expected);
        List<StudentCourses> actual = studentCoursesDAO.getAllCoursesByStudentId(studentId);
        Assert.assertTrue(actual.contains(expected));
    }

    /**
     * This test is testing the addNewStudentCourseEntry() method in a StudentCoursesDAO class.
     *
     * @Test verifies:
     *    ...that the method throws ItemAlreadyExistsException when attempting to add a record
     *    to the StudentCourses table with a values that already exists in the table.
     */
    @Test
    public void addNewStudentCourseEntryUnsuccessfulTest() {
        int studentId = 1;
        int courseId = 2;
        StudentCourses newEntry = new StudentCourses(studentId, courseId);

        Assert.assertThrows(ItemAlreadyExistsException.class, () -> studentCoursesDAO.addNewEntry(newEntry));
    }

    /**
     * This test is testing the deleteStudentCourseEntry() method in a StudentCoursesDAO class.
     *
     * @Test verifies:
     *    ...that the method performs an DELETE operation on an existing record in the StudentCourses table.
     *    If the record is not found, it throws an ItemDoesNotExistException.
     */
    @Test
    public void deleteStudentCourseEntrySuccessfulTest() throws ItemDoesNotExistException {
        int studentId = 1;
        int courseId = 2;
        StudentCourses entry = new StudentCourses(studentId, courseId);

        studentCoursesDAO.deleteEntry(entry);

        List<StudentCourses> actual = studentCoursesDAO.getAllCoursesByStudentId(studentId);
        Assert.assertFalse(actual.contains(entry));
    }

    /**
     * This test is testing the deleteStudentCourseEntry() method in a StudentCoursesDAO class.
     *
     * @Test verifies:
     *    ...that the method throws an ItemDoesNotExistException when attempting to DELETE
     *    a nonexistent record in the StudentCoursesDAO table.
     **/
    @Test
    public void deleteStudentCourseEntryUnsuccessfulTest() {
        int studentId = 1;
        int courseId = 3;
        StudentCourses entry = new StudentCourses(studentId, courseId);

        Assert.assertThrows(ItemDoesNotExistException.class, () -> studentCoursesDAO.deleteEntry(entry));
    }

    /**
     * This test is testing the deleteAllEntriesByStudentId() method in a StudentCoursesDAO class.
     *
     * @Test verifies:
     *    ...that the method performs an DELETE operation on an existing records where studentId = 1
     *    in the StudentCourses table.
     */
    @Test
    public void deleteAllEntriesByStudentIdTest() {
        int studentId = 1;

        studentCoursesDAO.deleteEntriesByStudentId(studentId);
        List<StudentCourses> actual = studentCoursesDAO.getAllCoursesByStudentId(studentId);

        Assert.assertTrue(actual.isEmpty());
    }


    /**
     * This test is testing the deleteAllEntriesByCourseId() method in a StudentCoursesDAO class.
     *
     * @Test verifies:
     *    ...that the method performs an DELETE operation on an existing records where courseId = 1
     *    in the StudentCourses table.
     */
    @Test
    public void deleteAllEntriesByCourseIdTest() {
        int courseId = 1;

        studentCoursesDAO.deleteEntriesByCourseId(courseId);
        List<StudentCourses> actual = studentCoursesDAO.getAllStudentsByCourseId(courseId);

        Assert.assertTrue(actual.isEmpty());
    }
}
