package Service;

import DAO.CourseDAO;
import Model.Course;
import Exception.CourseAlreadyExistsException;
import Exception.CourseDoesNotExistException;

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

    public void addCourse(Course course) throws CourseAlreadyExistsException {
        this.courseDAO.addCourse(course);
    }

    public void deleteCourse(int id) throws CourseDoesNotExistException {
        this.courseDAO.deleteCourse(id);
    }

    public void updateCourse(int id, String courseName) throws CourseDoesNotExistException {
        this.courseDAO.updateCourse(id, courseName);
    }
}
