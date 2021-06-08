package models;

import additionals.Date_of_week;
import additionals.Shift;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name="teacher_id", nullable=false)
    private Teacher teacher;

    private String classroom;

    @Enumerated(EnumType.STRING)
    private Date_of_week date_of_week;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    private int max_slot;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Student_Course> students = new HashSet<>();

    public Course() {
    }

    public Course(int course_id, Semester semester, Subject subject, Teacher teacher, String classroom, Date_of_week date_of_week, Shift shift, int max_slot, Set<Student_Course> students) {
        this.course_id = course_id;
        this.semester = semester;
        this.subject = subject;
        this.teacher = teacher;
        this.classroom = classroom;
        this.date_of_week = date_of_week;
        this.shift = shift;
        this.max_slot = max_slot;
        this.students = students;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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

    public Set<Student_Course> getStudents() {
        return students;
    }

    public void setStudents(Set<Student_Course> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return course_id == course.course_id && max_slot == course.max_slot && Objects.equals(semester, course.semester) && Objects.equals(subject, course.subject) && Objects.equals(teacher, course.teacher) && Objects.equals(classroom, course.classroom) && date_of_week == course.date_of_week && shift == course.shift && Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course_id, semester, subject, teacher, classroom, date_of_week, shift, max_slot);
    }
}
