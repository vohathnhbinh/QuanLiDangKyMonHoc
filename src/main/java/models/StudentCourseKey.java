package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class StudentCourseKey implements Serializable {
    @Column(name = "user_id")
    private int user_id;

    @Column(name = "course_id")
    private int course_id;

    public StudentCourseKey(int user_id, int course_id) {
        this.user_id = user_id;
        this.course_id = course_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourseKey that = (StudentCourseKey) o;
        return user_id == that.user_id && course_id == that.course_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, course_id);
    }
}
