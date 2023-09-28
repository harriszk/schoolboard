package DaoLayerTest;

import Model.Course;
import Model.Teacher;
import DAO.TeacherDAO;
import Exception.ItemDoesNotExistException;
import Exception.ItemAlreadyExistsException;
import Util.ConnectionSingleton;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TeacherDAOTest {
    private TeacherDAO teacherDAO;

    @Before
    public void setUp() {
        ConnectionSingleton.resetTestDatabase();
        teacherDAO = new TeacherDAO(ConnectionSingleton.getConnection());
    }

    /**
     * This test is testing the getAllTeachers() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that method provides an ArrayList populated by instances of Teacher class.
     */
    @Test
    public void getAllTeachersTest() {
        List<Teacher> expected = new ArrayList<>();
        expected.add(new Teacher(1, "Zachary Harris"));
        expected.add(new Teacher(2, "Ralph Fatkullin"));
        expected.add(new Teacher(3, "Walt Whitman"));

        List<Teacher> actual = teacherDAO.getAllTeachers();
        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the getTeacherById() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that method provides an instance of Teacher class with a Teacher.Id=1.
     */
    @Test
    public void getTeacherByIdSuccessfulTest() throws ItemDoesNotExistException {
        int id = 1;
        String name = "Zachary Harris";
        Teacher expected = new Teacher(id, name);
        Teacher actual = teacherDAO.getTeacherById(1);

        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the getTeacherById() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that the method throws an ItemDoesNotExistException when attempting to get
     *    a nonexistent record in the Teacher table with Teacher.Id = -1.
     */
    @Test
    public void getTeacherByIdUnsuccessfulTest() throws ItemDoesNotExistException {
        int id = -1;
        Assert.assertThrows(ItemDoesNotExistException.class, () -> teacherDAO.getTeacherById(id));
    }

    /**
     * This test is testing the addTeacher() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that the method performs an INSERT operation of a new record to the Teacher table.
     *    If the record with that id is already exist in a table it throws ItemAlreadyExistsException.
     */
    @Test
    public void addNewTeacherTest() throws ItemAlreadyExistsException, ItemDoesNotExistException {
        //ConnectionSingleton.resetTestDatabase();
        int id = 4;
        String name = "John Doe";
        Teacher expected = new Teacher(id, name);
        teacherDAO.addTeacher(expected);

        Teacher actual = teacherDAO.getTeacherById(id);
        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the addTeacher() method in a TeacherDAO class.
     *
     * @Test verifies:
     *     ...that the method throws an ItemAlreadyExistsException when attempting to add
     *    a record to the Teacher table with a value in the ID field that already exists in the table.
     */
    @Test
    public void addTeacherWithSameIdTest() {
        int id = 1;
        String name = "John Doe";
        Teacher newTeacher = new Teacher(id, name);
        
        Assert.assertThrows(ItemAlreadyExistsException.class, () -> teacherDAO.addTeacher(newTeacher));
    }


    /**
     * This test is testing the updateTeacher() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that the method performs an UPDATE operation on an existing record in the Teacher table.
     *    If the record is not found, it throws an ItemDoesNotExistException.
     **/
    @Test
    public void updateExistingTeacherNameTest() throws ItemDoesNotExistException {
        int id = 1;
        String name = "New name";
        teacherDAO.updateTeacher(id, name);

        Teacher expected = new Teacher(id, name);
        Teacher actual = teacherDAO.getTeacherById(id);
        Assert.assertEquals(expected, actual);
    }

    /**
     * This test is testing the updateTeacher() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that the method throws an ItemDoesNotExistException when attempting to UPDATE
     *    a nonexistent record in the Teacher table.
     **/
    @Test
    public void updateNonexistentTeacherNameTest() {
        int id = -1;
        String name = "New name";

        Assert.assertThrows(ItemDoesNotExistException.class, () -> teacherDAO.updateTeacher(id, name));
    }

    /**
     * This test is testing the deleteTeacher() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that the method performs an DELETE operation on an existing record in the Teacher table.
     *    If the record is not found, it throws an ItemDoesNotExistException.
     **/
    @Test
    public void deleteTeacherSuccessfulTest() throws ItemDoesNotExistException {
        int id = 1;

        teacherDAO.deleteTeacher(id);
        Assert.assertThrows(ItemDoesNotExistException.class, () -> teacherDAO.getTeacherById(id));
    }

    /**
     * This test is testing the deleteTeacher() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that the method throws an ItemDoesNotExistException when attempting to DELETE
     *    a nonexistent record in the Teacher table.
     **/
    @Test
    public void deleteTeacherUnsuccessfulTest() {
        int id = -1;

        Assert.assertThrows(ItemDoesNotExistException.class, () -> teacherDAO.deleteTeacher(id));
    }

    /**
     * This test is testing the coursesByTeacher() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that method provides an ArrayList populated by instances of Course class
     *    related to particular instance of a Teacher class.
     */

    @Test
    public void coursesByTeacherSuccessfulTest() throws ItemDoesNotExistException {
        Teacher teacher = new Teacher(3,"Walt Whitman");
        List<Course> expected = new ArrayList<>();

        expected.add(new Course(4, "ENG", 20200, "Literary Interpretation", 3.000, 3));
        expected.add(new Course(5, "ENG", 20400, "Introduction to Fiction", 3.000, 3));

        Assert.assertEquals(expected,teacherDAO.coursesByTeacher(teacher));
    }

    /**
     * This test is testing the coursesByTeacher() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that the method throws an ItemDoesNotExistException when attempting to get
     *    a  record of a courses related to nonexistent record in the Teacher table.
     **/
    @Test
    public void coursesByTeacherUnsuccessfulTest() throws ItemDoesNotExistException{
        Teacher teacher = new Teacher(4,"Walt");

        Assert.assertThrows(ItemDoesNotExistException.class, () -> teacherDAO.coursesByTeacher(teacher));

    }

    /**
     * This test is testing the searchTeacher() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that method provides an instance of Teacher class with a Teacher.name="Walt Whitman".
     */
    @Test
    public void searchTeacherSuccessfulTest() throws ItemDoesNotExistException {
        Teacher expected = new Teacher(3,"Walt Whitman");
        String name = "Walt Whitman";

        Assert.assertEquals(expected,teacherDAO.searchTeacherByName(name));
    }

    /**
     * This test is testing the searchTeacher() method in a TeacherDAO class.
     *
     * @Test verifies:
     *    ...that method throws an ItemDoesNotExistException when attempting to get
     *    an nonexistent record in the Teacher table with a Teacher.name="Walt".
     */

    @Test
    public void searchTeacherUnsuccessfulTest()  {
        String name = "Walt";

        //Assert.assertEquals(expected,teacherDAO.searchTeacherByName(name));
        Assert.assertThrows(ItemDoesNotExistException.class, () -> teacherDAO.searchTeacherByName(name));

    }

}
