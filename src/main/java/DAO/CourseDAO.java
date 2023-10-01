package DAO;

import Model.Course;
import Exception.ItemAlreadyExistsException;
import Exception.ItemDoesNotExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import static Util.LogUtil.log;

public class CourseDAO {
    private Connection conn;

    private static int PRIMARY_KEY_VIOLATION_CODE = 23505;
    private static int FOREIGN_KEY_VIOLATION_CODE = 23506;
    
    /**
     * Constructs a new Course Data Access Object (DAO).
     * 
     * This constructor creates a new instance of the CourseDAO class, which is responsible for handling
     * database operations related to courses. It takes a database connection as a parameter, which will be
     * used to interact with the underlying data store.
     * 
     * @param conn The database connection used for data access operations.
     */
    public CourseDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * Retrieves a list of all courses from the database.
     * 
     * This method queries the database to retrieve a list of all available courses and returns them as
     * a List of Course objects. Each Course object represents a course with attributes such as ID, subject,
     * number, title, credit hours, and teacher ID.
     * 
     * @return A List of Course objects containing information about all available courses in the database.
     */
    public List<Course> getAllCourses(){
        List<Course> courses = new ArrayList<Course>();

        try {
            PreparedStatement ps = conn.prepareStatement("select * from course");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String subject = rs.getString("subject");
                int number = rs.getInt("number");
                String title = rs.getString("title");
                double creditHours = rs.getDouble("credit_hours");
                int teacherId = rs.getInt("teacher_id");

                courses.add(new Course(id, subject, number, title, creditHours, teacherId));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    /**
     * Retrieves a course from the database by its unique ID.
     * 
     * This method queries the database to retrieve a course with the specified ID and returns it as
     * a Course object. The Course object represents a course with attributes such as subject, number,
     * title, credit hours, and teacher ID.
     * 
     * @param courseId The unique identifier of the course to retrieve.
     * @return A Course object containing information about the course with the specified ID.
     * @throws ItemDoesNotExistException If no course with the given ID is found in the database.
     */
    public Course getCourseById(int courseId) throws ItemDoesNotExistException {
        Course course = null;

        try {
            String sql = "select * from course where course.id=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                String subject = rs.getString("subject");
                int number = rs.getInt("number");
                String title = rs.getString("title");
                double creditHours = rs.getDouble("credit_hours");
                int teacherId = rs.getInt("teacher_id");

                course = new Course(courseId, subject, number, title, creditHours, teacherId);

                log.info("Course: {}", course);
            } else {
                throw new ItemDoesNotExistException("Course");
            } 
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    /**
     * Retrieves a list of courses taught by a teacher with the specified ID.
     * 
     * This method queries the database to retrieve a list of courses taught by a teacher with the provided
     * teacher ID. The courses are returned as a List of Course objects, each representing a course with
     * attributes such as ID, subject, number, title, and credit hours.
     * 
     * @param teacherId The unique identifier of the teacher whose courses are to be retrieved.
     * @return A List of Course objects containing information about the courses taught by the specified teacher.
     */
    public List<Course> getCoursesByTeacherId(int teacherId) {
        List<Course> courses = new ArrayList<Course>();

        try {
            String sql = "SELECT * FROM Course WHERE Course.teacher_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String subject = rs.getString("subject");
                int number = rs.getInt("number");
                String title = rs.getString("title");
                double creditHours = rs.getDouble("credit_hours");

                courses.add(new Course(id, subject, number, title, creditHours, teacherId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    /**
     * Adds a new course to the database.
     * 
     * This method inserts a new course record into the database using the provided Course object. The course
     * information, including ID, subject, number, title, credit hours, and teacher ID, is extracted from the
     * Course object and used to create a new database entry.
     * 
     * @param course The Course object representing the course to be added.
     * @throws ItemAlreadyExistsException If a course with the same ID already exists in the database.
     * @throws ItemDoesNotExistException If no teacher with the given  ID exists in the database.
     */
    public void addCourse(Course course) throws ItemAlreadyExistsException, ItemDoesNotExistException {
        try {
            String sql = "INSERT INTO Course (id, subject, number, title, credit_hours, teacher_id) VALUES (?, ?, ?, ?, ?, ?);";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, course.getId());
            ps.setString(2, course.getSubject());
            ps.setInt(3, course.getNumber());
            ps.setString(4, course.getTitle());
            ps.setDouble(5, course.getCreditHours());
            ps.setInt(6, course.getTeacherId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

            if (e.getErrorCode() == PRIMARY_KEY_VIOLATION_CODE) {
                throw new ItemAlreadyExistsException("Course");
            } 
            
            if (e.getErrorCode() == FOREIGN_KEY_VIOLATION_CODE) {
                throw new ItemDoesNotExistException("Teacher");
            }
        }
    }

    /**
     * Updates an existing course in the database.
     * 
     * This method updates the information of an existing course in the database based on the provided
     * Course object. The course information, including subject, number, title, credit hours, and teacher ID,
     * is extracted from the Course object and used to update the corresponding database entry.
     * 
     * @param course The Course object representing the updated course information.
     * @throws ItemDoesNotExistException If no course with the given ID exists in the database.
     */
    public void updateCourse(Course course) throws ItemDoesNotExistException {
        try {
            String sql = "UPDATE Course SET Course.subject = ?, Course.number = ?, course.title = ?, Course.credit_hours = ?, Course.teacher_id = ? WHERE Course.id = ?;";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, course.getSubject());
            ps.setInt(2, course.getNumber());
            ps.setString(3, course.getTitle());
            ps.setDouble(4, course.getCreditHours());
            ps.setInt(5, course.getTeacherId());
            ps.setInt(6, course.getId());

            if (ps.executeUpdate() == 0) {
                throw new ItemDoesNotExistException("Course");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a course from the database based on its ID.
     * 
     * This method attempts to delete a course record from the database. The deletion is performed
     * based on the course's unique ID.
     * 
     * @param courseId The unique identifier of the course to be deleted.
     * @throws ItemDoesNotExistException If no course with the given ID is found in the database.
     */
    public void deleteCourse(int courseId) throws ItemDoesNotExistException {
        try {
            String sql = "DELETE FROM Course WHERE Course.id = ?;";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);

            if (ps.executeUpdate() == 0) {
                throw new ItemDoesNotExistException("Course");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
