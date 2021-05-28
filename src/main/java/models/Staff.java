package models;

import javax.persistence.*;

@Entity
@Table(name = "staff")
public class Staff extends User{
    private String staff_number;

    public Staff() {
    }

    public Staff(int user_id, String username, String password, String fullname, Role role, String staff_number) {
        super(user_id, username, password, fullname, role);
        this.staff_number = staff_number;
    }

    public String getStaff_number() {
        return staff_number;
    }

    public void setStaff_number(String staff_number) {
        this.staff_number = staff_number;
    }
}
