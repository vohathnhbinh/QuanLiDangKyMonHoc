package daos;

import models.Semester;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SemesterDao {
    private static List<Semester> semesters;
    public static List<Semester> getAll() {
        Transaction trans = null;
        try {
            String hql = "FROM Semester";
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            semesters = ss.createQuery(hql).list();
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }

        return semesters;
    }

    public static Semester findOne(String cond, String field) {
        Semester semester = null;
        Transaction trans = null;
        try {
            String hql = "FROM Semester WHERE ";
            hql += field + " = " + ":" + field;
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            Query query = null;
            if (field.substring(field.length() - 2).equals("id"))
                query = ss.createQuery(hql).setParameter(field, Integer.parseInt(cond));
            else
                query = ss.createQuery(hql).setParameter(field, cond);
            semester = (Semester) query.uniqueResult();
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
        return semester;
    }

    public static void add(Semester semester) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            int id = (int) ss.save(semester);
            ss.flush();
            ss.refresh(semester);
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
    }

    public static void remove(Semester semester) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            ss.delete(semester);
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            err.printStackTrace();
        }
    }
}
