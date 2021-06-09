package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "semester")
public class Semester implements Serializable {
    @Id
    @Column(name = "semester_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int semester_id;

    private String name;
    private String school_year;
    private Date start_date;
    private Date end_date;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RegSession> regSessions = new HashSet<>();

    public Semester() {
    }

    public Semester(int semester_id, String name, String school_year, Date start_date, Date end_date, Set<Course> courses, Set<RegSession> regSessions) {
        this.semester_id = semester_id;
        this.name = name;
        this.school_year = school_year;
        this.start_date = start_date;
        this.end_date = end_date;
        this.courses = courses;
        this.regSessions = regSessions;
    }

    public int getSemester_id() {
        return semester_id;
    }

    public void setSemester_id(int semester_id) {
        this.semester_id = semester_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool_year() {
        return school_year;
    }

    public void setSchool_year(String school_year) {
        this.school_year = school_year;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<RegSession> getSessions() {
        return regSessions;
    }

    public void setSessions(Set<RegSession> regSessions) {
        this.regSessions = regSessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semester semester = (Semester) o;
        return semester_id == semester.semester_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(semester_id, name, school_year, start_date, end_date);
    }
}
