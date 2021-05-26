package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subject")
public class Subject implements Serializable {
    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int subject_id;

    private String subject_number;
    private String subject_name;
    private int credit_amount;

    @OneToMany(mappedBy = "subject")
    private Set<Course> courses = new HashSet<>();

    public Subject(int subject_id, String subject_number, String subject_name, int credit_amount, Set<Course> courses) {
        this.subject_id = subject_id;
        this.subject_number = subject_number;
        this.subject_name = subject_name;
        this.credit_amount = credit_amount;
        this.courses = courses;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_number() {
        return subject_number;
    }

    public void setSubject_number(String subject_number) {
        this.subject_number = subject_number;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getCredit_amount() {
        return credit_amount;
    }

    public void setCredit_amount(int credit_amount) {
        this.credit_amount = credit_amount;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
