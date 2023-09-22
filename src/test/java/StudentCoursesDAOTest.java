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

    @Test
    public void getAllStudentsByCourseIdTest() {
        int id = 3;
        List<StudentCourses> expected = new ArrayList<StudentCourses>();
        expected.add(new StudentCourses(2, id));
        expected.add(new StudentCourses(3, id));

        List<StudentCourses> actual = studentCoursesDAO.getAllStudentsByCourseId(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addNewStudentCourseEntrySuccessfulTest() throws ItemAlreadyExistsException {
        int studentId = 1;
        int courseId = 3;
        StudentCourses expected = new StudentCourses(studentId, courseId);

        studentCoursesDAO.addNewEntry(expected);
        List<StudentCourses> actual = studentCoursesDAO.getAllCoursesByStudentId(studentId);
        Assert.assertTrue(actual.contains(expected));
    }

    @Test
    public void addNewStudentCourseEntryUnsuccessfulTest() {
        int studentId = 1;
        int courseId = 2;
        StudentCourses newEntry = new StudentCourses(studentId, courseId);

        Assert.assertThrows(ItemAlreadyExistsException.class, () -> studentCoursesDAO.addNewEntry(newEntry));
    }

    @Test
    public void deleteStudentCourseEntrySuccessfulTest() throws ItemDoesNotExistException {
        int studentId = 1;
        int courseId = 2;
        StudentCourses entry = new StudentCourses(studentId, courseId);

        studentCoursesDAO.deleteEntry(entry);

        List<StudentCourses> actual = studentCoursesDAO.getAllCoursesByStudentId(studentId);
        Assert.assertFalse(actual.contains(entry));
    }

    @Test
    public void deleteStudentCourseEntryUnsuccessfulTest() {
        int studentId = 1;
        int courseId = 3;
        StudentCourses entry = new StudentCourses(studentId, courseId);

        Assert.assertThrows(ItemDoesNotExistException.class, () -> studentCoursesDAO.deleteEntry(entry));
    }

    @Test
    public void deleteAllEntriesByStudentIdTest() {
        int studentId = 1;

        studentCoursesDAO.deleteEntriesByStudentId(studentId);
        List<StudentCourses> actual = studentCoursesDAO.getAllCoursesByStudentId(studentId);

        Assert.assertTrue(actual.isEmpty());
    }
    
    @Test
    public void deleteAllEntriesByCourseIdTest() {
        int courseId = 1;

        studentCoursesDAO.deleteEntriesByCourseId(courseId);
        List<StudentCourses> actual = studentCoursesDAO.getAllStudentsByCourseId(courseId);

        Assert.assertTrue(actual.isEmpty());
    }
}
