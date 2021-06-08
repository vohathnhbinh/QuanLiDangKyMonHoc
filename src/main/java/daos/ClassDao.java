package daos;

import models.Class;
import models.Semester;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ClassDao {
    private static List<Class> classes;
    public static List<Class> getAll() {
        Transaction trans = null;
        try {
            String hql = "FROM Class";
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            classes = ss.createQuery(hql).list();
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }

        return classes;
    }

    public static Class findOne(String cond, String field) {
        Class myClass = null;
        Transaction trans = null;
        try {
            String hql = "FROM Class WHERE ";
            hql += field + " = " + ":" + field;
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            Query query = null;
            if (field.substring(field.length() - 2).equals("id"))
                query = ss.createQuery(hql).setParameter(field, Integer.parseInt(cond));
            else
                query = ss.createQuery(hql).setParameter(field, cond);
            myClass = (Class) query.uniqueResult();
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
        return myClass;
    }

    public static void add(Class myClass) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            int id = (int) ss.save(myClass);
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
    }

    public static void remove(Class myClass) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            ss.delete(myClass);
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            err.printStackTrace();
        }
    }
}
