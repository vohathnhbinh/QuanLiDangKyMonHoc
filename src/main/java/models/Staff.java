package models;

import javax.persistence.*;

@Entity
public class Staff extends User{
    private String staff_id;

    public Staff(int user_id, String username, String password, String fullname, int role, String staff_id) {
        super(user_id, username, password, fullname, role);
        this.staff_id = staff_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }
}
