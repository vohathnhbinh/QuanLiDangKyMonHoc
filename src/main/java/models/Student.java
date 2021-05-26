package models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

enum Gender {
    MALE, FEMALE
}

@Entity
@Table(name = "student")
public class Student extends User{
    private String student_number;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name="class_id", nullable = false)
    private Class myClass;

    @OneToMany(mappedBy = "student")
    Set<Student_Course> stu_cours = new HashSet<>();

    public Student(int user_id, String username, String password, String fullname, Role role, String student_number, Gender gender, Class myClass, Set<Student_Course> stu_cours) {
        super(user_id, username, password, fullname, role);
        this.student_number = student_number;
        this.gender = gender;
        this.myClass = myClass;
        this.stu_cours = stu_cours;
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

    public Set<Student_Course> getStu_cours() {
        return stu_cours;
    }

    public void setStu_cours(Set<Student_Course> stu_cours) {
        this.stu_cours = stu_cours;
    }
}
