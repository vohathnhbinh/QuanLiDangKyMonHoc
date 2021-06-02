package daos;

import models.Staff;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StaffDao {
    private static List<Staff> staffs;
    public static List<Staff> getAll() {
        Transaction trans = null;
        try {
            String hql = "FROM Staff";
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            staffs = ss.createQuery(hql).list();
            trans.commit();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }

        return staffs;
    }

    public static Staff findOne(String cond, String field) {
        Staff staff = null;
        Transaction trans = null;
        try {
            String hql = "FROM Staff WHERE ";
            hql += field + " = " + ":" + field;
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            Query query = ss.createQuery(hql).setParameter(field, cond);
            staff = (Staff) query.uniqueResult();
            trans.commit();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
        return staff;
    }

    public static void add(Staff staff) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            int id = (int) ss.save(staff);
            ss.flush();
            ss.refresh(staff);

            String staffNumber = staff.getStaff_number();
            staff.setUsername(staffNumber);
            staff.setPassword(staffNumber);
            ss.update(staff);
            trans.commit();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
    }
}
