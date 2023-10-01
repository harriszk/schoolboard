package Service;

import DAO.CourseDAO;
import Model.Course;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import java.sql.Connection;

import java.util.List;

public class CourseService {
    private CourseDAO courseDAO;

    /**
     *
     * @param conn
     */
    public CourseService(Connection conn) {
        this.courseDAO = new CourseDAO(conn);
    }

    /**
     *
     *
     * @param courseDAO
     */
    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    /**
     *
     *
     * @return
     */
    public List<Course> getAllCourses() {
        return this.courseDAO.getAllCourses();
    }

    /**
     * 
     * @param teacherId
     * @return
     */
    public List<Course> getCoursesByTeacherId(int teacherId) {
        List<Course> courses = this.courseDAO.getCoursesByTeacherId(teacherId);

        return courses;
    }

    /**
     * 
     * @param courseId
     * @return
     */
    public Course getCourseById(int courseId) throws ItemDoesNotExistException {
        return this.courseDAO.getCourseById(courseId);
    }

    /**
     * 
     * @param course
     * @throws ItemAlreadyExistsException
     */
    public void addCourse(Course course) throws ItemAlreadyExistsException, ItemDoesNotExistException {
        this.courseDAO.addCourse(course);
    }

    /**
     * 
     * @param courseId
     * @throws ItemDoesNotExistException
     */
    public void deleteCourse(int courseId) throws ItemDoesNotExistException {
        this.courseDAO.deleteCourse(courseId);
    }

    /**
     * 
     * @param course
     * @throws ItemDoesNotExistException
     */
    public void updateCourse(Course course) throws ItemDoesNotExistException {
        this.courseDAO.updateCourse(course);
    }
}
