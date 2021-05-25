package models;

import javax.persistence.*;

@Entity
public class Student extends User{
    private String student_id;

    public Student(int user_id, String username, String password, String fullname, int role, String student_id) {
        super(user_id, username, password, fullname, role);
        this.student_id = student_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
}
