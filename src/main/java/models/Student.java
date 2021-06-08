package models;

import additionals.Gender;
import additionals.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student extends User{
    private String student_number;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name="class_id")
    private Class myClass;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Student_Course> courses = new HashSet<>();

    public Student() {}

    public Student(String fullname, Class myClass, Gender gender) {
        super(fullname, Role.STUDENT);
        this.myClass = myClass;
        this.gender = gender;
    }

    public Student(int user_id, String username, String password, String fullname, String student_number, Gender gender, Class myClass, Set<Student_Course> courses) {
        super(user_id, username, password, fullname, Role.STUDENT);
        this.student_number = student_number;
        this.gender = gender;
        this.myClass = myClass;
        this.courses = courses;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Class getMyClass() {
        return myClass;
    }

    public void setMyClass(Class myClass) {
        this.myClass = myClass;
    }

    public Set<Student_Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Student_Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(student_number, student.student_number) && gender == student.gender && Objects.equals(myClass, student.myClass) && Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student_number, gender);
    }

    public void addCourse(Course course) {
        Student_Course studentCourse = new Student_Course(this, course);
        courses.add(studentCourse);
        course.getStudents().add(studentCourse);
    }

    public void removeCourse(Course course) {
        for (Iterator<Student_Course> iterator = courses.iterator();
             iterator.hasNext(); ) {
            Student_Course studentCourse = iterator.next();
            if (studentCourse.getStudent().equals(this) &&
                    studentCourse.getCourse().getCourse_id() == course.getCourse_id()) {
                iterator.remove();
                studentCourse.getCourse().getStudents().remove(studentCourse);
                studentCourse.setStudent(null);
                studentCourse.setCourse(null);
            }
        }
    }
}
