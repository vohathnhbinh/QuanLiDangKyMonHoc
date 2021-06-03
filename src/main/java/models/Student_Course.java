package models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "student_course")
public class Student_Course implements Serializable {
    @EmbeddedId
    StudentCourseKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("course_id")
    @JoinColumn(name = "course_id")
    Course course;

    private Timestamp create_on;

    public Student_Course() {
    }

    public Student_Course(StudentCourseKey id, Student student, Course course, Timestamp create_on) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.create_on = create_on;
    }

    public Student_Course(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.id = new StudentCourseKey(student.getUser_id(), course.getCourse_id());
    }

    public StudentCourseKey getId() {
        return id;
    }

    public void setId(StudentCourseKey id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Timestamp getcreate_on() {
        return create_on;
    }

    public void setcreate_on(Timestamp create_on) {
        this.create_on = create_on;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student_Course that = (Student_Course) o;
        return Objects.equals(id, that.id) && Objects.equals(student, that.student) && Objects.equals(course, that.course) && Objects.equals(create_on, that.create_on);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, create_on);
    }
}
