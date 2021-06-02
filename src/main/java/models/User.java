package models;

import javax.persistence.*;
import java.io.Serializable;

enum Role {
    STUDENT, STAFF
}

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    @Id @Column(name = "user_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected int user_id;

    @Column(unique = true)
    protected String username;

    protected String password;
    protected String fullname;

    @Enumerated(EnumType.STRING)
    protected Role role;

    public User() {}

    public User(String fullname, Role role) {
        this.fullname = fullname;
        this.role = role;
    }

    public User(int user_id, String username, String password, String fullname, Role role) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
