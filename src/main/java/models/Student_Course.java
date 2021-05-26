package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "student_course")
public class Student_Course implements Serializable {
    @EmbeddedId
    StudentCourseKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    Student student;

    @ManyToOne
    @MapsId("course_id")
    @JoinColumn(name = "course_id")
    Course course;

    private Date createOn = new Date(System.currentTimeMillis());
}
