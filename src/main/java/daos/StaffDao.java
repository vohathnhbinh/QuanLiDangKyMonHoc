package daos;

import models.Staff;
import models.Student;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StaffDao {
    private static List<Staff> staffs;
    public static List<Staff> getAll() {
        try {
            String hql = "FROM Staff";
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            Transaction trans = ss.beginTransaction();
            staffs = ss.createQuery(hql).list();
        } catch (HibernateError err) {
            System.err.println(err);
        }

        return staffs;
    }

    public static Staff findOne(String cond) {
        Staff staff = null;
        try {
            String hql = "FROM Staff WhERE ";
            hql += cond;
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            Transaction trans = ss.beginTransaction();
            staff = (Staff) ss.createQuery(hql).uniqueResult();
        } catch (HibernateError err) {
            System.err.println(err);
        }
        return staff;
    }
}
