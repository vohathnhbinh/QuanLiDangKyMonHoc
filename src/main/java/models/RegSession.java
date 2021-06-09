package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "session")

public class RegSession implements Serializable {
    @Id
    @Column(name = "session_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int session_id;

    @ManyToOne
    @JoinColumn(name="semester_id", nullable=false)
    private Semester semester;

    private Date begin_date;
    private Date expired_date;

    public RegSession() {
    }

    public RegSession(int session_id, Semester semester, Date begin_date, Date expired_date) {
        this.session_id = session_id;
        this.semester = semester;
        this.begin_date = begin_date;
        this.expired_date = expired_date;
    }
    public RegSession(Semester semester, Date begin_date, Date expired_date) {
        this.semester = semester;
        this.begin_date = begin_date;
        this.expired_date = expired_date;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Date getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(Date begin_date) {
        this.begin_date = begin_date;
    }

    public Date getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(Date expired_date) {
        this.expired_date = expired_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegSession that = (RegSession) o;
        return session_id == that.session_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(session_id, begin_date, expired_date);
    }
}
