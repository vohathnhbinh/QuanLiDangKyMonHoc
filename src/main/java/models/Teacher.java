package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher implements Serializable{
    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int teacher_id;

    private String teacher_name;

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses = new HashSet<>();

    public Teacher() {}

    public Teacher(int teacher_id, String teacher_name, Set<Course> courses) {
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
        this.courses = courses;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
