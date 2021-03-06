package models;

import additionals.Role;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "staff")
public class Staff extends User{
    private String staff_number;

    public Staff() {
    }

    public Staff(int user_id, String username, String password, String fullname, String staff_number) {
        super(user_id, username, password, fullname, Role.STAFF);
        this.staff_number = staff_number;
    }

    public String getStaff_number() {
        return staff_number;
    }

    public void setStaff_number(String staff_number) {
        this.staff_number = staff_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return Objects.equals(staff_number, staff.staff_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staff_number);
    }
}
