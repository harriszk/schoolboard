package Model;

import java.util.Objects;

public class StudentCourses {
    private int studentId;
    private int courseId;

    public StudentCourses(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getStudentId() {
        return this.studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourses that = (StudentCourses) o;
        return studentId == that.studentId && courseId == that.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }

    @Override
    public String toString() {
        return "{" +
            " studentId='" + getStudentId() + "'" +
            ", courseId='" + getCourseId() + "'" +
            "}";
    }    
}
