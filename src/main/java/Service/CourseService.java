package Service;

import DAO.CourseDAO;
import Model.Course;

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

    public void addCourse() {

    }

    public void deleteCourse() {

    }

    public void updateCourse() {

    }
}
