package Model;

import java.util.Objects;

public class Course {
    private String subject;
    private int number;
    private String title;
    private double creditHours;
    private int teacherId;

    public Course() {

    }

    public Course(String subject, int number, String title, double creditHours, int teacherId) {
        this.subject = subject;
        this.number = number;
        this.title = title;
        this.creditHours = creditHours;
        this.teacherId = teacherId;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCreditHours() {
        return this.creditHours;
    }

    public void setCreditHours(double creditHours) {
        this.creditHours = creditHours;
    }

    public int getTeacherId() {
        return this.teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Course)) {
            return false;
        }
        Course course = (Course) o;
        return Objects.equals(subject, course.subject) && number == course.number && Objects.equals(title, course.title) && creditHours == course.creditHours && teacherId == course.teacherId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, number, title, creditHours, teacherId);
    }

    @Override
    public String toString() {
        return "{" +
            " subject='" + getSubject() + "'" +
            ", number='" + getNumber() + "'" +
            ", title='" + getTitle() + "'" +
            ", creditHours='" + getCreditHours() + "'" +
            ", teacherId='" + getTeacherId() + "'" +
            "}";
    }
}
