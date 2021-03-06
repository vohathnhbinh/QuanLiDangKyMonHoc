package daos;

import models.Staff;
import models.Subject;
import models.Subject;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class SubjectDao {
    private static List<Subject> subjects;
    public static List<Subject> getAll() {
        Transaction trans = null;
        try {
            String hql = "FROM Subject";
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            subjects = ss.createQuery(hql).list();
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }

        return subjects;
    }

    public static Subject findOne(String cond, String field) {
        Subject subject = null;
        Transaction trans = null;
        try {
            String hql = "FROM Subject WHERE ";
            hql += field + " = " + ":" + field;
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            Query query = null;
            if (field.substring(field.length() - 2).equals("id"))
                query = ss.createQuery(hql).setParameter(field, Integer.parseInt(cond));
            else
                query = ss.createQuery(hql).setParameter(field, cond);
            subject = (Subject) query.uniqueResult();
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
        return subject;
    }

    public static void add(Subject subject) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            int id = (int) ss.save(subject);
            ss.flush();
            ss.createSQLQuery("SET SQL_SAFE_UPDATES = 0").executeUpdate();
            ss.createSQLQuery("CALL subj_number_update()").executeUpdate();
            ss.createSQLQuery("SET SQL_SAFE_UPDATES = 1").executeUpdate();
            ss.refresh(subject);
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
    }

    public static void remove(Subject subject) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            ss.delete(subject);
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            err.printStackTrace();
        }
    }
    public static String changeInfo(Subject subject, String subject_name, int credit) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();

            if (subject_name != null) {
                if (subject_name.length() < 10)
                    return "T??n m??n h???c ph???i c?? tr??n 10 k?? t???!";
                Subject anotherSubject = findOne(subject_name, "subject_name");
                if (anotherSubject != null)
                    return "T??n m??n h???c ???? t???n t???i!";
                subject.setSubject_name(subject_name);
            }
            if (credit > 0) {
                if (credit < 2 || credit > 10)
                    return "S??? t??n ch??? ph???i h???p l??? (2 <= x <= 10)";
                subject.setCredit_amount(credit);
            }
            ss.merge(subject);
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            System.err.println(err);
        }
        return "C???p nh???t th??ng tin th??nh c??ng!";
    }

    public static int myParseInt(String credit) {
        try {
            return Integer.parseInt(credit);
        } catch (NumberFormatException err) {
            return 0;
        }
    }

    public static List<Subject> searchSubject(String subject_number, String subject_name, String credit) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            String hql = "FROM Subject " +
                    "WHERE (subject_number LIKE :subject_number) OR (LOWER(subject_name) LIKE :subject_name) OR" +
                    "(credit_amount = :credit)";
            Query query = ss.createQuery(hql);
            query.setParameter("subject_number", '%' + subject_number + '%');
            query.setParameter("subject_name", '%' + subject_name.toLowerCase() + '%');
            query.setParameter("credit", myParseInt(credit));
            subjects = query.list();
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
        return subjects;
    }
}
