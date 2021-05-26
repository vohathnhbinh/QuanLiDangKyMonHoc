package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

enum Date_of_week {
    MON, TUE, WED, THU, FRI, SAT, SUN
}

enum Shift {
    ONE("7:30-9:30"),
    TWO("9:30-11:30"),
    THREE("13:30-15:30"),
    FOUR("15:30-17:30");

    private String shift_time;

    Shift(String shift_time) {
        this.shift_time = shift_time;
    }

    public String getShift_time() {
        return this.shift_time;
    }
}

@Entity
@Table(name = "course")
public class Course implements Serializable {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int course_id;

    @ManyToOne
    @JoinColumn(name="semester_id", nullable=false)
    private Semester semester;

    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
    private Subject subject;

    private String teacher_name;
    private String classroom;

    @Enumerated(EnumType.STRING)
    private Date_of_week date_of_week;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    private int max_slot;

    @OneToMany(mappedBy = "course")
    Set<Student_Course> stu_cours = new HashSet<>();

    public Course(int course_id, Semester semester, Subject subject, String teacher_name, String classroom, Date_of_week date_of_week, Shift shift, int max_slot, Set<Student_Course> stu_cours) {
        this.course_id = course_id;
        this.semester = semester;
        this.subject = subject;
        this.teacher_name = teacher_name;
        this.classroom = classroom;
        this.date_of_week = date_of_week;
        this.shift = shift;
        this.max_slot = max_slot;
        this.stu_cours = stu_cours;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public Date_of_week getDate_of_week() {
        return date_of_week;
    }

    public void setDate_of_week(Date_of_week date_of_week) {
        this.date_of_week = date_of_week;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public int getMax_slot() {
        return max_slot;
    }

    public void setMax_slot(int max_slot) {
        this.max_slot = max_slot;
    }

    public Set<Student_Course> getStu_cours() {
        return stu_cours;
    }

    public void setStu_cours(Set<Student_Course> stu_cours) {
        this.stu_cours = stu_cours;
    }
}
