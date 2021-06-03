package daos;

import additionals.Account_Info;
import models.Staff;
import models.Student;
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
            Query query = null;
            if (field.substring(field.length() - 2).equals("id"))
                query = ss.createQuery(hql).setParameter(field, Integer.parseInt(cond));
            else
                query = ss.createQuery(hql).setParameter(field, cond);
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

    public static String changeInfo(Staff staff, Account_Info account) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            if (account.fullname != null)
                staff.setFullname(account.fullname);
            if (account.username != null) {
                if (account.username.length() < 6)
                    return "Username must have more than 6 characters!";
                Staff anotherStaff = findOne(account.username, "username");
                if (anotherStaff != null)
                    return "Username is not available!";
                staff.setUsername(account.username);
            }
            if (account.old_password != null) {
                if (!staff.getPassword().equals(account.old_password))
                    return "Wrong password!";
                if (account.new_password.length() < 6)
                    return "Password must have more than 6 characters!";
                if (!account.new_password.equals(account.retype_password))
                    return "Passwords are not matched!";
                staff.setPassword(account.new_password);
            }
            ss.merge(staff);
            trans.commit();
        } catch (HibernateError err) {
            System.err.println(err);
        }
        return "Update successful!";
    }
}
