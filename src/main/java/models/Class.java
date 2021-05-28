package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "class")
public class Class implements Serializable {
    @Id
    @Column(name = "class_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int class_id;

    private String class_name;
    private int class_size = 0;
    private int male_size = 0;
    private int female_size = 0;

    @OneToMany(mappedBy = "myClass")
    private Set<Student> students = new HashSet<>();

    public Class() {
    }

    public Class(int class_id, String class_name, Set<Student> students) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.students = students;
        setClass_size();
        setMale_size();
        setFemale_size();
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getClass_size() {
        return class_size;
    }

    public void setClass_size() {
        this.class_size = students.size();
    }

    public int getMale_size() {
        return male_size;
    }

    public void setMale_size() {
        for (Student student : this.students) {
            if (student.getGender().equals("MALE"))
                ++this.male_size;
        }
    }

    public int getFemale_size() {
        return female_size;
    }

    public void setFemale_size() {
        for (Student student : this.students) {
            if (student.getGender().equals("FEMALE"))
                ++this.male_size;
        }
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
