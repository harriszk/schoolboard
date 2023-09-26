package Service;

import DAO.CourseDAO;
import Model.Course;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;
import Model.Teacher;

import java.sql.Connection;
import java.util.List;

public class CourseService {
    private CourseDAO courseDAO;

    // TODO: Fill in comments!
    /**
     *
     * @param conn
     */
    public CourseService(Connection conn) {
        courseDAO = new CourseDAO(conn);
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
     *
     * @param id
     * @return
     */
    public Course getCourseById(int id) {
        return this.courseDAO.getCourseById(id);
    }

    /**
     * 
     * @param course
     * @throws ItemAlreadyExistsException
     */
    public void addCourse(Course course) throws ItemAlreadyExistsException {
        this.courseDAO.addCourse(course);
    }

    /**
     * 
     * @param id
     * @throws ItemDoesNotExistException
     */
    public void deleteCourse(int id) throws ItemDoesNotExistException {
        this.courseDAO.deleteCourse(id);
    }

    /**
     * 
     * @param id
     * @param courseName
     * @throws ItemDoesNotExistException
     */
    public void updateCourse(int id, String courseName) throws ItemDoesNotExistException {
        this.courseDAO.updateCourse(id, courseName);
    }
}
